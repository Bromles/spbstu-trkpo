package trkpo.spbstu.hospitalavailability.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.testcontainers.shaded.org.apache.commons.lang3.RandomStringUtils;
import org.testcontainers.shaded.org.apache.commons.lang3.RandomUtils;
import trkpo.spbstu.hospitalavailability.dto.TrackingRequestDto;
import trkpo.spbstu.hospitalavailability.dto.TrackingResponseDto;
import trkpo.spbstu.hospitalavailability.service.TrackingService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TrackingControllerTests {

    private static final long ID = RandomUtils.nextLong();
    private static final long GORZDRAV_ID = RandomUtils.nextLong();
    private static final long HOSPITAL_ID = RandomUtils.nextLong();
    private static final long DIRECTION_ID = RandomUtils.nextLong();
    private static final boolean FINISHED = RandomUtils.nextBoolean();
    private static final long CLIENT_ID = RandomUtils.nextLong();
    private static final long DOCTOR_ID = RandomUtils.nextLong();
    private static final String FULL_NAME = RandomStringUtils.randomAlphabetic(5);
    @Mock
    private TrackingService trackingService;
    private TrackingController trackingController;

    @BeforeEach
    void setUp() {
        trackingController = new TrackingController(trackingService);
    }

    @Test
    void testDeleteTracking() {
        long id = 1L;
        when(trackingService.deleteTracking(anyLong())).thenReturn(id);
        long resultId = trackingController.deleteTracking(id);
        assertEquals(resultId, id, "Invalid id");
        verify(trackingService, times(1)).deleteTracking(id);
    }

    @Test
    void testGetActiveUserTracking() {
        TrackingResponseDto trackingResponseDto = getTrackingResponseDto();
        when(trackingService.findUserActiveTracking(anyString())).thenReturn(List.of(trackingResponseDto));
        String keyClockId = RandomStringUtils.randomAlphabetic(5);
        List<TrackingResponseDto> trackingResponseDtoList = trackingController.getActiveUserTracking(keyClockId);
        assertEquals(trackingResponseDtoList, List.of(trackingResponseDto));
        verify(trackingService, times(1)).findUserActiveTracking(keyClockId);
    }

    @Test
    void testAddTracking() {
        TrackingResponseDto trackingResponseDto = getTrackingResponseDto();
        TrackingRequestDto trackingRequestDto = new TrackingRequestDto();
        trackingRequestDto.setHospitalId(HOSPITAL_ID);
        trackingRequestDto.setDoctorId(DOCTOR_ID);
        trackingRequestDto.setDirectionId(DIRECTION_ID);
        when(trackingService.addTracking(any(TrackingRequestDto.class))).thenReturn(trackingResponseDto);
        TrackingResponseDto result = trackingController.addTracking(trackingRequestDto);
        assertEquals(result, trackingResponseDto);
        verify(trackingService, times(1)).addTracking(trackingRequestDto);
    }

    private TrackingResponseDto getTrackingResponseDto() {
        TrackingResponseDto trackingResponseDto = new TrackingResponseDto();
        trackingResponseDto.setClientId(CLIENT_ID);
        trackingResponseDto.setFinished(FINISHED);
        trackingResponseDto.setDoctorId(DOCTOR_ID);
        trackingResponseDto.setDirectionId(DIRECTION_ID);
        trackingResponseDto.setHospitalId(HOSPITAL_ID);
        trackingResponseDto.setHospitalFullName(FULL_NAME);
        trackingResponseDto.setHospitalGorzdravId(GORZDRAV_ID);
        trackingResponseDto.setId(ID);
        return trackingResponseDto;
    }
}
