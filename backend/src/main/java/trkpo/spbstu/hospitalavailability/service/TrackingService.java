package trkpo.spbstu.hospitalavailability.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import trkpo.spbstu.hospitalavailability.entity.Tracking;
import trkpo.spbstu.hospitalavailability.repository.TrackingRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TrackingService {

    private final TrackingRepository trackingRepository;

    public void deleteTracking(Long id) {
        trackingRepository.deleteById(id);
    }

    public Tracking findActiveTrackingById(Long id) {
        List<Tracking> activeTracking = trackingRepository.findByIsFinishedFalse();
        return  activeTracking.stream()
                .filter(tracking -> tracking.getId() == id)
                .findFirst()
                .orElse(null);
    }
}
