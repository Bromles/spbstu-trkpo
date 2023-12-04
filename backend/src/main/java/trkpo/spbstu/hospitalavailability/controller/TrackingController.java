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
import java.util.UUID;

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

    @GetMapping("/{id}")
    public List<TrackingResponseDto> getActiveUserTracking(@PathVariable @NotNull UUID id) {
        return trackingService.findUserActiveTracking(id);
    }

    @PostMapping
    public TrackingResponseDto addTracking(@Valid @RequestBody TrackingRequestDto trackingRequestDto) {
        return trackingService.addTracking(trackingRequestDto);
    }
}
