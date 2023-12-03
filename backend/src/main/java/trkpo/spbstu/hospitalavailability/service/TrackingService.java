package trkpo.spbstu.hospitalavailability.service;

import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.client.RestTemplate;
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

import java.sql.Timestamp;
import java.time.Duration;
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
    private final RestTemplate restTemplate;
    private final GorzdravService gorzdravService;
    private final TransactionTemplate transactionTemplate;
    private final NotificationMailSender notificationMailSender;
    private static final Logger logger = Logger.getLogger(TrackingService.class.getName());

    @Transactional
    public long deleteTracking(Long id) {
        Tracking tracking = findActiveTrackingById(id);
        if (tracking == null) {
            throw new NotFoundException("Tracking not found");
        }
        if (!SecurityUtils.getUserKey().equals(tracking.getClient().getKeycloakId().toString())) {
            throw new ForbiddenException("No access to delete tracking");
        }
        return trackingRepository.removeById(id);
    }

    public List<TrackingResponseDto> findUserActiveTracking(Long clientId) {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new NotFoundException("Client not found"));

        if (!SecurityUtils.getUserKey().equals(client.getKeycloakId().toString())) {
            throw new ForbiddenException("No access to delete tracking");
        }

        return trackingMapper.toTrackingDto(trackingRepository.findByIsFinishedFalseAndClientId(clientId));
    }

    @Transactional
    public TrackingResponseDto addTracking(TrackingRequestDto requestDto) {
        Hospital hospital = hospitalRepository.findByGorzdravId(requestDto.getHospitalId())
                .orElseThrow(() -> new NotFoundException("Hospital not found"));

        Client client = clientRepository.findFirstByKeycloakId(UUID.fromString(SecurityUtils.getUserKey()))
                .orElseThrow(() -> new ForbiddenException("No access to add tracking"));

        Tracking tracking = new Tracking(requestDto, hospital, client);
        tracking.setFinished(false);
        tracking.setDate(new Timestamp(System.currentTimeMillis()).toLocalDateTime());
        return trackingMapper.toTrackingDto(trackingRepository.save(tracking));
    }

    @SuppressWarnings("squid:S6809")
    @Scheduled(fixedRate = 15, timeUnit = TimeUnit.MINUTES)
    @Transactional
    public void waitingFreeAppointments() {
        List<Tracking> activeTracking = trackingRepository.findByIsFinishedFalse();
        for(Tracking tracking : activeTracking) {
            transactionTemplate.executeWithoutResult(txStatus -> checkFreeAppointments(tracking));
        }
    }

    @Transactional()
    //что делать с исключениями? нужно продолжать циклс в любом случа и не откатывать все изменения в бд для других трекингов
    public void checkFreeAppointments(Tracking tracking) {
        boolean existAppointments = false;
        long id = tracking.getId();
        Long doctorId = tracking.getDoctorId();
        if (doctorId == null) {
            existAppointments = existsSpecialtiesAppointments(tracking.getHospital().getGorzdravId(), tracking.getDirectionId());
        } else {
            existAppointments = existsDoctorsAppointments(tracking.getHospital().getGorzdravId(), tracking.getDoctorId());
        }
        if (existAppointments) {
            trackingRepository.updateTrackingFinishedById(false, id);
            notificationMailSender.sendMessage(SecurityUtils.getUserMail(), "Появились талоны для записи!", "...");
        } else {
            long durationDays = Duration.between(tracking.getDate(), new Timestamp(System.currentTimeMillis()).toLocalDateTime()).toDays();
            if (durationDays >= 60) {
                trackingRepository.updateTrackingFinishedById(false, id);
                notificationMailSender.sendMessage(SecurityUtils.getUserMail(), "Истек срок отслеживания талонов!", "...");
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
        ResponseEntity<String> response = restTemplate.getForEntity("/schedule/lpu/" + gorzdravHospitalId + "/doctor/" + doctorId + "/appointments", String.class);
        if (response.getStatusCode() == HttpStatus.BAD_GATEWAY || response.getStatusCode() == HttpStatus.SERVICE_UNAVAILABLE) {
            logger.warning(response.getStatusCode() + " " + response.getStatusCode().getReasonPhrase());
            throw new BackendUnavailableException("Gorzdrav is unavailable: " + response.getStatusCode().getReasonPhrase());
        }
        String responseBody = response.getBody();
        //если нет талонов у врача приходит флаг false, или если нет врача, или  нет такой больницы
        return new JSONObject(responseBody).getBoolean("success");
    }

    private boolean existsSpecialtiesAppointments(Long gorzdravHospitalId, Long specialtiesId) {
        List<GorzdravSpecialtiesDto> specialties = gorzdravService.getSpecialties(gorzdravHospitalId);
        GorzdravSpecialtiesDto special = specialties.stream()
                .filter(specialtiesDto -> specialtiesId.equals(specialtiesDto.getId()))
                .findFirst().orElseThrow(() -> new NotFoundException("Specialties not found"));
        return special.getCountFreeTicket() > 0 ;
    }
}


