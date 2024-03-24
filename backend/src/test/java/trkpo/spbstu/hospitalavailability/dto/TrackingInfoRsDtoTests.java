package trkpo.spbstu.hospitalavailability.dto;

import org.junit.jupiter.api.Test;
import org.testcontainers.shaded.org.apache.commons.lang3.RandomStringUtils;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class TrackingInfoRsDtoTests {

    private static final String DOCTOR_NAME = RandomStringUtils.randomAlphabetic(5);
    private static final String DIRECTION_NAME = RandomStringUtils.randomAlphabetic(5);

    @Test
    void testTrackingInfoRsDto() {
        TrackingInfoRsDto dto = new TrackingInfoRsDto(DIRECTION_NAME, DOCTOR_NAME);
        assertAll("Checking the response fields",
                () -> assertEquals(dto.getDirectionName(), DIRECTION_NAME, "DIRECTION_NAME is not equal"),
                () -> assertEquals(dto.getDoctorName(), DOCTOR_NAME, "DOCTOR_NAME is not equal"));
    }
}
