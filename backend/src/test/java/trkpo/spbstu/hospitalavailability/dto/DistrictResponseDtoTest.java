package trkpo.spbstu.hospitalavailability.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DistrictResponseDtoTest {

    @Test
    void testDistrictResponseDto() {
        DistrictResponseDto dto = new DistrictResponseDto();
        dto.setId(1L);
        assertEquals(1L, dto.getId());
        dto.setGorzdravId(2L);
        assertEquals(2L, dto.getGorzdravId());
        dto.setName("TestName");
        assertEquals("TestName", dto.getName());
    }

}
