package trkpo.spbstu.hospitalavailability.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import trkpo.spbstu.hospitalavailability.dto.TrackingResponseDto;
import trkpo.spbstu.hospitalavailability.service.TrackingService;

import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/v1/tracking")
@RequiredArgsConstructor
@Validated
public class TrackingController {

    private final TrackingService trackingService;

    @DeleteMapping(value = "/{id}")
    public long deleteTracking(@PathVariable @NotNull Long id) {
        return trackingService.deleteTracking(id);
    }

    @GetMapping(value = "/{clientId}")
    public List<TrackingResponseDto> getActiveUserTracking(@PathVariable @NotNull Long clientId) {
        return trackingService.findUserActiveTracking(clientId);
    }
}
