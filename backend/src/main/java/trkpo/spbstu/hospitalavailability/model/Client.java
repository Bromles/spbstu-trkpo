package trkpo.spbstu.hospitalavailability.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "client")
@NoArgsConstructor
public class Client {
    @Id
    @GeneratedValue
    @Column(name = "client_id", nullable = false)
    private Long clientId;
    @Column(name = "keycloak_id", nullable = false)
    private Long keycloakId;
}
