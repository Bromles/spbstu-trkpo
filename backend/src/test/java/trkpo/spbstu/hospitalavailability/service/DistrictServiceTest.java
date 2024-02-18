package trkpo.spbstu.hospitalavailability.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.transaction.support.TransactionTemplate;
import trkpo.spbstu.hospitalavailability.dto.DistrictResponseDto;
import trkpo.spbstu.hospitalavailability.dto.GorzdravDistrictRsDto;
import trkpo.spbstu.hospitalavailability.entity.District;
import trkpo.spbstu.hospitalavailability.mapper.DistrictMapper;
import trkpo.spbstu.hospitalavailability.mapper.DistrictMapperImpl;
import trkpo.spbstu.hospitalavailability.repository.DistrictRepository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DistrictServiceTest {

    @Mock
    private DistrictRepository districtRepository;

    private final DistrictMapper districtMapper = new DistrictMapperImpl();

    @Mock
    private GorzdravService gorzdravService;

    @Mock
    private TransactionTemplate simpleTransactionTemplate;

    @Mock
    private EntityManager entityManager;

    private DistrictService districtService;

    private final Long ID = 1L;

    private District getDefaultDistrict() {
        District district = new District();
        district.setName("District");
        return district;
    }

    private final GorzdravDistrictRsDto GORZDRAV_DISTRICT_RS = new GorzdravDistrictRsDto(ID, "gorzdravId");

    @BeforeEach
    void setup() {
        districtService = new DistrictService(districtRepository, districtMapper, gorzdravService, simpleTransactionTemplate);
        ReflectionTestUtils.setField(districtService, "entityManager", entityManager);
    }


    @Test
    void testFindAll() {
        List<District> districts = List.of(getDefaultDistrict());
        when(districtRepository.findAll()).thenReturn(districts);

        List<DistrictResponseDto> result = districtService.findAll();

        assertEquals(districtMapper.toDistrictDto(districts), result);
        verify(districtRepository, times(1)).findAll();
    }

    @Test
    void testInsert() {
        List<GorzdravDistrictRsDto> gorzdravDistrictRsDtos = List.of(GORZDRAV_DISTRICT_RS);
        District district = getDefaultDistrict();

        when(districtRepository.findByGorzdravId(1)).thenReturn(Optional.of(district));

        districtService.insertOrUpdate(gorzdravDistrictRsDtos);
        verify(districtRepository, times(1)).findByGorzdravId(1);
        verify(entityManager).merge(district);
    }

    @Test
    void testUpdate() {
        List<GorzdravDistrictRsDto> gorzdravDistrictRsDtos = List.of(GORZDRAV_DISTRICT_RS);
        when(districtRepository.findByGorzdravId(1)).thenReturn(Optional.empty());

        districtService.insertOrUpdate(gorzdravDistrictRsDtos);
        verify(districtRepository, times(1)).findByGorzdravId(1);
        verify(entityManager).persist(any(District.class));
    }

    @Test
    void testUpdateAll() {
        List<GorzdravDistrictRsDto> districts = List.of(GORZDRAV_DISTRICT_RS);
        when(gorzdravService.getDistricts()).thenReturn(districts);
        districtService.updateAll();

        verify(simpleTransactionTemplate, times(1)).executeWithoutResult(any());
    }

}

