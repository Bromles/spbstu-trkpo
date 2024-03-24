package trkpo.spbstu.hospitalavailability.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import trkpo.spbstu.hospitalavailability.dto.TrackingRequestDto;
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

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AddTrackingIntegrationTest {

    private static final String TEST_UUID = UUID.randomUUID().toString();
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

    @DynamicPropertySource
    static void postgresProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", POSTGRESQL_CONTAINER::getJdbcUrl);
        registry.add("spring.datasource.username", POSTGRESQL_CONTAINER::getUsername);
        registry.add("spring.datasource.password", POSTGRESQL_CONTAINER::getPassword);
    }

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

    @Test
    void whenValidInput_ThenReturn200() throws Exception {
        assertTrue(POSTGRESQL_CONTAINER.isRunning());
        Client client = createClient();
        Hospital hospital = createHospital();

        TrackingRequestDto trackingRequestDto = new TrackingRequestDto();
        trackingRequestDto.setDoctorId(null);
        trackingRequestDto.setHospitalId(HOSPITAL_G_ID);
        trackingRequestDto.setDirectionId(DIRECTION_ID);
        MvcResult response = mvc.perform(post("/v1/tracking")
                .content(objectMapper.writeValueAsString(trackingRequestDto))
                .contentType(MediaType.APPLICATION_JSON)
                .with(jwt()
                        .jwt(jwt -> jwt
                                .claim(StandardClaimNames.PREFERRED_USERNAME, TEST_UUID)
                                .claim(StandardClaimNames.SUB, TEST_UUID))
                )
        ).andExpect(status().isOk()).andReturn();

        TrackingResponseDto trackingResponseDto = objectMapper.readValue(response.getResponse().getContentAsString(), new TypeReference<>() {
        });
        assertAll("Проверим поля ответа",
                () -> assertFalse(trackingResponseDto.isFinished()),
                () -> assertEquals(trackingResponseDto.getHospitalId(), hospital.getId()),
                () -> assertEquals(trackingResponseDto.getDoctorId(), -1L),
                () -> assertEquals(trackingResponseDto.getClientId(), client.getId()),
                () -> assertEquals(DIRECTION_ID, trackingResponseDto.getDirectionId()),
                () -> assertEquals(HOSPITAL_G_ID, trackingResponseDto.getHospitalGorzdravId()),
                () -> assertEquals(FULL_NAME, trackingResponseDto.getHospitalFullName()));
    }

    @Test
    void whenUnauthorized_ThenReturn401() throws Exception {
        assertTrue(POSTGRESQL_CONTAINER.isRunning());
        TrackingRequestDto trackingRequestDto = new TrackingRequestDto();
        trackingRequestDto.setDoctorId(null);
        trackingRequestDto.setHospitalId(RandomUtils.nextLong());
        trackingRequestDto.setDirectionId(DIRECTION_ID);

        mvc.perform(post("/v1/tracking")
                .content(objectMapper.writeValueAsString(trackingRequestDto))
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isUnauthorized());
    }

    @Test
    void whenHospitalNotFound_ThenReturn404() throws Exception {
        assertTrue(POSTGRESQL_CONTAINER.isRunning());
        createClient();
        TrackingRequestDto trackingRequestDto = new TrackingRequestDto();
        trackingRequestDto.setDoctorId(null);
        trackingRequestDto.setHospitalId(RandomUtils.nextLong());
        trackingRequestDto.setDirectionId(DIRECTION_ID);

        mvc.perform(post("/v1/tracking")
                .content(objectMapper.writeValueAsString(trackingRequestDto))
                .with(jwt()
                        .jwt(jwt -> jwt
                                .claim(StandardClaimNames.PREFERRED_USERNAME, TEST_UUID)
                                .claim(StandardClaimNames.SUB, TEST_UUID))
                )
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound());
    }

    @Test
    void whenTrackingAlreadyExist_ThenReturn200() throws Exception {
        assertTrue(POSTGRESQL_CONTAINER.isRunning());
        Client client = createClient();
        Hospital hospital = createHospital();

        Tracking tracking = new Tracking();
        tracking.setFinished(false);
        tracking.setDate(LocalDateTime.now());
        tracking.setHospital(hospital);
        tracking.setDirectionId(DIRECTION_ID);
        tracking.setClient(client);
        tracking.setDoctorId(DOCTOR_ID);
        trackingRepository.saveAndFlush(tracking);

        TrackingRequestDto trackingRequestDto = new TrackingRequestDto();
        trackingRequestDto.setDoctorId(DOCTOR_ID);
        trackingRequestDto.setHospitalId(HOSPITAL_G_ID);
        trackingRequestDto.setDirectionId(DIRECTION_ID);

        MvcResult response = mvc.perform(post("/v1/tracking")
                .content(objectMapper.writeValueAsString(trackingRequestDto))
                .contentType(MediaType.APPLICATION_JSON)
                .with(jwt()
                        .jwt(jwt -> jwt
                                .claim(StandardClaimNames.PREFERRED_USERNAME, TEST_UUID)
                                .claim(StandardClaimNames.SUB, TEST_UUID))
                )
        ).andExpect(status().isOk()).andReturn();

        TrackingResponseDto trackingResponseDto = objectMapper.readValue(response.getResponse().getContentAsString(), new TypeReference<>() {
        });
        assertEquals(trackingResponseDto, trackingMapper.toTrackingDto(tracking), "Изменились поля, которые не должны были менятся");
        Optional<Tracking> trackingNew = trackingRepository.findById(tracking.getId());
        assertTrue(trackingNew.isPresent());
        assertNotEquals(trackingNew.get().getDate(), tracking.getDate(), "Дата не изменилась");
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
            district.setName(RandomStringUtils.randomAlphabetic(5));
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
