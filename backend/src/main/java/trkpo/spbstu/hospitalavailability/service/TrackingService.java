package trkpo.spbstu.hospitalavailability.service;

import trkpo.spbstu.hospitalavailability.entity.Tracking;

public interface TrackingService {

    void deleteTracking(Long id);
    Tracking findActiveTrackingById(Long id);
}
