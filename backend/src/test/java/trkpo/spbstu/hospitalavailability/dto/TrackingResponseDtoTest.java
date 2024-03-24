package trkpo.spbstu.hospitalavailability.dto;

import org.junit.jupiter.api.Test;
import org.testcontainers.shaded.org.apache.commons.lang3.RandomStringUtils;
import org.testcontainers.shaded.org.apache.commons.lang3.RandomUtils;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class TrackingResponseDtoTest {

    private static final long ID = RandomUtils.nextLong();
    private static final long GORZDRAV_ID = RandomUtils.nextLong();
    private static final long HOSPITAL_ID = RandomUtils.nextLong();
    private static final long DIRECTION_ID = RandomUtils.nextLong();
    private static final boolean FINISHED = RandomUtils.nextBoolean();
    private static final long CLIENT_ID = RandomUtils.nextLong();
    private static final long DOCTOR_ID = RandomUtils.nextLong();
    private static final String FULL_NAME = RandomStringUtils.randomAlphabetic(5);

    @Test
    void testTrackingResponseDto() {
        TrackingResponseDto dto = new TrackingResponseDto();
        dto.setClientId(CLIENT_ID);
        dto.setFinished(FINISHED);
        dto.setDoctorId(DOCTOR_ID);
        dto.setDirectionId(DIRECTION_ID);
        dto.setHospitalId(HOSPITAL_ID);
        dto.setHospitalFullName(FULL_NAME);
        dto.setHospitalGorzdravId(GORZDRAV_ID);
        dto.setId(ID);
        assertAll("Checking the response fields",
                () -> assertEquals(dto.getId(), ID),
                () -> assertEquals(dto.getClientId(), CLIENT_ID),
                () -> assertEquals(dto.getDirectionId(), DIRECTION_ID),
                () -> assertEquals(dto.getDoctorId(), DOCTOR_ID),
                () -> assertEquals(dto.getHospitalGorzdravId(), GORZDRAV_ID),
                () -> assertEquals(dto.getHospitalId(), HOSPITAL_ID),
                () -> assertEquals(dto.getHospitalFullName(), FULL_NAME),
                () -> assertEquals(dto.isFinished(), FINISHED));
    }
}
