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

    public void create(String keycloakUuid) {
        UUID uuid = UUID.fromString(keycloakUuid);
        if (clientRepository.findFirstByKeycloakId(uuid).isEmpty()) {
            Client client = new Client();
            client.setKeycloakId(uuid);
            clientRepository.save(client);
        }
    }
}
