package trkpo.spbstu.hospitalavailability.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import lombok.Data;

@Data
public class TrackingInfoRqDto {

    @PositiveOrZero
    @NotNull
    private Long hospitalId;
    @PositiveOrZero
    @NotNull
    private Long directionId;
    private Long doctorId;
}
