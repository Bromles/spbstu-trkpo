package trkpo.spbstu.hospitalavailability.service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import trkpo.spbstu.hospitalavailability.dto.GorzdravHospitalRsDto;
import trkpo.spbstu.hospitalavailability.dto.HospitalResponseDto;
import trkpo.spbstu.hospitalavailability.entity.Hospital;
import trkpo.spbstu.hospitalavailability.mapper.HospitalMapper;
import trkpo.spbstu.hospitalavailability.repository.HospitalRepository;

@Service
@AllArgsConstructor
public class HospitalService {
    private final GorzdravService gorzdravService;
    private final HospitalRepository hospitalRepository;
    private final HospitalMapper hospitalMapper;
    @PersistenceContext
    private EntityManager entityManager;

    public List<HospitalResponseDto> findAll() {
        return hospitalMapper.toHospitalDto(hospitalRepository.findAll());
    }

    @Scheduled(fixedDelay = 10080, initialDelay = 1, timeUnit = TimeUnit.MINUTES)
    @Transactional
    public ResponseEntity<String> updateAll() {
        List<GorzdravHospitalRsDto> hospitals = gorzdravService.getHospitals();
        batchInsertOrUpdate(hospitals);
        return ResponseEntity.ok().build();
    }

    @Transactional
    public void batchInsertOrUpdate(List<GorzdravHospitalRsDto> hospitals) {
        int batchSize = 50;

        for (int i = 0; i < hospitals.size(); i++) {
            GorzdravHospitalRsDto dto = hospitals.get(i);
            Optional<Hospital> existingHospital = hospitalRepository.findByGorzdravId(dto.getGorzdravId());

            if (existingHospital.isPresent()) {
                Hospital hospital = existingHospital.get();
                entityManager.merge(fill(hospital, dto));
            } else {
                Hospital newHospital = new Hospital();
                Hospital filledHosp = fill(newHospital, dto);
                filledHosp.setGorzdravId(dto.getGorzdravId());
                entityManager.persist(filledHosp);
            }
            if (i % batchSize == 0 || i == hospitals.size() - 1) {
                entityManager.flush();
                entityManager.clear();
            }
        }
    }

    private Hospital fill(Hospital hospital, GorzdravHospitalRsDto newInfo) {
        hospital.setDistrictId(newInfo.getDistrictId());
        hospital.setLongitude(newInfo.getLongitude());
        hospital.setLatitude(newInfo.getLatitude());
        hospital.setAddress(newInfo.getAddress());
        hospital.setFullName(newInfo.getFullName());
        hospital.setShortName(newInfo.getShortName());
        hospital.setPhone(newInfo.getPhone());
        return hospital;
    }
}
