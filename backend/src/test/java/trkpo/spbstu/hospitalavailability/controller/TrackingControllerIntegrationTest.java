package trkpo.spbstu.hospitalavailability.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.mail.MailSender;
import org.springframework.security.oauth2.core.oidc.StandardClaimNames;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.context.WebApplicationContext;
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

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.anonymous;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DirtiesContext
@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TrackingControllerIntegrationTest {
    private static final String TEST_UUID = "5eb95f44-abbc-45e7-b760-0875ba0b8dec";

    @Container
    @SuppressWarnings("resource")
    private  PostgreSQLContainer<?> POSTGRESQL_CONTAINER = new PostgreSQLContainer<>("postgres:16.0")
            .withDatabaseName("backend_db")
            .withUsername("hospital")
            .withPassword("password");

    @MockBean
    private MailSender mailSender;
    @MockBean
    private JwtDecoder jwtDecoder;

    @Autowired
    private TrackingMapper trackingMapper;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TransactionTemplate simpleTransactionTemplate;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private TrackingRepository trackingRepository;
    @Autowired
    private HospitalRepository hospitalRepository;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private DistrictRepository districtRepository;

    private MockMvc mvc;

    @BeforeEach
    void setup() {
        mvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(springSecurity())
                .build();

        resetDb();
    }

    @AfterEach
    void resetDb() {
        trackingRepository.deleteAll();
        clientRepository.deleteAll();
        hospitalRepository.deleteAll();
        districtRepository.deleteAll();
    }

    /*@DynamicPropertySource
    static void postgresProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", POSTGRESQL_CONTAINER::getJdbcUrl);
        registry.add("spring.datasource.username", POSTGRESQL_CONTAINER::getUsername);
        registry.add("spring.datasource.password", POSTGRESQL_CONTAINER::getPassword);
    }*/

    @Test
    void whenValidInput_ThenReturn200() throws Exception {
        assertTrue(POSTGRESQL_CONTAINER.isRunning());

        var tracking = simpleTransactionTemplate.execute(status -> {
            var district = new District();
            district.setName("Test district");
            districtRepository.saveAndFlush(district);

            var hospital = new Hospital();
            hospital.setAddress("test address");
            hospital.setFullName("Test hospital");
            hospital.setShortName("Hospital");
            hospital.setPhone("00000000");
            hospital.setDistrictId(district.getId());
            hospital.setDistrict(district);
            hospitalRepository.saveAndFlush(hospital);

            var client = new Client();
            client.setKeycloakId(UUID.fromString(TEST_UUID));
            client.setEmail("test@test.ru");
            clientRepository.saveAndFlush(client);

            var trackingEntity = new Tracking();
            trackingEntity.setDate(LocalDateTime.now());
            trackingEntity.setHospital(hospital);
            trackingEntity.setClient(client);
            trackingRepository.saveAndFlush(trackingEntity);

            return trackingEntity;
        });

        var response = mvc
                .perform(get("/v1/tracking/" + TEST_UUID)
                        .with(jwt()
                                .jwt(jwt -> jwt
                                        .claim(StandardClaimNames.PREFERRED_USERNAME, TEST_UUID)
                                        .claim(StandardClaimNames.SUB, TEST_UUID)
                                )
                        )
                )
                .andReturn()
                .getResponse();
        List<TrackingResponseDto> body = objectMapper.readValue(response.getContentAsString(), new TypeReference<>() {
        });

        tracking.setDoctorId(null);

        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertIterableEquals(List.of(trackingMapper.toTrackingDto(tracking)), body);
    }

    @Test
    void whenNoUser_ThenReturn401() throws Exception {
        mvc.perform(get("/v1/tracking/" + TEST_UUID).with(anonymous())).andExpect(status().isUnauthorized());
    }

    @Test
    void whenWrongUser_ThenReturn403() throws Exception {
        assertTrue(POSTGRESQL_CONTAINER.isRunning());

        simpleTransactionTemplate.executeWithoutResult(status -> {
            var district = new District();
            district.setName("Test district");
            districtRepository.saveAndFlush(district);

            var hospital = new Hospital();
            hospital.setAddress("test address");
            hospital.setFullName("Test hospital");
            hospital.setShortName("Hospital");
            hospital.setPhone("00000000");
            hospital.setDistrictId(district.getId());
            hospital.setDistrict(district);
            hospitalRepository.saveAndFlush(hospital);

            var client = new Client();
            client.setKeycloakId(UUID.fromString(TEST_UUID));
            client.setEmail("test@test.ru");
            clientRepository.saveAndFlush(client);

            var trackingEntity = new Tracking();
            trackingEntity.setDate(LocalDateTime.now());
            trackingEntity.setHospital(hospital);
            trackingEntity.setClient(client);
            trackingRepository.saveAndFlush(trackingEntity);
        });

        var anotherUuid = UUID.randomUUID();

        mvc.perform(get("/v1/tracking/" + TEST_UUID)
                .with(jwt()
                        .jwt(jwt -> jwt
                                .claim(StandardClaimNames.PREFERRED_USERNAME, anotherUuid.toString())
                                .claim(StandardClaimNames.SUB, anotherUuid.toString())
                        )
                )
        ).andExpect(status().isForbidden());
    }
}
