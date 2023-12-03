package trkpo.spbstu.hospitalavailability.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import trkpo.spbstu.hospitalavailability.dto.DistrictResponseDto;
import trkpo.spbstu.hospitalavailability.dto.GorzdravDoctorRsDto;
import trkpo.spbstu.hospitalavailability.dto.HospitalResponseDto;
import trkpo.spbstu.hospitalavailability.service.DistrictService;
import trkpo.spbstu.hospitalavailability.service.GorzdravService;
import trkpo.spbstu.hospitalavailability.service.HospitalService;

import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/v1/gorzdrav")
@RequiredArgsConstructor
@Validated
public class GorzdravController {
    private final DistrictService districtService;
    private final HospitalService hospitalService;
    private final GorzdravService gorzdravService;

    @GetMapping("/district")
    public List<DistrictResponseDto> getDistricts() {
        return districtService.findAll();
    }

    @GetMapping("/doctors/{gorzdrav_hospital_id}/{gorzdrav_speciality_id}")
    public List<GorzdravDoctorRsDto> getDoctorsBySpecialityId(@PathVariable @NotNull Long gorzdrav_hospital_id,
                                                              @PathVariable @NotNull Long gorzdrav_speciality_id) {
        return gorzdravService.getDoctorsBySpecialityId(gorzdrav_hospital_id, gorzdrav_speciality_id);
    }

    @PostMapping("/district")
    public void updateDistricts() {
        districtService.updateAll();
    }

    @GetMapping("/hospital")
    public List<HospitalResponseDto> getHospitals() {
        return hospitalService.findAll();
    }

    @PostMapping("/hospital")
    public void updateHospitals() {
        hospitalService.updateAll();
    }

}
