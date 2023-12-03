package trkpo.spbstu.hospitalavailability.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;
import trkpo.spbstu.hospitalavailability.entity.Tracking;

import java.util.List;

public interface TrackingRepository extends JpaRepository<Tracking, Long> {
    List<Tracking> findByIsFinishedFalse();

    long removeById(Long id);

    List<Tracking> findByIsFinishedFalseAndClientId(Long id);

    @NonNull
    @SuppressWarnings({"overrides", "unchecked"})
    @Query(value = "insert into tracking(date, direction_id, doctor_id, is_finished, client_id, hospital_id) values " +
            "(:track.date, :track.direction_id, :track.doctorId, :track.is_finished, :track.client.id, :track.hospital.id) " +
            "on conflict(direction_id, doctor_id, client_id, hospital_id) do update SET " +
            "date = excluded.date, is_finished = excluded.is_finished returning *", nativeQuery = true)
    Tracking save(@NonNull @Param("track") Tracking track);

    @Modifying
    @Query("update Tracking t set t.isFinished = :finished where t.id = :id")
    void updateTrackingFinishedById(boolean finished, long id);
}
