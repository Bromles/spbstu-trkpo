package trkpo.spbstu.hospitalavailability.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.testcontainers.shaded.org.apache.commons.lang3.RandomUtils;
import trkpo.spbstu.hospitalavailability.entity.Hospital;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static trkpo.spbstu.hospitalavailability.utils.HospitalUtils.getHospital;

@ExtendWith(MockitoExtension.class)
public class HospitalRepositoryTests {

    @Mock
    private HospitalRepository hospitalRepository;
    @InjectMocks
    private Hospital hospital = new Hospital();
    private static final long GORZDRAV_ID = RandomUtils.nextLong();

    @Test
    void testFindByGorzdravId() {
        hospital = getHospital();
        hospital.setGorzdravId(GORZDRAV_ID);
        when(hospitalRepository.findByGorzdravId(GORZDRAV_ID)).thenReturn(Optional.of(hospital));

        Optional<Hospital> result = hospitalRepository.findByGorzdravId(1);
        assertTrue(result.isPresent());
        assertEquals(result.get(), hospital, "Пришел не правильный ответ");
        verify(hospitalRepository, times(1)).findByGorzdravId(GORZDRAV_ID);
    }

    @Test
    void testNotFoundByKeycloakId() {
        when(hospitalRepository.findByGorzdravId(GORZDRAV_ID)).thenReturn(Optional.empty());
        Optional<Hospital> result = hospitalRepository.findByGorzdravId(GORZDRAV_ID);
        assertTrue(result.isEmpty(), "Пришел не пустой ответ");
        verify(hospitalRepository, times(1)).findByGorzdravId(GORZDRAV_ID);
    }
}
