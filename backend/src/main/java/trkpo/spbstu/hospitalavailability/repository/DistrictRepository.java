package trkpo.spbstu.hospitalavailability.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import trkpo.spbstu.hospitalavailability.entity.District;

public interface DistrictRepository extends JpaRepository<District, Long> {
}
