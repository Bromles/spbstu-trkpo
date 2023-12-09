package trkpo.spbstu.hospitalavailability.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
public class ClientRequestDto {
    @NotNull
    private String keycloakId;

    @NotNull
    @Email
    private String email;
}
