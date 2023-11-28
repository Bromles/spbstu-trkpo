package trkpo.spbstu.hospitalavailability.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import trkpo.spbstu.hospitalavailability.entity.Client;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long> {
}
