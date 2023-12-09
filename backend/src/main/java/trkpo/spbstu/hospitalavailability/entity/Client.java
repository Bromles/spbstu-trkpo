package trkpo.spbstu.hospitalavailability.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(
        name = "client",
        indexes = {
                @Index(name = "client__multi_uq_idx",
                        columnList = "keycloak_id, email",
                        unique = true
                )
        }
)
@NoArgsConstructor
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;

    @Column(name = "keycloak_id", nullable = false, unique = true)
    private UUID keycloakId;

    @Column(name = "email", nullable = false, unique = true)
    private String email;
}
