package trkpo.spbstu.hospitalavailability.dto;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class DistrictResponseDtoTest {

    @Test
    public void testId() {
        DistrictResponseDto dto = new DistrictResponseDto();
        dto.setId(1L);
        assertEquals(1L, dto.getId());
    }

    @Test
    public void testGorzdravId() {
        DistrictResponseDto dto = new DistrictResponseDto();
        dto.setGorzdravId(2L);
        assertEquals(2L, dto.getGorzdravId());
    }

    @Test
    public void testName() {
        DistrictResponseDto dto = new DistrictResponseDto();
        dto.setName("TestName");
        assertEquals("TestName", dto.getName());
    }
}
