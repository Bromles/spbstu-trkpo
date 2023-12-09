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
@CrossOrigin(origins = {"http://localhost:4200"})
public class TrackingController {

    private final TrackingService trackingService;

    @PostMapping(value = "/{id}")
    public long deleteTracking(@PathVariable @NotNull Long id) {
        return trackingService.deleteTracking(id);
    }

    @GetMapping("/{keycloak_uuid}")
    public List<TrackingResponseDto> getActiveUserTracking(@PathVariable @NotNull String keycloak_uuid) {
        return trackingService.findUserActiveTracking(keycloak_uuid);
    }

    @PostMapping
    public TrackingResponseDto addTracking(@Valid @RequestBody TrackingRequestDto trackingRequestDto) {
        return trackingService.addTracking(trackingRequestDto);
    }
}
