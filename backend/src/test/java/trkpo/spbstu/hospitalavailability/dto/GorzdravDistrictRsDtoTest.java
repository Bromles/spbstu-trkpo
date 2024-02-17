package trkpo.spbstu.hospitalavailability.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GorzdravDistrictRsDtoTest {

    @Test
    void testGorzdravId() {
        GorzdravDistrictRsDto dto = new GorzdravDistrictRsDto(1L, "TestName");
        assertEquals(1L, dto.getGorzdravId());
        assertEquals("TestName", dto.getName());
    }

}

