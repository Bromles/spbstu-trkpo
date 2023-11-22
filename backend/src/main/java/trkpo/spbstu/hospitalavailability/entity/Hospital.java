package trkpo.spbstu.hospitalavailability.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "hospital")
@NoArgsConstructor
public class Hospital {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "gorzdrav_id", nullable = false, unique = true)
    private Long gorzdravId;
    @Column(name = "latitude", nullable = false)
    private Double latitude;
    @Column(name = "district_id", nullable = false)
    private Long districtId;
    @Column(name = "address", nullable = false)
    private String address;
    @Column(name = "full_name", nullable = false)
    private String fullName;
    @Column(name = "short_name", nullable = false)
    private String shortName;
    @Column(name = "phone", nullable = false)
    private String phone;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "district_id", insertable = false, updatable = false)
    private Set<District> districts = new HashSet<>();
}
