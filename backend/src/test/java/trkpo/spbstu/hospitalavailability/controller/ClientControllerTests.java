package trkpo.spbstu.hospitalavailability.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.testcontainers.shaded.org.apache.commons.lang3.RandomStringUtils;
import trkpo.spbstu.hospitalavailability.dto.ClientRequestDto;
import trkpo.spbstu.hospitalavailability.service.ClientService;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


@ExtendWith(MockitoExtension.class)
class ClientControllerTests {

    @Mock
    private ClientService clientService;
    private ClientController clientController;

    @BeforeEach
    void setUp() {
        clientController = new ClientController(clientService);
    }

    @Test
    void createClientTest() {
        ClientRequestDto clientRequestDto = new ClientRequestDto();
        clientRequestDto.setKeycloakId(RandomStringUtils.randomAlphabetic(10));
        clientRequestDto.setEmail("test@email.com");
        clientController.create(clientRequestDto);
        verify(clientService, times(1)).create(clientRequestDto);
    }
}
