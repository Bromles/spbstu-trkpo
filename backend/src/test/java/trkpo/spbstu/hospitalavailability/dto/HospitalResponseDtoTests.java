package trkpo.spbstu.hospitalavailability.dto;

import org.junit.jupiter.api.Test;
import org.testcontainers.shaded.org.apache.commons.lang3.RandomStringUtils;
import org.testcontainers.shaded.org.apache.commons.lang3.RandomUtils;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class HospitalResponseDtoTests {

    private static final long ID = RandomUtils.nextLong();
    private static final long GORZDRAV_ID = RandomUtils.nextLong();
    private static final long DIRECTION_ID = RandomUtils.nextLong();
    private static final double LATITUDE = RandomUtils.nextDouble();
    private static final double LONGITUDE = RandomUtils.nextDouble();
    private static final String ADDRESS = RandomStringUtils.randomAlphabetic(5);
    private static final String PHONE = RandomStringUtils.randomAlphabetic(5);
    private static final String FULL_NAME = RandomStringUtils.randomAlphabetic(5);
    private static final String SHORT_NAME = RandomStringUtils.randomAlphabetic(5);

    @Test
    void testHospitalResponseDto() {
        HospitalResponseDto dto = new HospitalResponseDto();
        dto.setId(ID);
        dto.setGorzdravId(GORZDRAV_ID);
        dto.setAddress(ADDRESS);
        dto.setDistrictId(DIRECTION_ID);
        dto.setPhone(PHONE);
        dto.setFullName(FULL_NAME);
        dto.setShortName(SHORT_NAME);
        dto.setLongitude(LONGITUDE);
        dto.setLatitude(LATITUDE);
        assertAll("Checking the response fields",
                () -> assertEquals(dto.getId(), ID, "ID is not equal"),
                () -> assertEquals(dto.getGorzdravId(), GORZDRAV_ID, "GORZDRAV_ID is not equal"),
                () -> assertEquals(dto.getAddress(), ADDRESS, "ADDRESS is not equal"),
                () -> assertEquals(dto.getDistrictId(), DIRECTION_ID, "DIRECTION_ID is not equal"),
                () -> assertEquals(dto.getPhone(), PHONE, "PHONE is not equal"),
                () -> assertEquals(dto.getFullName(), FULL_NAME, "FULL_NAME is not equal"),
                () -> assertEquals(dto.getShortName(), SHORT_NAME, "SHORT_NAME is not equal"),
                () -> assertEquals(dto.getLatitude(), LATITUDE, "LATITUDE is not equal"),
                () -> assertEquals(dto.getLongitude(), LONGITUDE, "LONGITUDE is not equal")
        );
    }
}
