package trkpo.spbstu.hospitalavailability.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.client.RestTemplate;
import trkpo.spbstu.hospitalavailability.dto.GorzdravSpecialtiesDto;
import trkpo.spbstu.hospitalavailability.dto.TrackingInfoRsDto;
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
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class TrackingService {
    private final TrackingRepository trackingRepository;
    private final TrackingMapper trackingMapper;
    private final ClientRepository clientRepository;
    private final HospitalRepository hospitalRepository;
    private final RestTemplate restTemplate;
    private final GorzdravService gorzdravService;
    private final TransactionTemplate transactionTemplateRequiresNew;
    private final NotificationMailSender notificationMailSender;

    @Transactional
    public long deleteTracking(Long id) {
        Tracking tracking = findActiveTrackingById(id);
        if (tracking == null) {
            throw new NotFoundException("Tracking not found");
        }
        if (!SecurityUtils.getUserKey().equals(tracking.getClient().getKeycloakId().toString())) {
            throw new ForbiddenException("No access to delete tracking");
        }
        trackingRepository.updateTrackingFinishedById(true, id);
        return id;
    }

    public List<TrackingResponseDto> findUserActiveTracking(String keycloakUuid) {
        if (!SecurityUtils.getUserKey().equals(keycloakUuid)) {
            throw new ForbiddenException("No access to tracking info");
        }

        UUID uuid = UUID.fromString(keycloakUuid);
        List<Tracking> trackings = trackingRepository.findByIsFinishedFalseAndClientKeycloakId(uuid).stream()
                .map(tracking -> {
                    if (tracking.getDoctorId() != null && tracking.getDoctorId() == -1L) {
                        tracking.setDoctorId(null);
                    }
                    return tracking;
                }).collect(Collectors.toList());
        return trackingMapper.toTrackingDto(trackings);
    }

    @Transactional
    public TrackingResponseDto addTracking(TrackingRequestDto requestDto) {
        Hospital hospital = hospitalRepository.findByGorzdravId(requestDto.getHospitalId())
                .orElseThrow(() -> new NotFoundException("Hospital not found"));

        log.info("try to get client by keycloakId");
        Client client = clientRepository.findFirstByKeycloakId(UUID.fromString(SecurityUtils.getUserKey()))
                .orElseThrow(() -> new ForbiddenException("No access to add tracking"));
        log.info("Success getting client with id: " + client.getId());

        if (requestDto.getDoctorId() == null) {
            requestDto.setDoctorId(-1L);
        }
        Tracking tracking = new Tracking(requestDto, hospital, client);
        tracking.setFinished(false);
        tracking.setDate(new Timestamp(System.currentTimeMillis()).toLocalDateTime());
        return trackingMapper.toTrackingDto(trackingRepository.save(tracking));
    }

    @SuppressWarnings({"squid:S6809", "squid:S2229"})
    @Scheduled(fixedRate = 15, timeUnit = TimeUnit.MINUTES)
    public void waitingFreeAppointments() {
        List<Tracking> activeTracking = trackingRepository.findByIsFinishedFalse();
        for (Tracking tracking : activeTracking) {
            transactionTemplateRequiresNew.executeWithoutResult(txStatus -> checkFreeAppointments(tracking));
        }
    }

    @Transactional
    public void checkFreeAppointments(Tracking tracking) {
        boolean existAppointments;
        long id = tracking.getId();
        Long doctorId = tracking.getDoctorId();
        TrackingInfoRsDto trackingInfoRsDto = gorzdravService.getTrackingInfo(tracking.getHospital().getGorzdravId(), tracking.getDirectionId(), tracking.getDoctorId());
        String message;
        if (doctorId == -1L) {
            existAppointments = existsSpecialtiesAppointments(tracking.getHospital().getGorzdravId(), tracking.getDirectionId());
            message = " на направление " + trackingInfoRsDto.getDirectionName();
        }
        else {
            existAppointments = existsDoctorsAppointments(tracking.getHospital().getGorzdravId(), tracking.getDoctorId());
            message = " на направление " + trackingInfoRsDto.getDirectionName() + " ко врачу " + trackingInfoRsDto.getDoctorName();
        }
        if (existAppointments) {
            trackingRepository.updateTrackingFinishedById(true, id);
            notificationMailSender.sendMessage(tracking.getClient().getEmail(), "Появились талоны для записи!",
                    "Ура, Ура скорее беги записываться к врачу, появились свободные талоны в больницу " +
                            tracking.getHospital().getFullName() + message);
        }
        else {
            long durationDays = Duration.between(tracking.getDate(), new Timestamp(System.currentTimeMillis()).toLocalDateTime()).toDays();
            if (durationDays >= 60) {
                trackingRepository.updateTrackingFinishedById(true, id);
                notificationMailSender.sendMessage(tracking.getClient().getEmail(), "Истек срок отслеживания талонов!",
                        "Нам очень жаль, но талоны в больницу " + tracking.getHospital().getFullName() + message + " не появились в течении 60 дней, " +
                                "вы можете сделать новое отслеживание на сайте");
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
            log.warn(response.getStatusCode() + " " + response.getStatusCode().getReasonPhrase());
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
        return special.getCountFreeTicket() > 0;
    }
}


