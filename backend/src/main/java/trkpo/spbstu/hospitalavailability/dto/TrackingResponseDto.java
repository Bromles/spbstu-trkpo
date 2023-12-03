package trkpo.spbstu.hospitalavailability.dto;

import lombok.Data;

@Data
public class TrackingResponseDto {
    private long id;
    private long directionId;
    private Long doctorId;
    private boolean isFinished;
    private long clientId;
    private long hospitalId;
    private long hospitalGorzdravId;
    private String hospitalFullName;
}
