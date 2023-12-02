package trkpo.spbstu.hospitalavailability.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import trkpo.spbstu.hospitalavailability.entity.Hospital;

public interface HospitalRepository extends JpaRepository<Hospital, Long> {
    Optional<Hospital> findByGorzdravId(long gorzdravId);
}
