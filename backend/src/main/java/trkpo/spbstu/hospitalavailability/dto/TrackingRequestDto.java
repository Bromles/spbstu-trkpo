package trkpo.spbstu.hospitalavailability.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

@Data
public class TrackingRequestDto {
    @PositiveOrZero
    @NotNull
    private long directionId;
    @PositiveOrZero
    private Long doctorId;
    @PositiveOrZero
    @NotNull
    private long hospitalId;
}
