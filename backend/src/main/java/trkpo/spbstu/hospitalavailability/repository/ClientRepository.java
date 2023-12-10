package trkpo.spbstu.hospitalavailability.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import trkpo.spbstu.hospitalavailability.entity.Client;

import java.util.Optional;
import java.util.UUID;

public interface ClientRepository extends JpaRepository<Client, Long> {
    Optional<Client> findFirstByKeycloakId(UUID id);
    @Modifying
    @Query(value = "insert into Client (email, keycloak_id) values (:email,:keycloakId) " +
            "on conflict do nothing", nativeQuery = true)
    @Transactional
    void insert(@Param("email") String email, @Param("keycloakId") UUID keycloakId);
}
