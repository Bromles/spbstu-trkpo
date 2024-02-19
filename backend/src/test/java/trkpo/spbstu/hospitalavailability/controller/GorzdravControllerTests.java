package trkpo.spbstu.hospitalavailability.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.testcontainers.shaded.org.apache.commons.lang3.RandomStringUtils;
import org.testcontainers.shaded.org.apache.commons.lang3.RandomUtils;
import trkpo.spbstu.hospitalavailability.dto.*;
import trkpo.spbstu.hospitalavailability.service.DistrictService;
import trkpo.spbstu.hospitalavailability.service.GorzdravService;
import trkpo.spbstu.hospitalavailability.service.HospitalService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GorzdravControllerTests {

    @Mock
    private DistrictService districtService;
    @Mock
    private HospitalService hospitalService;
    @Mock
    private GorzdravService gorzdravService;
    private GorzdravController gorzdravController;
    private static final Long ID = 1L;
    private static final Long GORZDRAV_ID = 2L;
    private static final String NAME = RandomStringUtils.randomAlphabetic(5);
    private static final long DIRECTION_ID = RandomUtils.nextLong();
    private static final double LATITUDE = RandomUtils.nextDouble();
    private static final double LONGITUDE = RandomUtils.nextDouble();
    private static final String ADDRESS = RandomStringUtils.randomAlphabetic(5);
    private static final String PHONE = RandomStringUtils.randomAlphabetic(5);
    private static final String FULL_NAME = RandomStringUtils.randomAlphabetic(5);
    private static final String SHORT_NAME = RandomStringUtils.randomAlphabetic(5);
    private static final String DOCTOR_NAME = RandomStringUtils.randomAlphabetic(5);
    private static final String DIRECTION_NAME =  RandomStringUtils.randomAlphabetic(5);

    @BeforeEach
    void setUp() {
        gorzdravController = new GorzdravController(districtService, hospitalService, gorzdravService);
    }

    @Test
    void testGetDistricts() {
        DistrictResponseDto districtResponseDto = new DistrictResponseDto();
        districtResponseDto.setId(ID);
        districtResponseDto.setGorzdravId(GORZDRAV_ID);
        districtResponseDto.setName(NAME);
        when(districtService.findAll()).thenReturn(List.of(districtResponseDto));
        List<DistrictResponseDto> result = gorzdravController.getDistricts();
        assertEquals(result, List.of(districtResponseDto));
        verify(districtService, times(1)).findAll();
    }

    @Test
    void testGetDoctorsBySpecialityId() {
        GorzdravDoctorRsDto gorzdravDoctorRsDto = new GorzdravDoctorRsDto(GORZDRAV_ID, NAME);
        when(gorzdravService.getDoctorsBySpecialityId(anyLong(), anyLong())).thenReturn(List.of(gorzdravDoctorRsDto));
        List<GorzdravDoctorRsDto> result = gorzdravController.getDoctorsBySpecialityId(GORZDRAV_ID, GORZDRAV_ID);
        assertEquals(result, List.of(gorzdravDoctorRsDto));
        verify(gorzdravService, times(1)).getDoctorsBySpecialityId(GORZDRAV_ID, GORZDRAV_ID);
    }

    @Test
    void testUpdateDistricts() {
        gorzdravController.updateDistricts();
        verify(districtService, times(1)).updateAll();
    }

    @Test
    void testUpdateHospitals() {
        gorzdravController.updateHospitals();
        verify(hospitalService, times(1)).updateAll();
    }

    @Test
    void testGetHospitals() {
        HospitalResponseDto hospitalResponseDto = new HospitalResponseDto();
        hospitalResponseDto.setId(ID);
        hospitalResponseDto.setGorzdravId(GORZDRAV_ID);
        hospitalResponseDto.setAddress(ADDRESS);
        hospitalResponseDto.setDistrictId(DIRECTION_ID);
        hospitalResponseDto.setPhone(PHONE);
        hospitalResponseDto.setFullName(FULL_NAME);
        hospitalResponseDto.setShortName(SHORT_NAME);
        hospitalResponseDto.setLongitude(LONGITUDE);
        hospitalResponseDto.setLatitude(LATITUDE);
        when(hospitalService.findAll()).thenReturn(List.of(hospitalResponseDto));
        List<HospitalResponseDto> result = gorzdravController.getHospitals();
        assertEquals(result, List.of(hospitalResponseDto));
        verify(hospitalService, times(1)).findAll();
    }

    @Test
    void testGetSpecialties() {
        GorzdravSpecialtiesDto dto = new GorzdravSpecialtiesDto(ID, 20L, NAME);
        when(gorzdravService.getSpecialties(anyLong())).thenReturn(List.of(dto));
        List<GorzdravSpecialtiesDto> result = gorzdravController.getSpecialties(GORZDRAV_ID);
        assertEquals(result, List.of(dto));
        verify(gorzdravService, times(1)).getSpecialties(GORZDRAV_ID);
    }

    @Test
    void testGetTrackingInfo() {
        TrackingInfoRsDto trackingInfoRsDto = new TrackingInfoRsDto(DIRECTION_NAME, DOCTOR_NAME);
        when(gorzdravService.getTrackingInfo(anyLong(), anyLong(), anyLong())).thenReturn(trackingInfoRsDto);
        TrackingInfoRsDto result = gorzdravController.getTrackingInfo(ID, DIRECTION_ID, GORZDRAV_ID);
        assertEquals(result, trackingInfoRsDto);
        verify(gorzdravService, times(1)).getTrackingInfo(ID, DIRECTION_ID, GORZDRAV_ID);
    }
}

