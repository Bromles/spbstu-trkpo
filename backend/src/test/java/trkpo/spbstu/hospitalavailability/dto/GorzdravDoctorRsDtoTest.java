package trkpo.spbstu.hospitalavailability.dto;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class GorzdravDoctorRsDtoTest {
    @Test
    public void testGorzdravId() {
        GorzdravDoctorRsDto dto = new GorzdravDoctorRsDto(1L, "TestName");
        assertEquals(1L, dto.getGorzdravId());
    }

    @Test
    public void testName() {
        GorzdravDoctorRsDto dto = new GorzdravDoctorRsDto(1L, "TestName");
        assertEquals("TestName", dto.getName());
    }
}
