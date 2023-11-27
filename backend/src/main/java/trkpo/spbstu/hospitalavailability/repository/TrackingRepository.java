package trkpo.spbstu.hospitalavailability.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import trkpo.spbstu.hospitalavailability.entity.Tracking;
import java.util.List;

public interface TrackingRepository extends JpaRepository<Tracking, Long> {
    List<Tracking> findByIsFinishedFalse();

    @Modifying
    @Query("DELETE FROM Tracking t WHERE t.id = :id")
    long deleteByTrackingId(@Param("id") Long id);
}
