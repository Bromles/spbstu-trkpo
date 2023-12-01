package trkpo.spbstu.hospitalavailability.dto;

import lombok.Data;

@Data
public class HospitalResponseDto {
    private long id;
    private long gorzdravId;
    private double latitude;
    private long districtId;
    private String address;
    private String fullName;
    private String shortName;
    private String phone;
}
