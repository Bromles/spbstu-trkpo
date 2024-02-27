package trkpo.spbstu.hospitalavailability.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GorzdravHospitalRsDto {
    private long gorzdravId;
    private double longitude;
    private double latitude;
    private long districtId;
    private String address;
    private String fullName;
    private String shortName;
    private String phone;
}
