package trkpo.spbstu.hospitalavailability.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import trkpo.spbstu.hospitalavailability.entity.Client;

import java.util.Optional;
import java.util.Random;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ClientRepositoryTest {

    @Mock
    private ClientRepository clientRepository;

    private static final UUID KEYCLOAK_ID = UUID.randomUUID();
    private static final long ID = new Random().nextLong();
    private static final String EMAIL = "test@gmail.com";

    @InjectMocks
    private Client client = new Client();

    @Test
    public void insertTest() {
        doNothing().when(clientRepository).insert(EMAIL, KEYCLOAK_ID);
        clientRepository.insert(EMAIL, KEYCLOAK_ID);
        verify(clientRepository, times(1)).insert(EMAIL, KEYCLOAK_ID);
    }

    @Test
    void testInsertWithConflict() {
        // try to insert the same data twice
        assertDoesNotThrow(() -> {
            clientRepository.insert(EMAIL, KEYCLOAK_ID); // first insert
            clientRepository.insert(EMAIL, KEYCLOAK_ID); // second insert
        });
    }

    @Test
    public void findFirstByKeycloakIdTest() {
        client.setId(ID);
        client.setEmail(EMAIL);
        client.setKeycloakId(KEYCLOAK_ID);
        when(clientRepository.findFirstByKeycloakId(KEYCLOAK_ID)).thenReturn(Optional.of(client));

        Optional<Client> result = clientRepository.findFirstByKeycloakId(KEYCLOAK_ID);

        assertTrue(result.isPresent());
        assertEquals(client, result.get());
        verify(clientRepository, times(1)).findFirstByKeycloakId(KEYCLOAK_ID);
    }

    @Test
    public void notFoundByKeycloakIdTest() {
        when(clientRepository.findFirstByKeycloakId(KEYCLOAK_ID)).thenReturn(Optional.empty());

        Optional<Client> result = clientRepository.findFirstByKeycloakId(KEYCLOAK_ID);

        assertTrue(result.isEmpty());
        verify(clientRepository, times(1)).findFirstByKeycloakId(KEYCLOAK_ID);
    }
}

