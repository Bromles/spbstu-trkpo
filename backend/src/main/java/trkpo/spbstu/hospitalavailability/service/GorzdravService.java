package trkpo.spbstu.hospitalavailability.service;

import lombok.RequiredArgsConstructor;
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
import trkpo.spbstu.hospitalavailability.exception.BackendUnavailableException;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class GorzdravService {
    private static final Logger logger = Logger.getLogger(GorzdravService.class.getName());

    private final RestTemplate restTemplate;

    private Long errorNum;

    public List<GorzdravHospitalRsDto> getHospitals() {
        errorNum = 0L;
        ResponseEntity<String> response = restTemplate.getForEntity("/shared/lpus", String.class);

        if (response.getStatusCode() == HttpStatus.BAD_GATEWAY || response.getStatusCode() == HttpStatus.SERVICE_UNAVAILABLE) {
            logger.warning(response.getStatusCode() + " " + response.getStatusCode().getReasonPhrase());
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
            logger.warning("Cannot parse JSONObject, error number: " + errorNum);
        }
        return null;
    }

    public List<GorzdravDistrictRsDto> getDistricts() {
        errorNum = 0L;

        ResponseEntity<String> response = restTemplate.getForEntity("/shared/districts", String.class);

        if (response.getStatusCode() == HttpStatus.BAD_GATEWAY || response.getStatusCode() == HttpStatus.SERVICE_UNAVAILABLE) {
            logger.warning(response.getStatusCode() + " " + response.getStatusCode().getReasonPhrase());
            throw new BackendUnavailableException("Gorzdrav is unavailable: " + response.getStatusCode().getReasonPhrase());
        }

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

    private GorzdravDistrictRsDto convertToDistrictDto(JSONObject jsObj) {
        try {
            return new GorzdravDistrictRsDto(
                    Long.parseLong(jsObj.get("id").toString()),
                    jsObj.get("name").toString()
            );
        } catch (Exception e) {
            errorNum++;
            logger.warning("Cannot parse JSONObject, error number: " + errorNum);
        }
        return null;
    }

    public List<GorzdravDoctorRsDto> getDoctorsBySpecialityId(Long hospitalId, Long specialityId) {
        errorNum = 0L;
        String path = "/schedule/lpu/" + hospitalId + "/speciality/" + specialityId + "/doctors";
        ResponseEntity<String> response = restTemplate.getForEntity(path, String.class);
        System.out.println("response " + response);
        if (response.getStatusCode() == HttpStatus.BAD_GATEWAY || response.getStatusCode() == HttpStatus.SERVICE_UNAVAILABLE) {
            logger.warning(response.getStatusCode() + " " + response.getStatusCode().getReasonPhrase());
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
            return new ArrayList<>();
        }
    }

    private GorzdravDoctorRsDto convertToDoctorsDto(JSONObject jsObj) {
        try {
            return new GorzdravDoctorRsDto(
                    Long.parseLong(jsObj.get("id").toString()),
                    jsObj.get("name").toString()
            );
        } catch (Exception e) {
            errorNum++;
            logger.warning("Cannot parse JSONObject, error number: " + errorNum);
        }
        return null;
    }

}
