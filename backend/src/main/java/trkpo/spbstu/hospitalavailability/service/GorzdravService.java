package trkpo.spbstu.hospitalavailability.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import trkpo.spbstu.hospitalavailability.dto.GorzdravDistrictRsDto;
import trkpo.spbstu.hospitalavailability.dto.GorzdravDoctorRsDto;
import trkpo.spbstu.hospitalavailability.dto.GorzdravHospitalRsDto;
import trkpo.spbstu.hospitalavailability.dto.GorzdravSpecialtiesDto;
import trkpo.spbstu.hospitalavailability.dto.TrackingInfoRqDto;
import trkpo.spbstu.hospitalavailability.dto.TrackingInfoRsDto;
import trkpo.spbstu.hospitalavailability.exception.BackendUnavailableException;
import trkpo.spbstu.hospitalavailability.exception.NotFoundException;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class GorzdravService {

    private final RestTemplate restTemplate;

    private Long errorNum;

    public List<GorzdravHospitalRsDto> getHospitals() {
        errorNum = 0L;
        ResponseEntity<String> response = restTemplate.getForEntity("/shared/lpus", String.class);

        if (response.getStatusCode() == HttpStatus.BAD_GATEWAY || response.getStatusCode() == HttpStatus.SERVICE_UNAVAILABLE) {
            log.warn(response.getStatusCode() + " " + response.getStatusCode().getReasonPhrase());
            throw new BackendUnavailableException("Gorzdrav is unavailable: " + response.getStatusCode().getReasonPhrase());
        }

        String responseBody = response.getBody();
        JSONArray array = new JSONObject(responseBody).getJSONArray("result");

        List<GorzdravHospitalRsDto> hospitalRs = new ArrayList<>();
        for (int i = 0; i < array.length(); i++) {
            JSONObject jsObj = array.getJSONObject(i);
            var hosp = convertToHospitalDto(jsObj);
            if (hosp != null) {
                hospitalRs.add(hosp);
            }
        }

        return hospitalRs;
    }

    public List<GorzdravDistrictRsDto> getDistricts() {
        errorNum = 0L;

        ResponseEntity<String> response = restTemplate.getForEntity("/shared/districts", String.class);
        checkStatusCode(response);

        String responseBody = response.getBody();
        JSONArray array = new JSONObject(responseBody).getJSONArray("result");

        List<GorzdravDistrictRsDto> districtsRs = new ArrayList<>();
        for (int i = 0; i < array.length(); i++) {
            JSONObject jsObj = array.getJSONObject(i);
            var district = convertToDistrictDto(jsObj);
            if (district != null) {
                districtsRs.add(district);
            }
        }

        return districtsRs;
    }

    public TrackingInfoRsDto getTrackingInfo(TrackingInfoRqDto trackingInfoRqDto) {
        Long doctorId = trackingInfoRqDto.getDoctorId();
        Long hospitalId = trackingInfoRqDto.getHospitalId();
        Long directionId = trackingInfoRqDto.getDirectionId();
        String doctorName = "Без разницы";
        if (doctorId != null && doctorId != -1) {
            doctorName = getDoctornName(doctorId, directionId, hospitalId);
        }
        return new TrackingInfoRsDto(
                getDirectionName(directionId, hospitalId),
                doctorName
        );
    }

    public List<GorzdravSpecialtiesDto> getSpecialties(Long gorzdravHospitalId) {
        ResponseEntity<String> response = restTemplate.getForEntity("/schedule/lpu/" + gorzdravHospitalId + "/specialties", String.class);
        if (response.getStatusCode() == HttpStatus.BAD_GATEWAY || response.getStatusCode() == HttpStatus.SERVICE_UNAVAILABLE) {
            log.warn(response.getStatusCode() + " " + response.getStatusCode().getReasonPhrase());
            throw new BackendUnavailableException("Gorzdrav is unavailable: " + response.getStatusCode().getReasonPhrase());
        }
        String responseBody = response.getBody();
        if(!new JSONObject(responseBody).getBoolean("success")) {
            throw new NotFoundException("Hospital or specialties not found");
        }
        JSONArray array = new JSONObject(responseBody).getJSONArray("result");
        List<GorzdravSpecialtiesDto> specialties = new ArrayList<>();
        for (int i = 0; i < array.length(); i++) {
            JSONObject jsObj = array.getJSONObject(i);
            var special= convertToSpecialtiesDto(jsObj);
            if (special!= null) {
                specialties.add(special);
            }
        }
        return specialties;
    }

    public List<GorzdravDoctorRsDto> getDoctorsBySpecialityId(Long GorzdravHospitalId, Long GorzdravSpecialityId) {
        errorNum = 0L;
        String path = "/schedule/lpu/" + GorzdravHospitalId + "/speciality/" + GorzdravSpecialityId + "/doctors";
        ResponseEntity<String> response = restTemplate.getForEntity(path, String.class);
        if (response.getStatusCode() == HttpStatus.BAD_GATEWAY || response.getStatusCode() == HttpStatus.SERVICE_UNAVAILABLE) {
            log.warn(response.getStatusCode() + " " + response.getStatusCode().getReasonPhrase());
            throw new BackendUnavailableException("Gorzdrav is unavailable: " + response.getStatusCode().getReasonPhrase());
        }

        String responseBody = response.getBody();
        try {
            JSONArray array = new JSONObject(responseBody).getJSONArray("result");
            List<GorzdravDoctorRsDto> doctorsRs = new ArrayList<>();
            for (int i = 0; i < array.length(); i++) {
                JSONObject jsObj = array.getJSONObject(i);
                var doctor = convertToDoctorsDto(jsObj);
                if (doctor != null) {
                    doctorsRs.add(doctor);
                }
            }
            return doctorsRs;
        } catch (JSONException ex) {  // исключение, если отсутствуют специалисты для приёма по выбранной специальности
            return new ArrayList<>(); // поэтому возвращаем пустой лист
        }
    }

    private String getDirectionName(long directionId, long hospitalId) {
        ResponseEntity<String> response = restTemplate.getForEntity("/schedule/lpu/"+ hospitalId + "/specialties", String.class);
        return getNameById(response, directionId);
    }

    private String getDoctornName(long doctorId, long directionId, long hospitalId) {
        ResponseEntity<String> response = restTemplate.getForEntity(
                "/schedule/lpu/"+ hospitalId + "/speciality/" + directionId + "/doctors",
                String.class
        );
        return getNameById(response, doctorId);
    }

    private void checkStatusCode(ResponseEntity<String> response) {
        if (response.getStatusCode() == HttpStatus.BAD_GATEWAY || response.getStatusCode() == HttpStatus.SERVICE_UNAVAILABLE) {
            log.warn(response.getStatusCode() + " " + response.getStatusCode().getReasonPhrase());
            throw new BackendUnavailableException("Gorzdrav is unavailable: " + response.getStatusCode().getReasonPhrase());
        }
    }

    private String getNameById(ResponseEntity<String> response, Long id) {
        checkStatusCode(response);

        String responseBody = response.getBody();
        JSONArray array = new JSONObject(responseBody).getJSONArray("result");
        for (int i = 0; i < array.length(); i++) {
            JSONObject jsObj = array.getJSONObject(i);
            if (jsObj.get("id").toString().equals(id.toString())) {
                return jsObj.get("name").toString();
            }
        }
        throw new NotFoundException("Doctor not found");
    }

    private GorzdravDistrictRsDto convertToDistrictDto(JSONObject jsObj) {
        try {
            return new GorzdravDistrictRsDto(
                    Long.parseLong(jsObj.get("id").toString()),
                    jsObj.get("name").toString()
            );
        } catch (Exception e) {
            errorNum++;
            log.warn("Cannot parse JSONObject, error number: " + errorNum);
        }
        return null;
    }

    private GorzdravDoctorRsDto convertToDoctorsDto(JSONObject jsObj) {
        try {
            return new GorzdravDoctorRsDto(
                    Long.parseLong(jsObj.get("id").toString()),
                    jsObj.get("name").toString()
            );
        } catch (Exception e) {
            errorNum++;
            log.warn("Cannot parse JSONObject, error number: " + errorNum);
        }
        return null;
    }


    private GorzdravSpecialtiesDto convertToSpecialtiesDto(JSONObject jsObj) {
        try {
            return new GorzdravSpecialtiesDto(
                    Long.parseLong(jsObj.get("id").toString()),
                    Long.parseLong(jsObj.get("countFreeTicket").toString()),
                    jsObj.get("name").toString()
            );
        } catch (Exception e) {
            log.warn("Cannot parse JSONObject");
        }
        return null;
    }


    private GorzdravHospitalRsDto convertToHospitalDto(JSONObject jsObj) {
        try {
            return new GorzdravHospitalRsDto(
                    Long.parseLong(jsObj.get("id").toString()),
                    Double.parseDouble(jsObj.get("longitude").toString()),
                    Double.parseDouble(jsObj.get("latitude").toString()),
                    Long.parseLong(jsObj.get("districtId").toString()),
                    jsObj.get("address").toString(),
                    jsObj.get("lpuFullName").toString(),
                    jsObj.get("lpuShortName").toString(),
                    jsObj.get("phone").toString()
            );
        } catch (Exception e) {
            errorNum++;
            log.warn("Cannot parse JSONObject, error number: " + errorNum);
        }
        return null;
    }
}
