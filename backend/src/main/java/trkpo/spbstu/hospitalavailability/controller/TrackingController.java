package trkpo.spbstu.hospitalavailability.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import trkpo.spbstu.hospitalavailability.dto.TrackingRequestDto;
import trkpo.spbstu.hospitalavailability.dto.TrackingResponseDto;
import trkpo.spbstu.hospitalavailability.service.TrackingService;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/v1/tracking")
@RequiredArgsConstructor
@Validated
@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:8123"})
public class TrackingController {

    private final TrackingService trackingService;

    @DeleteMapping(value = "/{id}")
    public long deleteTracking(@PathVariable @NotNull Long id) {
        return trackingService.deleteTracking(id);
    }

    @GetMapping("/{keycloakUuid}")
    public List<TrackingResponseDto> getActiveUserTracking(@PathVariable @NotNull String keycloakUuid) {
        return trackingService.findUserActiveTracking(keycloakUuid);
    }

    @PostMapping
    public TrackingResponseDto addTracking(@Valid @RequestBody TrackingRequestDto trackingRequestDto) {
        return trackingService.addTracking(trackingRequestDto);
    }
}
