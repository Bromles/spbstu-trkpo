package trkpo.spbstu.hospitalavailability.dto;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class GorzdravDistrictRsDtoTest {

    @Test
    public void testGorzdravId() {
        GorzdravDistrictRsDto dto = new GorzdravDistrictRsDto(1L, "TestName");
        assertEquals(1L, dto.getGorzdravId());
    }

    @Test
    public void testName() {
        GorzdravDistrictRsDto dto = new GorzdravDistrictRsDto(1L, "TestName");
        assertEquals("TestName", dto.getName());
    }
}

