package trkpo.spbstu.hospitalavailability.dto;

import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.assertEquals;

public class GorzdravSpecialtiesDtoTest {
    @Test
    public void testGorzdravHospitalRs() {
        GorzdravSpecialtiesDto dto = new GorzdravSpecialtiesDto(1L, 20L, "Test Name");
        assertEquals(Long.valueOf(1L), dto.getId());
        assertEquals(Long.valueOf(20L), dto.getCountFreeTicket());
        assertEquals("Test Name", dto.getName());
    }

}

