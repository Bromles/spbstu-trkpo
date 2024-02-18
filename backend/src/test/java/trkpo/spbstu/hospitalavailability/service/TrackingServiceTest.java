package trkpo.spbstu.hospitalavailability.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
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
import trkpo.spbstu.hospitalavailability.mapper.TrackingMapperImpl;
import trkpo.spbstu.hospitalavailability.repository.ClientRepository;
import trkpo.spbstu.hospitalavailability.repository.HospitalRepository;
import trkpo.spbstu.hospitalavailability.repository.TrackingRepository;
import trkpo.spbstu.hospitalavailability.utils.SecurityUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TrackingServiceTest {

    @Mock
    private TrackingRepository trackingRepository;
    private final TrackingMapper trackingMapper = new TrackingMapperImpl();
    @Mock
    private ClientRepository clientRepository;
    @Mock
    private HospitalRepository hospitalRepository;
    @Mock
    private SecurityContext securityContext;
    @Mock
    private Authentication authentication;
    @Mock
    private Jwt jwt;
    @Mock
    private RestTemplate restTemplate;
    @Mock
    private GorzdravService gorzdravService;
    @Mock
    private TransactionTemplate transactionTemplateRequiresNew;
    @Mock
    private NotificationMailSender notificationMailSender;
    private TrackingService trackingService;

    private static final UUID KEYCLOAK_ID = UUID.randomUUID();
    private static final long ID = new Random().nextLong();
    protected static final TrackingInfoRsDto TRACKING_INFO_RS_DTO = new TrackingInfoRsDto("Doctor Name", "Direction Name");
    protected static final GorzdravSpecialtiesDto GORZDRAV_SPECIALTIES_DTO = new GorzdravSpecialtiesDto(ID, 10L, "Specialty name");

    private Client getDefaultClient() {
        Client client = new Client();
        client.setKeycloakId(KEYCLOAK_ID);
        return client;
    }

    private Hospital getDefaultHospital() {
        Hospital hospital = new Hospital();
        hospital.setId(ID);
        hospital.setGorzdravId(ID);
        return hospital;
    }

    private Tracking getDefaultTracking() {
        Tracking tracking = new Tracking();
        tracking.setClient(getDefaultClient());
        tracking.setId(ID);
        tracking.setHospital(getDefaultHospital());
        tracking.setDoctorId(ID);
        tracking.setDirectionId(ID);
        tracking.setDate(LocalDateTime.now());
        tracking.setFinished(false);
        return tracking;
    }

    private TrackingRequestDto getDefaultTrackingRequestDto() {
        Tracking tracking = getDefaultTracking();
        TrackingRequestDto trackingRequestDto = new TrackingRequestDto();
        trackingRequestDto.setHospitalId(tracking.getHospital().getId());
        trackingRequestDto.setDirectionId(tracking.getDirectionId());
        trackingRequestDto.setDoctorId(tracking.getDoctorId());
        return trackingRequestDto;
    }

    private String checkAppointmentsUrl(Tracking tracking) {
        return "/schedule/lpu/" + tracking.getHospital().getGorzdravId() + "/doctor/" + tracking.getDoctorId() + "/appointments";
    }

    @BeforeEach
    void setUp() {
        this.trackingService = new TrackingService(trackingRepository, trackingMapper,
                clientRepository, hospitalRepository, restTemplate, gorzdravService,
                transactionTemplateRequiresNew, notificationMailSender);
        // security
        Mockito.lenient().when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        Mockito.lenient().when(authentication.getPrincipal()).thenReturn(jwt);
        Mockito.lenient().when(jwt.getSubject()).thenReturn(KEYCLOAK_ID.toString());
    }

    @Test
    void testDeleteTracking() {
        Tracking tracking = getDefaultTracking();

        when(trackingRepository.findByIsFinishedFalse()).thenReturn(List.of(tracking));
        when(SecurityUtils.getUserKey()).thenReturn(KEYCLOAK_ID.toString());

        trackingService.deleteTracking(ID);

        verify(trackingRepository, times(1)).updateTrackingFinishedById(true, ID);
    }

    @Test
    void testDeleteNotExistingTracking() {
        when(trackingRepository.findByIsFinishedFalse()).thenReturn(List.of());
        assertThrows(NotFoundException.class, () -> trackingService.deleteTracking(ID));
        verify(trackingRepository, times(0)).updateTrackingFinishedById(true, ID);
    }

    @Test
    void testDeleteFinishedTracking() {
        Tracking tracking = getDefaultTracking();
        tracking.setFinished(true);
        when(trackingRepository.findByIsFinishedFalse()).thenReturn(List.of(tracking));
        when(trackingRepository.findByIsFinishedFalse()).thenReturn(List.of());
        assertThrows(NotFoundException.class, () -> trackingService.deleteTracking(ID));
        verify(trackingRepository, times(0)).updateTrackingFinishedById(true, ID);
    }

    @Test
    void testDeleteTrackingWhenUserIsNotOwner() {
        Tracking tracking = getDefaultTracking();
        when(trackingRepository.findByIsFinishedFalse()).thenReturn(List.of(tracking));
        Mockito.lenient().when(jwt.getSubject()).thenReturn("");
        assertThrows(ForbiddenException.class, () -> trackingService.deleteTracking(ID));
        verify(trackingRepository, times(0)).updateTrackingFinishedById(true, ID);
    }

    @Test
    void findUserActiveTracking() {
        Tracking tracking = getDefaultTracking();
        List<Tracking> trackingList = List.of(tracking);
        TrackingResponseDto trackingDto = trackingMapper.toTrackingDto(tracking);
        List<TrackingResponseDto> trackingResponseDtoList = List.of(trackingDto);

        when(trackingRepository.findByIsFinishedFalseAndClientKeycloakId(KEYCLOAK_ID))
                .thenReturn(trackingList);

        List<TrackingResponseDto> result = trackingService.findUserActiveTracking(KEYCLOAK_ID.toString());
        assertEquals(trackingResponseDtoList, result);
        verify(trackingRepository, times(1)).findByIsFinishedFalseAndClientKeycloakId(KEYCLOAK_ID);
    }

    @Test
    void notFoundUserActiveTracking() {
        when(trackingRepository.findByIsFinishedFalseAndClientKeycloakId(KEYCLOAK_ID))
                .thenReturn(List.of());
        List<TrackingResponseDto> result = trackingService.findUserActiveTracking(KEYCLOAK_ID.toString());
        assertTrue(result.isEmpty());
        verify(trackingRepository, times(1)).findByIsFinishedFalseAndClientKeycloakId(KEYCLOAK_ID);
    }

    @Test
    void findUserActiveTrackingWithoutDoctor() {
        Tracking tracking = getDefaultTracking();
        tracking.setDoctorId(-1L);
        List<Tracking> trackingList = List.of(tracking);
        TrackingResponseDto trackingDto = trackingMapper.toTrackingDto(tracking);

        when(trackingRepository.findByIsFinishedFalseAndClientKeycloakId(KEYCLOAK_ID))
                .thenReturn(trackingList);

        List<TrackingResponseDto> result = trackingService.findUserActiveTracking(KEYCLOAK_ID.toString());
        trackingDto.setDoctorId(null);
        assertEquals(List.of(trackingDto), result);
        verify(trackingRepository, times(1)).findByIsFinishedFalseAndClientKeycloakId(KEYCLOAK_ID);
    }

    @Test
    void testAddTracking() {
        Hospital hospital = getDefaultHospital();
        Client client = getDefaultClient();
        Tracking tracking = getDefaultTracking();
        TrackingRequestDto trackingRequestDto = getDefaultTrackingRequestDto();

        when(hospitalRepository.findByGorzdravId(hospital.getId())).thenReturn(Optional.of(hospital));
        when(clientRepository.findFirstByKeycloakId(KEYCLOAK_ID)).thenReturn(Optional.of(client));
        when(trackingRepository.save(any())).thenReturn(tracking);

        TrackingResponseDto resultTrackingResponseDto = trackingService.addTracking(trackingRequestDto);

        assertEquals(trackingMapper.toTrackingDto(tracking), resultTrackingResponseDto);
        verify(hospitalRepository, times(1)).findByGorzdravId(hospital.getId());
        verify(clientRepository, times(1)).findFirstByKeycloakId(KEYCLOAK_ID);
        verify(trackingRepository, times(1)).save(any());
    }

    @Test
    void testAddTrackingHospitalNotExist() {
        TrackingRequestDto trackingRequestDto = getDefaultTrackingRequestDto();
        when(hospitalRepository.findByGorzdravId(trackingRequestDto.getHospitalId())).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> trackingService.addTracking(trackingRequestDto));
        verify(hospitalRepository, times(1)).findByGorzdravId(trackingRequestDto.getHospitalId());
        verify(clientRepository, times(0)).findFirstByKeycloakId(KEYCLOAK_ID);
        verify(trackingRepository, times(0)).save(any());
    }

    @Test
    void testAddTrackingClientNotExist() {
        Hospital hospital = getDefaultHospital();
        TrackingRequestDto trackingRequestDto = getDefaultTrackingRequestDto();

        when(hospitalRepository.findByGorzdravId(trackingRequestDto.getHospitalId())).thenReturn(Optional.of(hospital));
        when(clientRepository.findFirstByKeycloakId(UUID.fromString(SecurityUtils.getUserKey()))).thenReturn(Optional.empty());

        assertThrows(ForbiddenException.class, () -> trackingService.addTracking(trackingRequestDto));
        verify(hospitalRepository, times(1)).findByGorzdravId(trackingRequestDto.getHospitalId());
        verify(clientRepository, times(1)).findFirstByKeycloakId(KEYCLOAK_ID);
        verify(trackingRepository, times(0)).save(any());
    }

    @Test
    void testAddTrackingWithoutDoctor() {
        Hospital hospital = getDefaultHospital();
        TrackingRequestDto trackingRequestDto = getDefaultTrackingRequestDto();
        trackingRequestDto.setDoctorId(null);
        Client client = getDefaultClient();
        Tracking tracking = getDefaultTracking();
        tracking.setDoctorId(null);

        when(hospitalRepository.findByGorzdravId(hospital.getId())).thenReturn(Optional.of(hospital));
        when(clientRepository.findFirstByKeycloakId(KEYCLOAK_ID)).thenReturn(Optional.of(client));
        when(trackingRepository.save(any())).thenReturn(tracking);

        TrackingResponseDto resultTrackingResponseDto = trackingService.addTracking(trackingRequestDto);

        assertEquals(trackingMapper.toTrackingDto(tracking), resultTrackingResponseDto);
    }

    @Test
    void testCheckFreeAppointments() {
        Tracking tracking = getDefaultTracking();

        when(gorzdravService.getTrackingInfo(any(), any(), any())).thenReturn(TRACKING_INFO_RS_DTO);
        ResponseEntity<String> responseEntity = new ResponseEntity<>("{\"success\": true}", HttpStatus.OK);
        String url = "/schedule/lpu/" + tracking.getHospital().getGorzdravId() + "/doctor/" + tracking.getDoctorId() + "/appointments";
        when(restTemplate.getForEntity(url, String.class)).thenReturn(responseEntity);

        trackingService.checkFreeAppointments(tracking);

        verify(trackingRepository, times(1)).updateTrackingFinishedById(true, tracking.getId());
        verify(notificationMailSender, times(1)).sendMessage(any(), eq("Появились талоны для записи!"), any());
        verify(gorzdravService, times(1)).getTrackingInfo(any(), any(), any());
    }

    @Test
    void testCheckFreeAppointmentsBadGateway() {
        Tracking tracking = getDefaultTracking();

        when(gorzdravService.getTrackingInfo(any(), any(), any())).thenReturn(TRACKING_INFO_RS_DTO);
        ResponseEntity<String> responseEntity = new ResponseEntity<>("", HttpStatus.BAD_GATEWAY);
        String url = "/schedule/lpu/" + tracking.getHospital().getGorzdravId() + "/doctor/" + tracking.getDoctorId() + "/appointments";
        when(restTemplate.getForEntity(url, String.class)).thenReturn(responseEntity);
        assertThrows(BackendUnavailableException.class, () -> trackingService.checkFreeAppointments(tracking));

    }

    @Test
    void testCheckFreeAppointmentsWithoutDoctor() {
        Tracking tracking = getDefaultTracking();
        tracking.setDoctorId(-1L);
        tracking.setDate(tracking.getDate().minusDays(30));
        when(gorzdravService.getTrackingInfo(any(), any(), any())).thenReturn(TRACKING_INFO_RS_DTO);
        when(gorzdravService.getSpecialties(tracking.getHospital().getGorzdravId())).thenReturn(List.of(GORZDRAV_SPECIALTIES_DTO));

        trackingService.checkFreeAppointments(tracking);

        verify(trackingRepository, times(1)).updateTrackingFinishedById(true, tracking.getId());
        verify(notificationMailSender, times(1)).sendMessage(any(), eq("Появились талоны для записи!"), any());
        verify(gorzdravService, times(1)).getTrackingInfo(any(), any(), any());
    }

    @Test
    void testCheckFreeAppointmentsAndTrackingExpired() {
        Tracking tracking = getDefaultTracking();
        tracking.setDate(tracking.getDate().minusDays(61));

        when(gorzdravService.getTrackingInfo(any(), any(), any())).thenReturn(TRACKING_INFO_RS_DTO);
        ResponseEntity<String> responseEntity = new ResponseEntity<>("{\"success\": false}", HttpStatus.OK);
        when(restTemplate.getForEntity(checkAppointmentsUrl(tracking), String.class)).thenReturn(responseEntity);

        trackingService.checkFreeAppointments(tracking);

        verify(trackingRepository, times(1)).updateTrackingFinishedById(true, tracking.getId());
        verify(notificationMailSender, times(1)).sendMessage(any(), eq("Истек срок отслеживания талонов!"), any());
        verify(gorzdravService, times(1)).getTrackingInfo(any(), any(), any());
    }

    @Test
    void testWaitingFreeAppointments() {
        Tracking tracking1 = getDefaultTracking();
        Tracking tracking2 = getDefaultTracking();
        tracking2.setId(ID + 1);
        when(trackingRepository.findByIsFinishedFalse()).thenReturn(List.of(tracking1, tracking2));

        trackingService.waitingFreeAppointments();

        verify(transactionTemplateRequiresNew, times(2)).executeWithoutResult(any());
        verify(trackingRepository, times(1)).findByIsFinishedFalse();
    }

}
