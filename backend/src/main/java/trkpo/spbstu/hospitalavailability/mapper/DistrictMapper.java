package trkpo.spbstu.hospitalavailability.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import trkpo.spbstu.hospitalavailability.dto.DistrictResponseDto;
import trkpo.spbstu.hospitalavailability.entity.District;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface DistrictMapper {
    DistrictResponseDto toDistrictDto(District district);

    List<DistrictResponseDto> toDistrictDto(List<District> districts);
}