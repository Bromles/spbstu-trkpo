package trkpo.spbstu.hospitalavailability.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import trkpo.spbstu.hospitalavailability.dto.HospitalResponseDto;
import trkpo.spbstu.hospitalavailability.entity.Hospital;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface HospitalMapper {
    HospitalResponseDto toHospitalDto(Hospital hospital);

    List<HospitalResponseDto> toHospitalDto(List<Hospital> hospitals);
}
