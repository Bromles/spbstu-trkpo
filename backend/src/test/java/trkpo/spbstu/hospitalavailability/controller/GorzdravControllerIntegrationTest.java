package trkpo.spbstu.hospitalavailability.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
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
import trkpo.spbstu.hospitalavailability.dto.GorzdravDistrictRsDto;
import trkpo.spbstu.hospitalavailability.dto.GorzdravHospitalRsDto;
import trkpo.spbstu.hospitalavailability.entity.District;
import trkpo.spbstu.hospitalavailability.repository.DistrictRepository;
import trkpo.spbstu.hospitalavailability.repository.HospitalRepository;
import trkpo.spbstu.hospitalavailability.service.GorzdravService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

    @Autowired
    private HospitalRepository hospitalRepository;
    @Autowired
    private DistrictRepository districtRepository;

    @Autowired
    private WebApplicationContext context;
    private MockMvc mvc;

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
}
