package trkpo.spbstu.hospitalavailability.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import trkpo.spbstu.hospitalavailability.dto.HospitalResponseDto;
import trkpo.spbstu.hospitalavailability.entity.Hospital;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface HospitalMapper {
    @Mapping(source = "hospital.id", target = "id")
    @Mapping(source = "hospital.gorzdravId", target = "gorzdravId")
    @Mapping(source = "hospital.latitude", target = "latitude")
    @Mapping(source = "hospital.longitude", target = "longitude")
    @Mapping(source = "hospital.districtId", target = "districtId")
    @Mapping(source = "hospital.address", target = "address")
    @Mapping(source = "hospital.fullName", target = "fullName")
    @Mapping(source = "hospital.shortName", target = "shortName")
    @Mapping(source = "hospital.phone", target = "phone")
    HospitalResponseDto toHospitalDto(Hospital hospital);

    List<HospitalResponseDto> toHospitalDto(List<Hospital> hospitals);
}
