package trkpo.spbstu.hospitalavailability.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import trkpo.spbstu.hospitalavailability.dto.DistrictResponseDto;
import trkpo.spbstu.hospitalavailability.mapper.DistrictMapper;
import trkpo.spbstu.hospitalavailability.repository.DistrictRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DistrictService {
    private final DistrictRepository districtRepository;
    private final DistrictMapper districtMapper;

    public List<DistrictResponseDto> findAll() {
        return districtMapper.toDistrictDto(districtRepository.findAll());
    }
}
