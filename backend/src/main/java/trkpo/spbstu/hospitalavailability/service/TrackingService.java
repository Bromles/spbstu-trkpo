package trkpo.spbstu.hospitalavailability.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import trkpo.spbstu.hospitalavailability.utils.SecurityUtils;
import trkpo.spbstu.hospitalavailability.entity.Tracking;
import trkpo.spbstu.hospitalavailability.exception.ForbiddenException;
import trkpo.spbstu.hospitalavailability.exception.NotFoundException;
import trkpo.spbstu.hospitalavailability.repository.TrackingRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TrackingService {

    private final TrackingRepository trackingRepository;

    @Transactional
    public long deleteTracking(Long id) {
        Tracking tracking = findActiveTrackingById(id);
        if (tracking==null) {
            throw new NotFoundException("Not found tracking");
        }
        if(!SecurityUtils.getUserKey().equals(tracking.getClient().getKeycloakId().toString())) {
            throw new ForbiddenException("No access to delete tracking");
        }
        return trackingRepository.removeById(id);
    }

    public Tracking findActiveTrackingById(Long id) {
        List<Tracking> activeTracking = trackingRepository.findByIsFinishedFalse();
        return  activeTracking.stream()
                .filter(tracking -> tracking.getId() == id)
                .findFirst()
                .orElse(null);
    }
}
