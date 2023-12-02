package trkpo.spbstu.hospitalavailability.service;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import trkpo.spbstu.hospitalavailability.dto.HospitalResponseDto;
import trkpo.spbstu.hospitalavailability.mapper.HospitalMapper;
import trkpo.spbstu.hospitalavailability.repository.HospitalRepository;

@Service
@RequiredArgsConstructor
public class HospitalService {
    private final HospitalRepository hospitalRepository;
    private final HospitalMapper hospitalMapper;

    public List<HospitalResponseDto> findAll() {
        return hospitalMapper.toHospitalDto(hospitalRepository.findAll());
    }
}
