package trkpo.spbstu.hospitalavailability.dto;

import lombok.Cleanup;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.shaded.org.apache.commons.lang3.RandomUtils;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class TrackingRequestDtoTests {

    private Validator validator;
    private static final long DOCTOR_ID = RandomUtils.nextLong();
    private static final long HOSPITAL_ID = RandomUtils.nextLong();
    private static final long DIRECTION_ID = RandomUtils.nextLong();
    private static final Long NEGATIVE_ID = -1L;
    private static final String VALIDATION_NEGATIVE_ERROR = "must be greater than or equal to 0";
    private static final String VALIDATION_NOT_NULL_ERROR = "must not be null";

    @BeforeEach
    void setUp() {
        @Cleanup
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        this.validator = factory.getValidator();
    }

    @Test
    void testDirectionalIdIsNegative() {
        TrackingRequestDto trackingRequestDto = new TrackingRequestDto();
        trackingRequestDto.setHospitalId(HOSPITAL_ID);
        trackingRequestDto.setDoctorId(DOCTOR_ID);
        trackingRequestDto.setDirectionId(NEGATIVE_ID);
        Set<ConstraintViolation<TrackingRequestDto>> violations = this.validator.validate(trackingRequestDto);
        assertEquals(1, violations.size());
        assertEquals(violations.iterator().next().getMessage(), VALIDATION_NEGATIVE_ERROR, "The validator did not work");
    }

    @Test
    void testDirectionalIdIsNull() {
        TrackingRequestDto trackingRequestDto = new TrackingRequestDto();
        trackingRequestDto.setHospitalId(HOSPITAL_ID);
        trackingRequestDto.setDoctorId(DOCTOR_ID);
        Set<ConstraintViolation<TrackingRequestDto>> violations = this.validator.validate(trackingRequestDto);
        assertEquals(1, violations.size());
        assertEquals(violations.iterator().next().getMessage(),VALIDATION_NOT_NULL_ERROR, "The validator did not work");
    }

    @Test
    void testHospitalIdIsNull() {
        TrackingRequestDto trackingRequestDto = new TrackingRequestDto();
        trackingRequestDto.setDoctorId(DOCTOR_ID);
        trackingRequestDto.setDirectionId(DIRECTION_ID);
        Set<ConstraintViolation<TrackingRequestDto>> violations = this.validator.validate(trackingRequestDto);
        assertEquals(1, violations.size());
        assertEquals(violations.iterator().next().getMessage(), VALIDATION_NOT_NULL_ERROR, "The validator did not work");
    }

    @Test
    void testDoctorIdIsNegative() {
        TrackingRequestDto trackingRequestDto = new TrackingRequestDto();
        trackingRequestDto.setHospitalId(HOSPITAL_ID);
        trackingRequestDto.setDoctorId(NEGATIVE_ID);
        trackingRequestDto.setDirectionId(DIRECTION_ID);
        Set<ConstraintViolation<TrackingRequestDto>> violations = this.validator.validate(trackingRequestDto);
        assertEquals(1, violations.size());
        assertEquals(violations.iterator().next().getMessage(), VALIDATION_NEGATIVE_ERROR, "The validator did not work");
    }

    @Test
    void testHospitalIdIsNegative() {
        TrackingRequestDto trackingRequestDto = new TrackingRequestDto();
        trackingRequestDto.setHospitalId(NEGATIVE_ID);
        trackingRequestDto.setDoctorId(DOCTOR_ID);
        trackingRequestDto.setDirectionId(DIRECTION_ID);
        Set<ConstraintViolation<TrackingRequestDto>> violations = this.validator.validate(trackingRequestDto);
        assertEquals(1, violations.size());
        assertEquals(violations.iterator().next().getMessage(), VALIDATION_NEGATIVE_ERROR, "The validator did not work");
    }

    @Test
    void testValidClientRequestDto() {
        TrackingRequestDto trackingRequestDto = new TrackingRequestDto();
        trackingRequestDto.setHospitalId(HOSPITAL_ID);
        trackingRequestDto.setDoctorId(DOCTOR_ID);
        trackingRequestDto.setDirectionId(DIRECTION_ID);
        Set<ConstraintViolation<TrackingRequestDto>> violations = this.validator.validate(trackingRequestDto);
        assertTrue(violations.isEmpty());
        assertAll("Checking the response fields",
                () -> assertEquals(trackingRequestDto.getDoctorId(), DOCTOR_ID, "DOCTOR_ID is not equal"),
                () -> assertEquals(trackingRequestDto.getHospitalId(), HOSPITAL_ID, "HOSPITAL_ID is not equal"),
                () -> assertEquals(trackingRequestDto.getDirectionId(), DIRECTION_ID, "DIRECTIONAL_ID is not equal")
        );
    }
}
