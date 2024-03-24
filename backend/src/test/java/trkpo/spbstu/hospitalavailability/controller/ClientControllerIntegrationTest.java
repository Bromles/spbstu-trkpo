package trkpo.spbstu.hospitalavailability.controller;


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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.shaded.org.apache.commons.lang3.RandomStringUtils;
import trkpo.spbstu.hospitalavailability.dto.ClientRequestDto;
import trkpo.spbstu.hospitalavailability.repository.ClientRepository;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ClientControllerIntegrationTest {
    private static final String TEST_UUID = UUID.randomUUID().toString();

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
    private ObjectMapper objectMapper;
    @Autowired
    private WebApplicationContext context;
    @Autowired
    private ClientRepository clientRepository;
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
        clientRepository.deleteAll();
    }

    @Test
    void whenValidInput_ThenReturn200() throws Exception {
        assertTrue(POSTGRESQL_CONTAINER.isRunning());
        ClientRequestDto clientRequestDto = new ClientRequestDto();
        clientRequestDto.setEmail("test@test.ru");
        clientRequestDto.setKeycloakId(TEST_UUID);

        mvc.perform(post("/v1/client")
                .content(objectMapper.writeValueAsString(clientRequestDto))
                .contentType(MediaType.APPLICATION_JSON)
                .with(jwt()
                        .jwt(jwt -> jwt
                                .claim(StandardClaimNames.PREFERRED_USERNAME, TEST_UUID)
                                .claim(StandardClaimNames.SUB, TEST_UUID))
                )
        ).andExpect(status().isOk());
        assertNotNull(clientRepository.findFirstByKeycloakId(UUID.fromString(TEST_UUID)));
    }
}
