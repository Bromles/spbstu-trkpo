package trkpo.spbstu.hospitalavailability.controller;


import java.time.LocalDateTime;
import java.util.UUID;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mail.MailSender;
import org.springframework.security.oauth2.core.oidc.StandardClaimNames;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.shaded.org.apache.commons.lang3.RandomStringUtils;
import org.testcontainers.shaded.org.apache.commons.lang3.RandomUtils;
import trkpo.spbstu.hospitalavailability.entity.Client;
import trkpo.spbstu.hospitalavailability.entity.District;
import trkpo.spbstu.hospitalavailability.entity.Hospital;
import trkpo.spbstu.hospitalavailability.entity.Tracking;
import trkpo.spbstu.hospitalavailability.repository.ClientRepository;
import trkpo.spbstu.hospitalavailability.repository.DistrictRepository;
import trkpo.spbstu.hospitalavailability.repository.HospitalRepository;
import trkpo.spbstu.hospitalavailability.repository.TrackingRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DeleteTrackingIntegrationTest {
    private static final String TEST_UUID = UUID.randomUUID().toString();
    private static final String TEST_UUID2 = UUID.randomUUID().toString();
    private static final Long HOSPITAL_G_ID = RandomUtils.nextLong();
    private static final String FULL_NAME = RandomStringUtils.randomAlphabetic(5);
    private static final Long DIRECTION_ID = RandomUtils.nextLong();
    private static final Long DOCTOR_ID = RandomUtils.nextLong();

    @Container
    @SuppressWarnings("resource")
    private static final PostgreSQLContainer<?> POSTGRESQL_CONTAINER = new PostgreSQLContainer<>("postgres:16.0")
            .withDatabaseName("backend_db")
            .withUsername(RandomStringUtils.randomAlphabetic(5))
            .withPassword(RandomStringUtils.randomAlphabetic(5));

    @MockBean
    private MailSender mailSender;
    @MockBean
    private JwtDecoder jwtDecoder;
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
    }

    @AfterEach
    void resetDb() {
        trackingRepository.deleteAll();
        hospitalRepository.deleteAll();
        clientRepository.deleteAll();
        districtRepository.deleteAll();
    }

    @DynamicPropertySource
    static void postgresProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", POSTGRESQL_CONTAINER::getJdbcUrl);
        registry.add("spring.datasource.username", POSTGRESQL_CONTAINER::getUsername);
        registry.add("spring.datasource.password", POSTGRESQL_CONTAINER::getPassword);
    }

    @Test
    void whenValidInput_ThenReturn200() throws Exception {
        assertTrue(POSTGRESQL_CONTAINER.isRunning());
        Tracking tracking = fillDatabase();

        MvcResult response = mvc.perform(delete("/v1/tracking/" + tracking.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .with(jwt()
                        .jwt(jwt -> jwt
                                .claim(StandardClaimNames.PREFERRED_USERNAME, TEST_UUID)
                                .claim(StandardClaimNames.SUB, TEST_UUID))
                )
        ).andExpect(status().isOk()).andReturn();
        assertEquals("" + tracking.getId(), response.getResponse().getContentAsString());
    }

    @Test
    void whenTrackingNotExists_ThenReturn404() throws Exception {
        assertTrue(POSTGRESQL_CONTAINER.isRunning());
        Tracking tracking = fillDatabase();

        mvc.perform(delete("/v1/tracking/" + tracking.getId() + 1)
                .contentType(MediaType.APPLICATION_JSON)
                .with(jwt()
                        .jwt(jwt -> jwt
                                .claim(StandardClaimNames.PREFERRED_USERNAME, TEST_UUID)
                                .claim(StandardClaimNames.SUB, TEST_UUID))
                ).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound());
        assertNotNull(trackingRepository.findById(tracking.getId()));
    }

    @Test
    void whenUnauthorised_ThenReturn401() throws Exception {
        assertTrue(POSTGRESQL_CONTAINER.isRunning());
        Tracking tracking = fillDatabase();

        mvc.perform(delete("/v1/tracking/" + tracking.getId())
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isUnauthorized());
        assertNotNull(trackingRepository.findById(tracking.getId()));
    }

    @Test
    void whenDifferentUser_ThenReturn401() throws Exception {
        assertTrue(POSTGRESQL_CONTAINER.isRunning());
        Tracking tracking = fillDatabase();

        mvc.perform(delete("/v1/tracking/" + tracking.getId())
                .with(jwt()
                        .jwt(jwt -> jwt
                                .claim(StandardClaimNames.PREFERRED_USERNAME, TEST_UUID2)
                                .claim(StandardClaimNames.SUB, TEST_UUID2))
                ).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isForbidden());
        assertNotNull(trackingRepository.findById(tracking.getId()));
    }

    private Tracking fillDatabase() {
        Hospital hospital = createHospital();
        Client client = createClient();
        return createTracking(client, hospital);
    }

    private Tracking createTracking(Client client, Hospital hospital) {
        Tracking tracking = new Tracking();
        tracking.setClient(client);
        tracking.setHospital(hospital);
        tracking.setFinished(false);
        tracking.setDirectionId(DIRECTION_ID);
        tracking.setDoctorId(DOCTOR_ID);
        tracking.setDate(LocalDateTime.now());
        trackingRepository.saveAndFlush(tracking);
        return tracking;
    }

    private Client createClient() {
        return simpleTransactionTemplate.execute(status -> {
            Client clientEntity = new Client();
            clientEntity.setKeycloakId(UUID.fromString(TEST_UUID));
            clientEntity.setEmail("test@test.ru");
            clientRepository.saveAndFlush(clientEntity);
            return clientEntity;
        });
    }

    private Hospital createHospital() {
        return simpleTransactionTemplate.execute(status -> {
            var district = new District();
            district.setName("Адмиралтейский");
            districtRepository.saveAndFlush(district);

            var hospitalEntity = new Hospital();
            hospitalEntity.setGorzdravId(HOSPITAL_G_ID);
            hospitalEntity.setAddress(RandomStringUtils.randomAlphabetic(5));
            hospitalEntity.setFullName(FULL_NAME);
            hospitalEntity.setShortName(RandomStringUtils.randomAlphabetic(5));
            hospitalEntity.setPhone(RandomStringUtils.randomAlphabetic(5));
            hospitalEntity.setDistrictId(district.getId());
            hospitalEntity.setDistrict(district);
            hospitalEntity.setLongitude(RandomUtils.nextDouble());
            hospitalEntity.setLatitude(RandomUtils.nextDouble());
            hospitalRepository.saveAndFlush(hospitalEntity);
            return hospitalEntity;
        });
    }
}
