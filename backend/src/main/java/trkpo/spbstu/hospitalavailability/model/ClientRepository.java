package trkpo.spbstu.hospitalavailability.model;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClientRepository extends JpaRepository<Client, Long> {
    List<Client> findByClientId(Long clientId);
}
