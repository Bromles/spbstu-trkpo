package trkpo.spbstu.hospitalavailability.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import trkpo.spbstu.hospitalavailability.entity.Tracking;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static trkpo.spbstu.hospitalavailability.utils.TrackingUtils.getActiveTrackingWithDoctorId;

@ExtendWith(MockitoExtension.class)
public class TrackingRepositoryTests {

    @Mock
    private TrackingRepository trackingRepository;
    private static final UUID CLIENT_ID = UUID.randomUUID();

    @InjectMocks
    private Tracking tracking = new Tracking();

    @Test
    void testFindByIsFinishedFalse() {
        tracking = getActiveTrackingWithDoctorId();
        tracking.setFinished(false);
        when(trackingRepository.findByIsFinishedFalse()).thenReturn(List.of(tracking));

        List<Tracking> trackings = trackingRepository.findByIsFinishedFalse();
        assertFalse(trackings.isEmpty(),"Пришел пустой список");
        assertEquals(List.of(tracking), trackings, "Список не совпадает");
        verify(trackingRepository, times(1)).findByIsFinishedFalse();
    }

    @Test
    void testFindByIsFinishedFalseEmptyList() {
        when(trackingRepository.findByIsFinishedFalse()).thenReturn(List.of());
        List<Tracking> trackings = trackingRepository.findByIsFinishedFalse();
        assertTrue(trackings.isEmpty(),"Пришел не пустой список");
        verify(trackingRepository, times(1)).findByIsFinishedFalse();
    }

    @Test
    void testFindByIsFinishedFalseAndClientKeycloakId() {
        tracking = getActiveTrackingWithDoctorId();
        tracking.setFinished(false);
        tracking.getClient().setKeycloakId(CLIENT_ID);
        when(trackingRepository.findByIsFinishedFalseAndClientKeycloakId(CLIENT_ID)).thenReturn(List.of(tracking));

        List<Tracking> trackings = trackingRepository.findByIsFinishedFalseAndClientKeycloakId(CLIENT_ID);
        assertFalse(trackings.isEmpty(),"Пришел пустой список");
        assertEquals(List.of(tracking), trackings, "Список не совпадает");
        verify(trackingRepository, times(1)).findByIsFinishedFalseAndClientKeycloakId(CLIENT_ID);
    }

    @Test
    void testFindByIsFinishedFalseAndClientNotFound() {
        when(trackingRepository.findByIsFinishedFalseAndClientKeycloakId(CLIENT_ID)).thenReturn(List.of());
        List<Tracking> trackings = trackingRepository.findByIsFinishedFalseAndClientKeycloakId(CLIENT_ID);
        assertTrue(trackings.isEmpty(),"Пришел не пустой список");
        verify(trackingRepository, times(1)).findByIsFinishedFalseAndClientKeycloakId(CLIENT_ID);
    }

    @Test
    void testUpdateTracking() {
        doNothing().when(trackingRepository).updateTrackingFinishedById(anyBoolean(), anyLong());
        trackingRepository.updateTrackingFinishedById(false, 1L);
        verify(trackingRepository, times(1)).updateTrackingFinishedById(false, 1L);
    }

    @Test
    void testInsertTracking() {
        tracking = getActiveTrackingWithDoctorId();
        when(trackingRepository.save(tracking)).thenReturn(tracking);
        Tracking tracking1 = trackingRepository.save(tracking);
        assertEquals(tracking1, tracking, "Возвращаемое значение не совпадает с ожидаемым");
        verify(trackingRepository, times(1)).save(tracking);
    }

    @Test
    void testInsertWithConflict() {
        tracking = getActiveTrackingWithDoctorId();
        assertDoesNotThrow(() -> {
            trackingRepository.save(tracking);
            trackingRepository.save(tracking);
        });
    }
}
