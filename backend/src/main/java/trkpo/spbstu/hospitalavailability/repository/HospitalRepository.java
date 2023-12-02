package trkpo.spbstu.hospitalavailability.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import trkpo.spbstu.hospitalavailability.entity.Hospital;

@Repository
public interface HospitalRepository extends JpaRepository<Hospital, Long> {
    @Query(value = "select h from Hospital h where h.gorzdravId = ?1")
    Optional<Hospital> findByGorzdravId(long gorzdravId);
}
