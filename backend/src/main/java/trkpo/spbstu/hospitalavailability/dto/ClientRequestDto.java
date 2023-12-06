package trkpo.spbstu.hospitalavailability.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ClientRequestDto {
    @NotNull
    private String keycloakId;
    @NotNull
    private String email;
}
