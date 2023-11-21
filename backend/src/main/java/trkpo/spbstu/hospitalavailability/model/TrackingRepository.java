package trkpo.spbstu.hospitalavailability.model;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TrackingRepository extends JpaRepository<Tracking, Long> {
    List<Tracking> findByIsFinishedFalse();
}
