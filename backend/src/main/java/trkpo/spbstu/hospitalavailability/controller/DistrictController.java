package trkpo.spbstu.hospitalavailability.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import trkpo.spbstu.hospitalavailability.dto.DistrictResponseDto;
import trkpo.spbstu.hospitalavailability.service.DistrictService;

import java.util.List;

@RestController
@RequestMapping("/v1/district")
@RequiredArgsConstructor
@Validated
public class DistrictController {
    private final DistrictService districtService;

    @GetMapping
    public List<DistrictResponseDto> getDistricts() {
        return districtService.findAll();
    }
}
