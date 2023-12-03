package trkpo.spbstu.hospitalavailability.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import trkpo.spbstu.hospitalavailability.dto.TrackingResponseDto;
import trkpo.spbstu.hospitalavailability.entity.Tracking;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface TrackingMapper {

    @Mapping(source = "hospital.fullName", target = "hospitalFullName")
    @Mapping(source = "hospital.gorzdravId", target = "hospitalGorzdravId")
    @Mapping(source = "hospital.id", target = "hospital")
    @Mapping(source = "client.id", target = "client")
    TrackingResponseDto toTrackingDto(Tracking tracking);

    List<TrackingResponseDto> toTrackingDto(List<Tracking> tracking);
}
