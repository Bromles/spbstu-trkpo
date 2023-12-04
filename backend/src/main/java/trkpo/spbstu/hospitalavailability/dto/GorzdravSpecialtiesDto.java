package trkpo.spbstu.hospitalavailability.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GorzdravSpecialtiesDto {
    private Long id;
    private Long countFreeTicket;
    private String name;
}