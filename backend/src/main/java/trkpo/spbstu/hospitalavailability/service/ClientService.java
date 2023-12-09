package trkpo.spbstu.hospitalavailability.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import trkpo.spbstu.hospitalavailability.dto.ClientRequestDto;
import trkpo.spbstu.hospitalavailability.entity.Client;
import trkpo.spbstu.hospitalavailability.repository.ClientRepository;

import java.util.UUID;


@Service
@RequiredArgsConstructor
public class ClientService {
    private final ClientRepository clientRepository;

    @Transactional
    public void create(ClientRequestDto clientRequestDto) {
        UUID uuid = UUID.fromString(clientRequestDto.getKeycloakId());
        if (clientRepository.findFirstByKeycloakId(uuid).isPresent()) {
            Client client = new Client();
            client.setKeycloakId(uuid);
            client.setEmail(clientRequestDto.getEmail());
            clientRepository.save(client);
        }
    }
}
