package trkpo.spbstu.hospitalavailability.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GorzdravDoctorRsDtoTest {

    @Test
    void testGorzdravDoctorRsDto() {
        GorzdravDoctorRsDto dto = new GorzdravDoctorRsDto(1L, "TestName");
        assertEquals(1L, dto.getGorzdravId());
        assertEquals("TestName", dto.getName());
    }

}
