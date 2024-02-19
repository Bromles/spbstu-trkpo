package trkpo.spbstu.hospitalavailability.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.support.TransactionTemplate;
import org.testcontainers.shaded.org.apache.commons.lang3.RandomStringUtils;
import org.testcontainers.shaded.org.apache.commons.lang3.RandomUtils;
import trkpo.spbstu.hospitalavailability.dto.GorzdravHospitalRsDto;
import trkpo.spbstu.hospitalavailability.dto.HospitalResponseDto;
import trkpo.spbstu.hospitalavailability.entity.Hospital;
import trkpo.spbstu.hospitalavailability.mapper.HospitalMapper;
import trkpo.spbstu.hospitalavailability.repository.HospitalRepository;

import javax.persistence.EntityManager;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static trkpo.spbstu.hospitalavailability.utils.HospitalUtils.getHospital;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.anyList;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.any;

@ExtendWith(MockitoExtension.class)
class HospitalServiceTests {

    @Mock
    private GorzdravService gorzdravService;
    @Mock
    private HospitalRepository hospitalRepository;
    @Mock
    private HospitalMapper hospitalMapper;
    @Mock
    private TransactionTemplate simpleTransactionTemplate;
    @Mock
    private EntityManager entityManager;
    private HospitalService hospitalService;
    private static final String FULL_NAME = RandomStringUtils.randomAlphabetic(5);
    private static final String SHORT_NAME = RandomStringUtils.randomAlphabetic(5);
    private static final String ADDRESS = RandomStringUtils.randomAlphabetic(5);
    private static final Long ID = RandomUtils.nextLong();
    private static final Long DISTRICT_ID = RandomUtils.nextLong();
    private static final String PHONE = "(812) 246-39-90";
    private static final Double LONGITUDE = RandomUtils.nextDouble();
    private static final Double LATITUDE = RandomUtils.nextDouble();

    @BeforeEach
    void setup() {
        hospitalService = new HospitalService(gorzdravService, hospitalRepository,
                hospitalMapper, simpleTransactionTemplate, entityManager);
    }

    @Test
    void testFindAll() {
       when(hospitalRepository.findAll()).thenReturn(List.of());
       HospitalResponseDto mockHospitalResponseDto = mock(HospitalResponseDto.class);
       when(hospitalMapper.toHospitalDto(anyList())).thenReturn(List.of(mockHospitalResponseDto));
       List<HospitalResponseDto> hospitalResponseDto = hospitalService.findAll();
       assertThat(hospitalResponseDto).containsExactly(mockHospitalResponseDto);
    }

    @Test
    void testFindAllEmpty() {
        when(hospitalRepository.findAll()).thenReturn(List.of());
        when(hospitalMapper.toHospitalDto(anyList())).thenReturn(List.of());
        List<HospitalResponseDto> hospitalResponseDto = hospitalService.findAll();
        assertThat(hospitalResponseDto).isEmpty();
    }

    @Test
    void testInsert() {
        GorzdravHospitalRsDto dto = new GorzdravHospitalRsDto(ID, LONGITUDE, LATITUDE, DISTRICT_ID, ADDRESS, FULL_NAME, SHORT_NAME, PHONE);
        List<GorzdravHospitalRsDto> hospitals = List.of(dto);
        Hospital hospital = getHospital();
        hospital.setId(ID);
        when(hospitalRepository.findByGorzdravId(ID)).thenReturn(Optional.of(hospital));
        hospitalService.batchInsertOrUpdate(hospitals);
        verify(hospitalRepository, times(1)).findByGorzdravId(ID);
        verify(entityManager).merge(hospital);
    }

    @Test
    void testUpdate() {
        GorzdravHospitalRsDto dto = new GorzdravHospitalRsDto(ID, LONGITUDE, LATITUDE, DISTRICT_ID, ADDRESS, FULL_NAME, SHORT_NAME, PHONE);
        List<GorzdravHospitalRsDto> hospitals = List.of(dto);
        when(hospitalRepository.findByGorzdravId(ID)).thenReturn(Optional.empty());
        hospitalService.batchInsertOrUpdate(hospitals);
        verify(hospitalRepository, times(1)).findByGorzdravId(ID);
        verify(entityManager).persist(any(Hospital.class));
    }

    @Test
    void testUpdateAll() {
        GorzdravHospitalRsDto dto = new GorzdravHospitalRsDto(1L, 55.75, 37.61, 2L, "Test Address", "Test Full Name", "Test Short Name", "+7-900-000-1122");
        when(gorzdravService.getHospitals()).thenReturn(List.of(dto));
        hospitalService.updateAll();
        verify(simpleTransactionTemplate, times(1)).executeWithoutResult(any());
    }
}
