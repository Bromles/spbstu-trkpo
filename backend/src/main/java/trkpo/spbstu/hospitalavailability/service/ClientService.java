package trkpo.spbstu.hospitalavailability.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import trkpo.spbstu.hospitalavailability.entity.Client;
import trkpo.spbstu.hospitalavailability.repository.ClientRepository;

import java.util.UUID;


@Service
@RequiredArgsConstructor
public class ClientService {
    private final ClientRepository clientRepository;

    public void create(UUID keycloakUuid) {
        if (clientRepository.findFirstByKeycloakId(keycloakUuid).isEmpty()) {
            Client client = new Client();
            client.setKeycloakId(keycloakUuid);
            clientRepository.save(client);
        }
    }
}
