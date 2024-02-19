package trkpo.spbstu.hospitalavailability.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import trkpo.spbstu.hospitalavailability.dto.ClientRequestDto;
import trkpo.spbstu.hospitalavailability.entity.Client;
import trkpo.spbstu.hospitalavailability.repository.ClientRepository;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.never;

@ExtendWith(MockitoExtension.class)
public class ClientServiceTests {

    @Mock
    private ClientRepository clientRepository;
    private static final UUID CLIENT_UUID = UUID.randomUUID();
    private static final String EMAIL = "test@example.com";
    private ClientService clientService;

    @BeforeEach
    void setup() {
        clientService = new ClientService(clientRepository);
    }

    @Test
    void testCreateClient() {
        ClientRequestDto clientRequestDto = new ClientRequestDto();
        clientRequestDto.setKeycloakId(CLIENT_UUID.toString());
        clientRequestDto.setEmail(EMAIL);

        when(clientRepository.findFirstByKeycloakId(CLIENT_UUID)).thenReturn(Optional.empty());
        doNothing().when(clientRepository).insert(EMAIL, CLIENT_UUID);
        clientService.create(clientRequestDto);
        verify(clientRepository, times(1)).insert(EMAIL, CLIENT_UUID);
    }

    @Test
    void testCreateClientWhenClientAlreadyExists() {
        ClientRequestDto clientRequestDto = new ClientRequestDto();
        clientRequestDto.setKeycloakId(CLIENT_UUID.toString());
        clientRequestDto.setEmail(EMAIL);

        when(clientRepository.findFirstByKeycloakId(CLIENT_UUID)).thenReturn(Optional.of(new Client()));
        clientService.create(clientRequestDto);
        verify(clientRepository, never()).insert(EMAIL, CLIENT_UUID);
    }
}