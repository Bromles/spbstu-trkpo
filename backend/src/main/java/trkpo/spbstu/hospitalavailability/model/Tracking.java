package trkpo.spbstu.hospitalavailability.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "tracking")
@NoArgsConstructor
public class Tracking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tracking_id", nullable = false)
    private Long id;
    @Column(name = "direction_id", nullable = false)
    private Long directionId;
    @Column(name = "doctor_id", nullable = false)
    private Long doctorId;
    @Column(name = "is_finished", nullable = false)
    private Boolean isFinished;

    @ManyToOne
    @JoinColumn(name = "client_id", insertable = false, updatable = false)
    private Client client;

    @ManyToOne
    @JoinColumn(name = "hospital_id", insertable = false, updatable = false)
    private Hospital hospitalId;

}
