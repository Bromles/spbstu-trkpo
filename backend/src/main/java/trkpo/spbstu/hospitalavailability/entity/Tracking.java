package trkpo.spbstu.hospitalavailability.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import trkpo.spbstu.hospitalavailability.dto.TrackingRequestDto;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(
        name = "tracking",
        indexes = {
                //при конфликте на сохранение будем ставить isFinished = false и менять время создания записи
                @Index(name = "tracking__multi_uq_idx",
                        columnList = "direction_id, doctor_id, client_id, hospital_id",
                        unique = true
                )
        }
)
@NoArgsConstructor
public class Tracking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;
    @Column(name = "direction_id", nullable = false)
    private long directionId;
    @Column(name = "doctor_id")
    private Long doctorId;
    @Column(name = "is_finished", nullable = false)
    private boolean isFinished;

    @Column(name = "date", nullable = false, columnDefinition = "TIMESTAMP")
    private LocalDateTime date;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false, insertable = false, updatable = false)
    private Client client;

    @ManyToOne
    @JoinColumn(name = "hospital_id", nullable = false, insertable = false, updatable = false)
    private Hospital hospital;

    public Tracking(TrackingRequestDto requestDto, Hospital hospital, Client client) {
        this.directionId = requestDto.getDirectionId();
        this.doctorId = requestDto.getDoctorId();
        this.client = client;
        this.hospital = hospital;
    }
}
