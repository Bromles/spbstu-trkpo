package trkpo.spbstu.hospitalavailability.dto;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class GorzdravHospitalRsDtoTest {

    @Test
    public void testGorzdravHospitalRs() {
        GorzdravHospitalRsDto dto = new GorzdravHospitalRsDto(1L, 55.75, 37.61, 2L, "Test Address", "Test Full Name", "Test Short Name", "+7-900-000-1122");
        assertEquals(1L, dto.getGorzdravId());
        assertEquals(55.75, dto.getLongitude(), 0.01);
        assertEquals(37.61, dto.getLatitude(), 0.01);
        assertEquals(2L, dto.getDistrictId());
        assertEquals("Test Address", dto.getAddress());
        assertEquals("Test Full Name", dto.getFullName());
        assertEquals("Test Short Name", dto.getShortName());
        assertEquals("+7-900-000-1122", dto.getPhone());
    }

}

