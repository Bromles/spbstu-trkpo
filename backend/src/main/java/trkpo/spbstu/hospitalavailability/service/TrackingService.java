package trkpo.spbstu.hospitalavailability.service;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import trkpo.spbstu.hospitalavailability.dto.TrackingRequestDto;
import trkpo.spbstu.hospitalavailability.dto.TrackingResponseDto;
import trkpo.spbstu.hospitalavailability.entity.Client;
import trkpo.spbstu.hospitalavailability.entity.Hospital;
import trkpo.spbstu.hospitalavailability.entity.Tracking;
import trkpo.spbstu.hospitalavailability.exception.ForbiddenException;
import trkpo.spbstu.hospitalavailability.exception.NotFoundException;
import trkpo.spbstu.hospitalavailability.exception.ViolationOfIntegrityException;
import trkpo.spbstu.hospitalavailability.mapper.TrackingMapper;
import trkpo.spbstu.hospitalavailability.repository.ClientRepository;
import trkpo.spbstu.hospitalavailability.repository.HospitalRepository;
import trkpo.spbstu.hospitalavailability.repository.TrackingRepository;
import trkpo.spbstu.hospitalavailability.utils.SecurityUtils;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TrackingService {

    private final TrackingRepository trackingRepository;
    private final TrackingMapper trackingMapper;
    private final ClientRepository clientRepository;
    private final HospitalRepository hospitalRepository;

    @Transactional
    public long deleteTracking(Long id) {
        Tracking tracking = findActiveTrackingById(id);
        if (tracking == null) {
            throw new NotFoundException("Not found tracking");
        }
        if (!SecurityUtils.getUserKey().equals(tracking.getClient().getKeycloakId().toString())) {
            throw new ForbiddenException("No access to delete tracking");
        }
        return trackingRepository.removeById(id);
    }

    public List<TrackingResponseDto> findUserActiveTracking(Long clientId) {
        Client client = clientRepository.findById(clientId).orElseThrow(() -> new NotFoundException("Not found client"));
        if (!SecurityUtils.getUserKey().equals(client.getKeycloakId().toString())) {
            throw new ForbiddenException("No access to delete tracking");
        }
        return trackingMapper.toTrackingDto(trackingRepository.findByIsFinishedFalseAndClientId(clientId));
    }

    @Transactional
    public TrackingResponseDto addTracking(TrackingRequestDto requestDto) {
        Hospital hospital = hospitalRepository.findById(requestDto.getHospitalId())
                .orElseThrow(() -> new NotFoundException("Not found hospital"));
        Client client = clientRepository.findByKeycloakId(UUID.fromString(SecurityUtils.getUserKey()))
                .orElseThrow(() -> new ForbiddenException("No access to delete tracking"));
        Tracking tracking = new Tracking(requestDto, hospital, client);
        tracking.setFinished(false);
        tracking.setDate(new Date());
        try {//обработать исключение вставки которое будет вылетать,  если добавим @UniqueConstraint на все поля?
            return trackingMapper.toTrackingDto(trackingRepository.save(tracking));
        } catch (DataIntegrityViolationException e) {
            throw new ViolationOfIntegrityException("Bad request", e);
        }
    }

    private Tracking findActiveTrackingById(Long id) {
        List<Tracking> activeTracking = trackingRepository.findByIsFinishedFalse();
        return activeTracking.stream()
                .filter(tracking -> id.equals(tracking.getId()))
                .findFirst()
                .orElse(null);
    }
}
