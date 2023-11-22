package trkpo.spbstu.hospitalavailability.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import trkpo.spbstu.hospitalavailability.entity.Client;
import trkpo.spbstu.hospitalavailability.entity.Tracking;
import trkpo.spbstu.hospitalavailability.service.TrackingService;

import java.util.Objects;

@RestController
public class Controller {

    @Autowired
    private TrackingService trackingService;

    @DeleteMapping(value = "/tracking/deleteById={id}")
    public ResponseEntity<Void> deleteTracking(@PathVariable Long id) {
        Authentication  authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication==null){
            return ResponseEntity.status(401).build();
        }
        Client client = (Client) authentication.getPrincipal();
        Tracking tracking = trackingService.findActiveTrackingById(id);
        if (tracking==null) {
            return ResponseEntity.notFound().build();
        }
        if (!Objects.equals(tracking.getClient().getId(), client.getId())) {
            return ResponseEntity.status(401).build();
        }
        trackingService.deleteTracking(id);
        return ResponseEntity.ok().build();
    }
}
