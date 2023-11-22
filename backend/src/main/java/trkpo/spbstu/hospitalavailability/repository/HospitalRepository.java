package trkpo.spbstu.hospitalavailability.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import trkpo.spbstu.hospitalavailability.entity.Hospital;

public interface HospitalRepository extends JpaRepository<Hospital, Long> {
}
