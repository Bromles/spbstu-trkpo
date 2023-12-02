package trkpo.spbstu.hospitalavailability.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import trkpo.spbstu.hospitalavailability.dto.DistrictResponseDto;
import trkpo.spbstu.hospitalavailability.dto.HospitalResponseDto;
import trkpo.spbstu.hospitalavailability.service.DistrictService;
import trkpo.spbstu.hospitalavailability.service.HospitalService;

import java.util.List;

@RestController
@RequestMapping("/v1/gorzdrav")
@RequiredArgsConstructor
@Validated
public class GorzdravController {
    private final DistrictService districtService;
    private final HospitalService hospitalService;

    @GetMapping("/district")
    public List<DistrictResponseDto> getDistricts() {
        return districtService.findAll();
    }

    @PostMapping("/district")
    public ResponseEntity<String> updateDistricts() {
        return districtService.updateAll();
    }

    @GetMapping("/hospital")
    public List<HospitalResponseDto> getHospitals() {
        return hospitalService.findAll();
    }

    @PostMapping("/hospital")
    public ResponseEntity<String> updateHospitals() {
        return hospitalService.updateAll();
    }

}
