package trkpo.spbstu.hospitalavailability.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;
import trkpo.spbstu.hospitalavailability.dto.DistrictResponseDto;
import trkpo.spbstu.hospitalavailability.dto.GorzdravDoctorRsDto;
import trkpo.spbstu.hospitalavailability.dto.GorzdravSpecialtiesDto;
import trkpo.spbstu.hospitalavailability.dto.HospitalResponseDto;
import trkpo.spbstu.hospitalavailability.dto.TrackingInfoRsDto;
import trkpo.spbstu.hospitalavailability.service.DistrictService;
import trkpo.spbstu.hospitalavailability.service.GorzdravService;
import trkpo.spbstu.hospitalavailability.service.HospitalService;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import java.util.List;

@RestController
@RequestMapping("/v1/gorzdrav")
@RequiredArgsConstructor
@Validated
@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:8123"})
public class GorzdravController {
    private final DistrictService districtService;
    private final HospitalService hospitalService;
    private final GorzdravService gorzdravService;

    @GetMapping("/district")
    public List<DistrictResponseDto> getDistricts() {
        return districtService.findAll();
    }

    @GetMapping("/doctors/{gorzdravHospitalId}/{gorzdravSpecialityId}")
    public List<GorzdravDoctorRsDto> getDoctorsBySpecialityId(@PathVariable @NotNull Long gorzdravHospitalId,
                                                              @PathVariable @NotNull Long gorzdravSpecialityId) {
        return gorzdravService.getDoctorsBySpecialityId(gorzdravHospitalId, gorzdravSpecialityId);
    }

    @PostMapping("/district")
    public void updateDistricts() {
        districtService.updateAll();
    }

    @GetMapping("/hospital")
    public List<HospitalResponseDto> getHospitals() {
        return hospitalService.findAll();
    }

    @GetMapping("/specialties/{hospitalId}")
    public List<GorzdravSpecialtiesDto> getSpecialties(@PathVariable @NotNull Long hospitalId) {
        return gorzdravService.getSpecialties(hospitalId);
    }

    @PostMapping("/hospital")
    public void updateHospitals() {
        hospitalService.updateAll();
    }

    @GetMapping("/trackingInfo/{hospitalId}/{directionId}/{doctorId}")
    public TrackingInfoRsDto getTrackingInfo(
            @PathVariable @PositiveOrZero @NotNull Long hospitalId,
            @PathVariable @PositiveOrZero @NotNull Long directionId,
            @PathVariable @NotNull Long doctorId
    ) {
        return gorzdravService.getTrackingInfo(hospitalId, directionId, doctorId);
    }
}
