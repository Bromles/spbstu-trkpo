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
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import trkpo.spbstu.hospitalavailability.dto.*;
import trkpo.spbstu.hospitalavailability.entity.District;
import trkpo.spbstu.hospitalavailability.exception.NotFoundException;
import trkpo.spbstu.hospitalavailability.mapper.HospitalMapper;
import trkpo.spbstu.hospitalavailability.mapper.TrackingMapper;
import trkpo.spbstu.hospitalavailability.repository.DistrictRepository;
import trkpo.spbstu.hospitalavailability.repository.HospitalRepository;
import trkpo.spbstu.hospitalavailability.service.DistrictService;
import trkpo.spbstu.hospitalavailability.service.GorzdravService;
import trkpo.spbstu.hospitalavailability.service.HospitalService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class GorzdravControllerIntegrationTest {
    private static final String TEST_UUID = "5eb95f44-abbc-45e7-b760-0875ba0b8dec";

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
    @MockBean
    private GorzdravService gorzdravService;
    @MockBean
    private DistrictService districtService;
    @MockBean
    private HospitalService hospitalService;
    @Autowired
    private HospitalRepository hospitalRepository;
    @Autowired
    private DistrictRepository districtRepository;
    @Autowired
    private WebApplicationContext context;
    @Autowired
    private HospitalMapper hospitalMapper;
    private MockMvc mvc;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        mvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @AfterEach
    void resetDb() {
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
    void whenUpdateHospitals_ThenReturn200() throws Exception {
        var district = new District();
        district.setName("Test district");
        districtRepository.saveAndFlush(district);

        var hospitalDto = new GorzdravHospitalRsDto();
        hospitalDto.setAddress("test address");
        hospitalDto.setFullName("Test hospital");
        hospitalDto.setShortName("Hospital");
        hospitalDto.setPhone("00000000");
        hospitalDto.setDistrictId(district.getId());

        when(gorzdravService.getHospitals()).thenReturn(List.of(hospitalDto));

        mvc.perform(post("/v1/gorzdrav/hospital")
                .with(jwt()
                        .jwt(jwt -> jwt
                                .claim(StandardClaimNames.PREFERRED_USERNAME, TEST_UUID)
                                .claim(StandardClaimNames.SUB, TEST_UUID)
                        )
                )
        ).andExpect(status().isOk());

        assertEquals(1, hospitalRepository.count());
    }

    @Test
    void whenUpdateDistricts_ThenReturn200() throws Exception {
        var districtDto = new GorzdravDistrictRsDto();
        districtDto.setName("Test district");

        when(gorzdravService.getDistricts()).thenReturn(List.of(districtDto));

        mvc.perform(post("/v1/gorzdrav/district")
                .with(jwt()
                        .jwt(jwt -> jwt
                                .claim(StandardClaimNames.PREFERRED_USERNAME, TEST_UUID)
                                .claim(StandardClaimNames.SUB, TEST_UUID)
                        )
                )
        ).andExpect(status().isOk());

        assertEquals(1, districtRepository.count());
    }


    @Test
    void whenGetSpecialties_ThenReturn200() throws Exception {
        Long hospitalId = 1L;
        GorzdravSpecialtiesDto dto = new GorzdravSpecialtiesDto(1L, 20L, "Test Name");

        when(gorzdravService.getSpecialties(hospitalId)).thenReturn(List.of(dto));

        var response = mvc.perform(get("/v1/gorzdrav/specialties/" + hospitalId)
                        .with(jwt()
                                .jwt(jwt -> jwt
                                        .claim(StandardClaimNames.PREFERRED_USERNAME, TEST_UUID)
                                        .claim(StandardClaimNames.SUB, TEST_UUID)
                                )
                        )
                ).andReturn()
                .getResponse();

        List<GorzdravSpecialtiesDto> body = objectMapper.readValue(response.getContentAsString(), new TypeReference<>() {
        });

        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertIterableEquals(List.of(dto), body);
    }

    @Test
    void whenGetSpecialtiesInNonExistingHospital_ThenReturn404() throws Exception {
        Long hospitalId = 1L;

        when(gorzdravService.getSpecialties(hospitalId)).thenThrow(new NotFoundException("Hospital or specialties not found"));

        mvc.perform(get("/v1/gorzdrav/specialties/" + hospitalId)
                .with(jwt()
                        .jwt(jwt -> jwt
                                .claim(StandardClaimNames.PREFERRED_USERNAME, TEST_UUID)
                                .claim(StandardClaimNames.SUB, TEST_UUID)
                        )
                )
        ).andExpect(status().isNotFound());
    }

    @Test
    void whenGetHospitals_ThenReturn200() throws Exception {
        var district = new District();
        district.setName("Test district");
        districtRepository.saveAndFlush(district);

        HospitalResponseDto hospitalResponseDto = new HospitalResponseDto();
        hospitalResponseDto.setId(1L);
        hospitalResponseDto.setGorzdravId(1L);
        hospitalResponseDto.setAddress("ADDRESS");
        hospitalResponseDto.setDistrictId(district.getGorzdravId());
        hospitalResponseDto.setPhone("PHONE");
        hospitalResponseDto.setFullName("FULL_NAME");
        hospitalResponseDto.setShortName("SHORT_NAME");
        hospitalResponseDto.setLongitude(1.0);
        hospitalResponseDto.setLatitude(1.0);
        when(hospitalService.findAll()).thenReturn(List.of(hospitalResponseDto));

        var response = mvc.perform(get("/v1/gorzdrav/hospital")
                .with(jwt()
                        .jwt(jwt -> jwt
                                .claim(StandardClaimNames.PREFERRED_USERNAME, TEST_UUID)
                                .claim(StandardClaimNames.SUB, TEST_UUID)
                        )
                )
        ).andReturn().getResponse();
        List<GorzdravHospitalRsDto> body = objectMapper.readValue(response.getContentAsString(), new TypeReference<>() {
        });

        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertIterableEquals(List.of(hospitalResponseDto), body);
    }

    @Test
    void whenGetDistricts_ThenReturn200() throws Exception {
        var dto = new GorzdravDistrictRsDto(1L, "gorzdravId");
        DistrictResponseDto districtResponseDto = new DistrictResponseDto();
        districtResponseDto.setId(1L);
        districtResponseDto.setGorzdravId(1L);
        districtResponseDto.setName("gorzdravId");
        when(districtService.findAll()).thenReturn(List.of(districtResponseDto));

        var response = mvc.perform(get("/v1/gorzdrav/district")
                .with(jwt()
                        .jwt(jwt -> jwt
                                .claim(StandardClaimNames.PREFERRED_USERNAME, TEST_UUID)
                                .claim(StandardClaimNames.SUB, TEST_UUID)
                        )
                )
        ).andReturn().getResponse();
        List<GorzdravDistrictRsDto> body = objectMapper.readValue(response.getContentAsString(), new TypeReference<>() {
        });

        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertIterableEquals(List.of(dto), body);
    }

    @Test
    void whenGetDoctorsBySpecialityId_ThenReturn200() throws Exception {
        Long specialityId = 1L;
        Long hospitalId = 1L;
        GorzdravDoctorRsDto gorzdravDoctorRsDto = new GorzdravDoctorRsDto(1L, "NAME");

        when(gorzdravService.getDoctorsBySpecialityId(hospitalId, specialityId)).thenReturn(List.of(gorzdravDoctorRsDto));

        var response = mvc.perform(get("/v1/gorzdrav/doctors/" + hospitalId + "/" + specialityId)
                .with(jwt()
                        .jwt(jwt -> jwt
                                .claim(StandardClaimNames.PREFERRED_USERNAME, TEST_UUID)
                                .claim(StandardClaimNames.SUB, TEST_UUID)
                        )
                )
        ).andReturn().getResponse();
        List<GorzdravDoctorRsDto> body = objectMapper.readValue(response.getContentAsString(), new TypeReference<>() {
        });
        assertIterableEquals(List.of(gorzdravDoctorRsDto), body);
    }
}
