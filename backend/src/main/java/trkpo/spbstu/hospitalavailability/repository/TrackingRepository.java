package trkpo.spbstu.hospitalavailability.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import trkpo.spbstu.hospitalavailability.entity.Tracking;

import java.util.List;
import java.util.UUID;

public interface TrackingRepository extends JpaRepository<Tracking, Long> {
    List<Tracking> findByIsFinishedFalse();

    List<Tracking> findByIsFinishedFalseAndClientKeycloakId(UUID id);

    @NonNull
    @SuppressWarnings({"overrides", "unchecked"})
    @Modifying
    @Query(value = "insert into tracking(date, direction_id, doctor_id, is_finished, client_id, hospital_id) values " +
            "(:#{#track.date}, :#{#track.directionId}, :#{#track.doctorId}, :#{#track.isFinished}, :#{#track.client.id}, :#{#track.hospital.id})" +
            "on conflict(direction_id, doctor_id, client_id, hospital_id) do update SET " +
            "date = excluded.date, is_finished = excluded.is_finished returning *", nativeQuery = true)
    Tracking save(@NonNull Tracking track);

    @Modifying
    @Query("update Tracking t set t.isFinished = :finished where t.id = :id")
    void updateTrackingFinishedById(boolean finished, long id);
}
