package trkpo.spbstu.hospitalavailability.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GorzdravSpecialtiesDtoTest {

    @Test
    void testGorzdravHospitalRs() {
        GorzdravSpecialtiesDto dto = new GorzdravSpecialtiesDto(1L, 20L, "Test Name");
        assertEquals(Long.valueOf(1L), dto.getId());
        assertEquals(Long.valueOf(20L), dto.getCountFreeTicket());
        assertEquals("Test Name", dto.getName());
    }

}

