package trkpo.spbstu.hospitalavailability.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import trkpo.spbstu.hospitalavailability.dto.ClientRequestDto;
import trkpo.spbstu.hospitalavailability.entity.Client;
import trkpo.spbstu.hospitalavailability.repository.ClientRepository;

import java.util.UUID;


@Service
@RequiredArgsConstructor
public class ClientService {
    private final ClientRepository clientRepository;

    public void create(ClientRequestDto clientRequestDto) {
        UUID uuid = UUID.fromString(clientRequestDto.getKeycloakId());
        if (clientRepository.findFirstByKeycloakId(uuid).isEmpty()) {
            Client client = new Client();
            client.setKeycloakId(uuid);
            client.setEmail(client.getEmail());
            clientRepository.save(client);
        }
    }
}
