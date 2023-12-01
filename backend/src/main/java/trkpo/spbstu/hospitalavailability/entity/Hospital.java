package trkpo.spbstu.hospitalavailability.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "hospital")
@NoArgsConstructor
public class Hospital {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;
    @Column(name = "gorzdrav_id", nullable = false, unique = true)
    private long gorzdravId;
    @Column(name = "latitude", nullable = false)
    private double latitude;
    @Column(name = "district_id", nullable = false)
    private long districtId;
    @Column(name = "address", nullable = false)
    private String address;
    @Column(name = "full_name", nullable = false)
    private String fullName;
    @Column(name = "short_name", nullable = false)
    private String shortName;
    @Column(name = "phone", nullable = false)
    private String phone;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name="district_id", referencedColumnName="id", nullable = false, insertable=false, updatable=false)
    private District district;
}
