package trkpo.spbstu.hospitalavailability.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import trkpo.spbstu.hospitalavailability.entity.District;

import java.util.Optional;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DistrictRepositoryTest {
    private static final long ID = new Random().nextLong();
    private static final long GORZDRAV_ID = new Random().nextLong();
    private static final String NAME = "test district";
    @Mock
    private DistrictRepository districtRepository;
    @InjectMocks
    private District district = new District();

    @Test
    void findByGorzdravIdTest() {
        district.setId(ID);
        district.setGorzdravId(GORZDRAV_ID);
        district.setName(NAME);

        when(districtRepository.findByGorzdravId(ID)).thenReturn(Optional.of(district));

        Optional<District> foundDistrict = districtRepository.findByGorzdravId(ID);

        verify(districtRepository, times(1)).findByGorzdravId(ID);
        assertTrue(foundDistrict.isPresent());
        assertEquals(district, foundDistrict.get());
    }

    @Test
    void findByGorzdravIdNotFoundTest() {
        when(districtRepository.findByGorzdravId(GORZDRAV_ID)).thenReturn(Optional.empty());

        Optional<District> foundDistrict = districtRepository.findByGorzdravId(GORZDRAV_ID);

        verify(districtRepository, times(1)).findByGorzdravId(GORZDRAV_ID);
        assertTrue(foundDistrict.isEmpty());
    }

}
