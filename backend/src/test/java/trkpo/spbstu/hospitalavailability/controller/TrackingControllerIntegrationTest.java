package trkpo.spbstu.hospitalavailability.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.mail.MailSender;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import trkpo.spbstu.hospitalavailability.dto.TrackingResponseDto;
import trkpo.spbstu.hospitalavailability.entity.Client;
import trkpo.spbstu.hospitalavailability.entity.District;
import trkpo.spbstu.hospitalavailability.entity.Hospital;
import trkpo.spbstu.hospitalavailability.entity.Tracking;
import trkpo.spbstu.hospitalavailability.mapper.TrackingMapper;
import trkpo.spbstu.hospitalavailability.repository.ClientRepository;
import trkpo.spbstu.hospitalavailability.repository.DistrictRepository;
import trkpo.spbstu.hospitalavailability.repository.HospitalRepository;
import trkpo.spbstu.hospitalavailability.repository.TrackingRepository;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TrackingControllerIntegrationTest {
    private static final String TEST_UUID = "5eb95f44-abbc-45e7-b760-0875ba0b8dec";
    private static final String TEST_PASSWORD = "test";

    @Container
    @SuppressWarnings("resource")
    private static final PostgreSQLContainer<?> POSTGRESQL_CONTAINER = new PostgreSQLContainer<>("postgres:16.0")
            .withDatabaseName("backend_db")
            .withUsername("hospital")
            .withPassword("password");

    @MockBean
    private MailSender mailSender;
    @MockBean
    private JwtDecoder jwtDecoder;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private TrackingMapper trackingMapper;

    private TransactionTemplate transactionTemplate;

    @Autowired
    private PlatformTransactionManager transactionManager;

    @Autowired
    private TrackingRepository trackingRepository;
    @Autowired
    private HospitalRepository hospitalRepository;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private DistrictRepository districtRepository;

    @BeforeEach
    void setup() {
        this.transactionTemplate = new TransactionTemplate(transactionManager);
    }

    @AfterEach
    void resetDb() {
        trackingRepository.deleteAll();
        clientRepository.deleteAll();
        hospitalRepository.deleteAll();
        districtRepository.deleteAll();
    }

    @DynamicPropertySource
    static void postgresProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", POSTGRESQL_CONTAINER::getJdbcUrl);
        registry.add("spring.datasource.username", POSTGRESQL_CONTAINER::getUsername);
        registry.add("spring.datasource.password", POSTGRESQL_CONTAINER::getPassword);
    }

    @Test
    @WithMockUser(username = TEST_UUID, password = TEST_PASSWORD)
    void whenValidInput_ThenReturn200() {
        assertTrue(POSTGRESQL_CONTAINER.isRunning());

        var tracking = transactionTemplate.execute(status -> {
            var district = new District();
            district.setId(1L);
            district.setName("Test district");

            var hospital = new Hospital();
            hospital.setId(1L);
            hospital.setAddress("test address");
            hospital.setFullName("Test hospital");
            hospital.setShortName("Hospital");
            hospital.setPhone("00000000");
            hospital.setDistrictId(district.getId());
            hospital.setDistrict(district);
            hospitalRepository.saveAndFlush(hospital);

            var client = new Client();
            client.setId(1L);
            client.setKeycloakId(UUID.fromString(TEST_UUID));
            client.setEmail("test@test.ru");
            clientRepository.saveAndFlush(client);

            var trackingEntity = new Tracking();
            trackingEntity.setId(1L);
            trackingEntity.setDate(LocalDateTime.now());
            trackingEntity.setHospital(hospital);
            trackingEntity.setClient(client);
            trackingEntity.setDoctorId(1L);
            trackingRepository.saveAndFlush(trackingEntity);

            return trackingEntity;
        });

        var headers = new HttpHeaders();
        var token = new String(Base64.getEncoder().encode(TEST_PASSWORD.getBytes(StandardCharsets.UTF_8)));

        headers.add("Authorization", "Bearer " + token);

        ResponseEntity<List<TrackingResponseDto>> response = testRestTemplate.exchange("/v1/tracking/" + TEST_UUID,
                HttpMethod.GET,
                new HttpEntity<>(headers), new ParameterizedTypeReference<>() {
                });

        var body = response.getBody();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertIterableEquals(List.of(trackingMapper.toTrackingDto(tracking)), body);
    }
}
