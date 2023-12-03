package trkpo.spbstu.hospitalavailability.service;

import com.github.dockerjava.zerodep.shaded.org.apache.hc.client5.http.classic.methods.HttpGet;
import com.github.dockerjava.zerodep.shaded.org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import com.github.dockerjava.zerodep.shaded.org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import com.github.dockerjava.zerodep.shaded.org.apache.hc.client5.http.impl.classic.HttpClients;
import com.github.dockerjava.zerodep.shaded.org.apache.hc.core5.http.ParseException;
import com.github.dockerjava.zerodep.shaded.org.apache.hc.core5.http.io.entity.EntityUtils;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import trkpo.spbstu.hospitalavailability.dto.GorzdravSpecialtiesDto;
import trkpo.spbstu.hospitalavailability.dto.TrackingRequestDto;
import trkpo.spbstu.hospitalavailability.dto.TrackingResponseDto;
import trkpo.spbstu.hospitalavailability.entity.Client;
import trkpo.spbstu.hospitalavailability.entity.Hospital;
import trkpo.spbstu.hospitalavailability.entity.Tracking;
import trkpo.spbstu.hospitalavailability.exception.BackendUnavailableException;
import trkpo.spbstu.hospitalavailability.exception.ForbiddenException;
import trkpo.spbstu.hospitalavailability.exception.NotFoundException;
import trkpo.spbstu.hospitalavailability.mapper.TrackingMapper;
import trkpo.spbstu.hospitalavailability.repository.ClientRepository;
import trkpo.spbstu.hospitalavailability.repository.HospitalRepository;
import trkpo.spbstu.hospitalavailability.repository.TrackingRepository;
import trkpo.spbstu.hospitalavailability.utils.SecurityUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class TrackingService {

    private final TrackingRepository trackingRepository;
    private final TrackingMapper trackingMapper;
    private final ClientRepository clientRepository;
    private final HospitalRepository hospitalRepository;
    private static final Logger logger = Logger.getLogger(TrackingService.class.getName());
    private static final String BASE_URL = "https://gorzdrav.spb.ru/_api/api/v2/schedule/lpu/";

    @Transactional
    public long deleteTracking(Long id) {
        Tracking tracking = findActiveTrackingById(id);
        if (tracking == null) {
            throw new NotFoundException("Not found tracking");
        }
        if (!SecurityUtils.getUserKey().equals(tracking.getClient().getKeycloakId().toString())) {
            throw new ForbiddenException("No access to delete tracking");
        }
        return trackingRepository.removeById(id);
    }

    public List<TrackingResponseDto> findUserActiveTracking(Long clientId) {
        Client client = clientRepository.findById(clientId).orElseThrow(() -> new NotFoundException("Not found client"));
        if (!SecurityUtils.getUserKey().equals(client.getKeycloakId().toString())) {
            throw new ForbiddenException("No access to delete tracking");
        }
        return trackingMapper.toTrackingDto(trackingRepository.findByIsFinishedFalseAndClientId(clientId));
    }

    @Transactional
    public TrackingResponseDto addTracking(TrackingRequestDto requestDto) {
        Hospital hospital = hospitalRepository.findById(requestDto.getHospitalId())
                .orElseThrow(() -> new NotFoundException("Not found hospital"));
        Client client = clientRepository.findFirstByKeycloakId(UUID.fromString(SecurityUtils.getUserKey()))
                .orElseThrow(() -> new ForbiddenException("No access to add tracking"));
        Tracking tracking = new Tracking(requestDto, hospital, client);
        tracking.setFinished(false);
        tracking.setDate(new Timestamp(System.currentTimeMillis()).toLocalDateTime());
        return trackingMapper.toTrackingDto(trackingRepository.save(tracking));
    }

    @Async
    @Scheduled(fixedRate = 15, timeUnit = TimeUnit.MINUTES)
    @Transactional // так как updateTrackingFinishedById()?
    //как обрабатывать исключения?
    // что будет если парально пойдет обновление больниц
    // что будет если паральльно пойдет два потока отслеживания
    //что будет если удалим трекинг во ввремя отслеживания
    public void waitingFreeAppointments() {
        List<Tracking> activeTracking = trackingRepository.findByIsFinishedFalse();
        for(Tracking tracking : activeTracking) {
            long id = tracking.getId();
            Long doctorId = tracking.getDoctorId();
            boolean existAppointments;
            if (doctorId == null) {
                existAppointments = existsSpecialtiesAppointments(tracking.getHospital().getGorzdravId(), tracking.getDirectionId());
            } else {
                existAppointments = existsDoctorsAppointments(tracking.getHospital().getGorzdravId(), tracking.getDoctorId());
            }
            if (existAppointments ) {
                trackingRepository.updateTrackingFinishedById(false, id);
                //sendMessage
            } else {
                long durationDays = Duration.between(tracking.getDate(), new Timestamp(System.currentTimeMillis()).toLocalDateTime()).toDays();
                if (durationDays>=60) {
                    trackingRepository.updateTrackingFinishedById(false, id);
                    //sendMessage sorry много времени прошло
                }
            }
        }
    }

    private Tracking findActiveTrackingById(Long id) {
        List<Tracking> activeTracking = trackingRepository.findByIsFinishedFalse();
        return activeTracking.stream()
                .filter(tracking -> id.equals(tracking.getId()))
                .findFirst()
                .orElse(null);
    }

    private boolean existsDoctorsAppointments(Long gorzdravHospitalId, Long doctorId) {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            final HttpGet httpGet = new HttpGet(BASE_URL + gorzdravHospitalId + "/doctor/" + doctorId + "/appointments");

            try (CloseableHttpResponse response = httpClient.execute(httpGet)) {
                if (response.getCode() == 502 || response.getCode() == 503) {
                    logger.warning(response.getCode() + " " + response.getReasonPhrase());
                    throw new BackendUnavailableException("Gorzdrav is unavailable: " + response.getReasonPhrase());
                }
                String responseBody = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
                //если нет талонов у врача приходит флаг false
                return new JSONObject(responseBody).getBoolean("success");
            } catch (ParseException e) {
                throw new ForbiddenException("Cannot parse json from Gorzdrav: " + e);
            }
        } catch (IOException e) {
            throw new ForbiddenException("Cannot create default http client for Gorzdrav: " + e);
        }
    }

    private boolean existsSpecialtiesAppointments(Long gorzdravHospitalId, Long specialtiesId) {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            final HttpGet httpGet = new HttpGet(BASE_URL + gorzdravHospitalId + "/specialties");

            try (CloseableHttpResponse response = httpClient.execute(httpGet)) {
                if (response.getCode() == 502 || response.getCode() == 503) {
                    logger.warning(response.getCode() + " " + response.getReasonPhrase());
                    throw new BackendUnavailableException("Gorzdrav is unavailable: " + response.getReasonPhrase());
                }
                String responseBody = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
                JSONArray array = new JSONObject(responseBody).getJSONArray("result");
                List<GorzdravSpecialtiesDto>  specialties = new ArrayList<>();
                for (int i = 0; i < array.length(); i++) {
                    JSONObject jsObj = array.getJSONObject(i);
                    var special= convertToDto(jsObj);
                    if (special!= null) {
                        specialties.add(special);
                    }
                }
                GorzdravSpecialtiesDto special = specialties.stream()
                        .filter(specialtiesDto -> specialtiesId.equals(specialtiesDto.getId()))
                        .findFirst().orElseThrow(() -> new NotFoundException("not found specialties"));

                return special.getCountFreeTicket() > 0 ;
            } catch (ParseException e) {
                throw new ForbiddenException("Cannot parse json from Gorzdrav: " + e);
            }
        } catch (IOException e) {
            throw new ForbiddenException("Cannot create default http client for Gorzdrav: " + e);
        }
    }

    private GorzdravSpecialtiesDto convertToDto(JSONObject jsObj) {
        try {
            return new GorzdravSpecialtiesDto(
                    Long.parseLong(jsObj.get("id").toString()),
                    Long.parseLong(jsObj.get("countFreeTicket").toString())
            );
        } catch (Exception e) {
            logger.warning("cannot parse JSONObject, error number: ");
        }
        return null;
    }
}


