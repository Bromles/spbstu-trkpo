package trkpo.spbstu.hospitalavailability.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "district")
@NoArgsConstructor
public class District {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "gorzdrav_id", nullable = false)
    private Long gorzdravId;
    @Column(name = "name", nullable = false)
    private String name;
}
