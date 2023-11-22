package trkpo.spbstu.hospitalavailability.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import trkpo.spbstu.hospitalavailability.entity.Tracking;
import trkpo.spbstu.hospitalavailability.repository.TrackingRepository;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class TrackingServiceImpl implements TrackingService{

    private final TrackingRepository trackingRepository;

    @Override
    public void deleteTracking(Long id) {
        trackingRepository.deleteById(id);
    }

    @Override
    public Tracking findActiveTrackingById(Long id) {
        List<Tracking> activeTracking = trackingRepository.findByIsFinishedFalse();
        return  activeTracking.stream()
                .filter(tracking -> Objects.equals(tracking.getId(), id))
                .findFirst()
                .orElse(null);
    }
}
