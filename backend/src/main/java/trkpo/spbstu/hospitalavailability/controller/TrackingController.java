package trkpo.spbstu.hospitalavailability.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import trkpo.spbstu.hospitalavailability.entity.Tracking;
import trkpo.spbstu.hospitalavailability.service.TrackingService;

@RestController("/tracking")
public class TrackingController {

    @Autowired
    private TrackingService trackingService;

    @DeleteMapping(value = "/deleteById/{id}")
    public ResponseEntity<Void> deleteTracking(@PathVariable Long id, @AuthenticationPrincipal Jwt jwt) {
        Tracking tracking = trackingService.findActiveTrackingById(id);
        if (tracking==null) {
            return ResponseEntity.notFound().build();
        }
        if(Long.parseLong(jwt.getClaimAsString("sub") )!= tracking.getClient().getKeycloakId()) {
            return ResponseEntity.status(401).build();
        }
        trackingService.deleteTracking(id);
        return ResponseEntity.ok().build();
    }
}
