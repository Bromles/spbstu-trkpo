package trkpo.spbstu.hospitalavailability.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import trkpo.spbstu.hospitalavailability.dto.DistrictResponseDto;
import trkpo.spbstu.hospitalavailability.dto.GorzdravDistrictRsDto;
import trkpo.spbstu.hospitalavailability.entity.District;
import trkpo.spbstu.hospitalavailability.mapper.DistrictMapper;
import trkpo.spbstu.hospitalavailability.repository.DistrictRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class DistrictService {
    private final DistrictRepository districtRepository;
    private final DistrictMapper districtMapper;
    private final GorzdravService gorzdravService;
    @PersistenceContext
    private EntityManager entityManager;

    public List<DistrictResponseDto> findAll() {
        return districtMapper.toDistrictDto(districtRepository.findAll());
    }

    @Async
    @Scheduled(fixedDelay = 7, timeUnit = TimeUnit.DAYS)
    @Transactional
    public ResponseEntity<String> updateAll() {
        List<GorzdravDistrictRsDto> districts = gorzdravService.getDistricts();
        insertOrUpdate(districts);
        return ResponseEntity.ok().build();
    }

    @Transactional
    public void insertOrUpdate(List<GorzdravDistrictRsDto> districts) {
        for (GorzdravDistrictRsDto dto : districts) {
            Optional<District> existingDistrict = districtRepository.findByGorzdravId(dto.getGorzdravId());

            if (existingDistrict.isPresent()) {
                District district = existingDistrict.get();
                entityManager.merge(fill(district, dto));
            } else {
                District district = new District();
                District filledDistrict = fill(district, dto);
                filledDistrict.setGorzdravId(dto.getGorzdravId());
                entityManager.persist(filledDistrict);
            }
        }
    }

    private District fill(District district, GorzdravDistrictRsDto newInfo) {
        district.setName(newInfo.getName());
        return district;
    }

}
