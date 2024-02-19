package trkpo.spbstu.hospitalavailability.service;

import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.testcontainers.shaded.org.apache.commons.lang3.RandomStringUtils;
import org.testcontainers.shaded.org.apache.commons.lang3.RandomUtils;
import trkpo.spbstu.hospitalavailability.dto.*;
import trkpo.spbstu.hospitalavailability.exception.BackendUnavailableException;
import trkpo.spbstu.hospitalavailability.exception.NotFoundException;
import trkpo.spbstu.hospitalavailability.utils.JsonUtils;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GorzdravServiceTests {

    @Mock
    private RestTemplate restTemplate;
    private GorzdravService gorzdravService;
    private static final String FULL_NAME = RandomStringUtils.randomAlphabetic(5);
    private static final String SHORT_NAME = RandomStringUtils.randomAlphabetic(5);
    private static final String ADDRESS = RandomStringUtils.randomAlphabetic(5);
    private static final Long ID = RandomUtils.nextLong();
    private static final String PHONE = "(812) 246-39-90";
    private static final Double LONGITUDE = RandomUtils.nextDouble();
    private static final Double LATITUDE = RandomUtils.nextDouble();
    private static final String DISTRICT_NAME = RandomStringUtils.randomAlphabetic(10);
    private static final String SP_NAME = RandomStringUtils.randomAlphabetic(10);
    private static final String DOCTOR_NAME = RandomStringUtils.randomAlphabetic(10);
    private static final Long HOSPITAL_ID = RandomUtils.nextLong();
    private static final Long DOCTOR_ID = RandomUtils.nextLong();
    private static final Long SP_ID = RandomUtils.nextLong();

    @BeforeEach
    void setup() {
        gorzdravService = new GorzdravService(restTemplate);
    }

    @Test
    void testGetHospitals() throws JSONException {
        ResponseEntity<String> responseEntity = new ResponseEntity<>(JsonUtils.getHospitalBodySuccess(ID, FULL_NAME, SHORT_NAME, ADDRESS, PHONE, LONGITUDE, LATITUDE), HttpStatus.OK);
        String url = "/shared/lpus";
        when(restTemplate.getForEntity(url, String.class)).thenReturn(responseEntity);

        List<GorzdravHospitalRsDto> hospitals = gorzdravService.getHospitals();
        assertThat(hospitals).hasSize(1);
        assertAll("Checking the response fields",
                () -> assertEquals(hospitals.get(0).getGorzdravId(), ID),
                () -> assertEquals(hospitals.get(0).getShortName(), SHORT_NAME),
                () -> assertEquals(hospitals.get(0).getFullName(), FULL_NAME),
                () -> assertEquals(hospitals.get(0).getAddress(), ADDRESS),
                () -> assertEquals(hospitals.get(0).getPhone(), PHONE),
                () -> assertEquals(hospitals.get(0).getLatitude(), LATITUDE),
                () -> assertEquals(hospitals.get(0).getLongitude(), LONGITUDE)
        );
    }

    @ParameterizedTest
    @ValueSource(ints = {503, 502})
    void testGetHospitalsWithBadHttpStatus(int httpStatus){
        ResponseEntity<String> responseEntity = new ResponseEntity<>("", HttpStatus.valueOf(httpStatus));
        String url = "/shared/lpus";
        when(restTemplate.getForEntity(url, String.class)).thenReturn(responseEntity);
        assertThrows(BackendUnavailableException.class, () -> gorzdravService.getHospitals());
    }

    @Test
    void testGetHospitalsBadGson() throws JSONException {
        ResponseEntity<String> responseEntity = new ResponseEntity<>(JsonUtils.getBadJson(ID), HttpStatus.OK);
        String url = "/shared/lpus";
        when(restTemplate.getForEntity(url, String.class)).thenReturn(responseEntity);
        List<GorzdravHospitalRsDto> hospitals = gorzdravService.getHospitals();
        assertThat(hospitals).hasSize(0);
    }

    @Test
    void testGetDistricts() throws JSONException {
        ResponseEntity<String> responseEntity = new ResponseEntity<>(JsonUtils.getUniversalJson(ID, DISTRICT_NAME), HttpStatus.OK);
        String url = "/shared/districts";
        when(restTemplate.getForEntity(url, String.class)).thenReturn(responseEntity);

        List<GorzdravDistrictRsDto> districts = gorzdravService.getDistricts();
        assertThat(districts).hasSize(1);
        assertAll("Checking the response fields",
                () -> assertEquals(districts.get(0).getGorzdravId(), ID),
                () -> assertEquals(districts.get(0).getName(), DISTRICT_NAME));
    }

    @ParameterizedTest
    @ValueSource(ints = {503, 502})
    void testGetDistrictWithBadHttpStatus(int httpStatus){
        ResponseEntity<String> responseEntity = new ResponseEntity<>("", HttpStatus.valueOf(httpStatus));
        String url = "/shared/districts";
        when(restTemplate.getForEntity(url, String.class)).thenReturn(responseEntity);
        assertThrows(BackendUnavailableException.class, () -> gorzdravService.getDistricts());
    }

    @Test
    void testGetDistrictBadGson() throws JSONException {
        ResponseEntity<String> responseEntity = new ResponseEntity<>(JsonUtils.getBadJson(ID), HttpStatus.OK);
        String url = "/shared/districts";
        when(restTemplate.getForEntity(url, String.class)).thenReturn(responseEntity);
        List<GorzdravDistrictRsDto> districts = gorzdravService.getDistricts();
        assertThat(districts).hasSize(0);
    }

    @Test
    void getSpecialties() throws JSONException {
        long count = 0;
        ResponseEntity<String> responseEntity = new ResponseEntity<>(JsonUtils.getSpecialties(ID, SP_NAME, count), HttpStatus.OK);
        String url = "/schedule/lpu/" + HOSPITAL_ID + "/specialties";
        when(restTemplate.getForEntity(url, String.class)).thenReturn(responseEntity);

        List<GorzdravSpecialtiesDto> specialties = gorzdravService.getSpecialties(HOSPITAL_ID);
        assertThat(specialties).hasSize(1);
        assertAll("Checking the response fields",
                () -> assertEquals(specialties.get(0).getId(), ID),
                () -> assertEquals(specialties.get(0).getName(), SP_NAME),
                () -> assertEquals(specialties.get(0).getCountFreeTicket(), count));
    }

    @ParameterizedTest
    @ValueSource(ints = {503, 502})
    void getSpecialtiesWithBadHttpStatus(int httpStatus) {
        ResponseEntity<String> responseEntity = new ResponseEntity<>("", HttpStatus.valueOf(httpStatus));
        String url = "/schedule/lpu/" + HOSPITAL_ID + "/specialties";
        when(restTemplate.getForEntity(url, String.class)).thenReturn(responseEntity);
        assertThrows(BackendUnavailableException.class, () -> gorzdravService.getSpecialties(HOSPITAL_ID));
    }

    @Test
    void getSpecialtiesNotFound(){
        ResponseEntity<String> responseEntity = new ResponseEntity<>("{\"success\": false}", HttpStatus.OK);
        String url = "/schedule/lpu/" + HOSPITAL_ID + "/specialties";
        when(restTemplate.getForEntity(url, String.class)).thenReturn(responseEntity);
        assertThrows(NotFoundException.class, () -> gorzdravService.getSpecialties(HOSPITAL_ID));
    }

    @Test
    void testGetSpecialtiesBadGson() throws JSONException {
        ResponseEntity<String> responseEntity = new ResponseEntity<>(JsonUtils.getBadJson(ID), HttpStatus.OK);
        String url = "/schedule/lpu/" + HOSPITAL_ID + "/specialties";
        when(restTemplate.getForEntity(url, String.class)).thenReturn(responseEntity);
        List<GorzdravSpecialtiesDto> specialties = gorzdravService.getSpecialties(HOSPITAL_ID);
        assertThat(specialties).hasSize(0);
    }

    @Test
    void getDoctorsBySpecialityId() throws JSONException {
        ResponseEntity<String> responseEntity = new ResponseEntity<>(JsonUtils.getUniversalJson(ID, DOCTOR_NAME), HttpStatus.OK);
        String url = "/schedule/lpu/" + HOSPITAL_ID  + "/speciality/" + SP_ID + "/doctors";
        when(restTemplate.getForEntity(url, String.class)).thenReturn(responseEntity);

        List<GorzdravDoctorRsDto> doctors = gorzdravService.getDoctorsBySpecialityId(HOSPITAL_ID, SP_ID);
        assertThat(doctors).hasSize(1);
        assertAll("Checking the response fields",
                () -> assertEquals(doctors.get(0).getGorzdravId(), ID),
                () -> assertEquals(doctors.get(0).getName(), DOCTOR_NAME));
    }

    @ParameterizedTest
    @ValueSource(ints = {503, 502})
    void getDoctorsBySpecialityIdHttpStatus(int httpStatus) {
        ResponseEntity<String> responseEntity = new ResponseEntity<>("", HttpStatus.valueOf(httpStatus));
        String url = "/schedule/lpu/" + HOSPITAL_ID + "/speciality/" + SP_ID + "/doctors";
        when(restTemplate.getForEntity(url, String.class)).thenReturn(responseEntity);
        assertThrows(BackendUnavailableException.class, () -> gorzdravService.getDoctorsBySpecialityId(HOSPITAL_ID, SP_ID));
    }

    @Test
    void testDoctorsBySpecialityIdBadGson() throws JSONException {
        String url = "/schedule/lpu/" + HOSPITAL_ID + "/speciality/" + SP_ID + "/doctors";
        ResponseEntity<String> responseEntity = new ResponseEntity<>(JsonUtils.getBadJson(ID), HttpStatus.OK);
        when(restTemplate.getForEntity(url, String.class)).thenReturn(responseEntity);
        List<GorzdravDoctorRsDto> doctors = gorzdravService.getDoctorsBySpecialityId(HOSPITAL_ID, SP_ID);
        assertThat(doctors).hasSize(0);
    }

    @Test
    void getTrackingInfoTest() throws JSONException {
        String urlForGetDoctorName = "/schedule/lpu/"+ HOSPITAL_ID + "/speciality/" + SP_ID + "/doctors";
        String urlForGetDirectionName = "/schedule/lpu/"+ HOSPITAL_ID + "/specialties";

        ResponseEntity<String> responseEntityGetDoctorName = new ResponseEntity<>(JsonUtils.getUniversalJson(DOCTOR_ID, DOCTOR_NAME), HttpStatus.OK);
        ResponseEntity<String> responseEntityGetDirectionName = new ResponseEntity<>(JsonUtils.getUniversalJson(SP_ID, SP_NAME), HttpStatus.OK);

        when(restTemplate.getForEntity(urlForGetDoctorName, String.class)).thenReturn(responseEntityGetDoctorName);
        when(restTemplate.getForEntity(urlForGetDirectionName, String.class)).thenReturn(responseEntityGetDirectionName);

        TrackingInfoRsDto trackingInfoRsDto = gorzdravService.getTrackingInfo(HOSPITAL_ID, SP_ID, DOCTOR_ID);
        assertNotNull(trackingInfoRsDto,"Empty answer");
        assertAll("Checking the response fields",
                () -> assertEquals(trackingInfoRsDto.getDirectionName(), SP_NAME),
                () -> assertEquals(trackingInfoRsDto.getDoctorName(), DOCTOR_NAME));
    }

    @Test
    void getTrackingInfoTestWithoutDoctor() throws JSONException {
        Long doctorId = -1L;
        String urlForGetDirectionName = "/schedule/lpu/"+ HOSPITAL_ID + "/specialties";

        ResponseEntity<String> responseEntityGetDirectionName = new ResponseEntity<>(JsonUtils.getUniversalJson(SP_ID, SP_NAME), HttpStatus.OK);
        when(restTemplate.getForEntity(urlForGetDirectionName, String.class)).thenReturn(responseEntityGetDirectionName);

        TrackingInfoRsDto trackingInfoRsDto = gorzdravService.getTrackingInfo(HOSPITAL_ID, SP_ID, doctorId);
        assertNotNull(trackingInfoRsDto,"Empty");
        assertAll("Checking the response fields",
                () -> assertEquals(trackingInfoRsDto.getDirectionName(), SP_NAME),
                () -> assertEquals(trackingInfoRsDto.getDoctorName(), "Без разницы"));
    }

    @Test
    void getTrackingInfoTestNoInfoAboutDoctor() throws JSONException {
        Long anotherDoctorId = RandomUtils.nextLong();
        String urlForGetDoctorName = "/schedule/lpu/"+ HOSPITAL_ID + "/speciality/" + SP_ID + "/doctors";
        String urlForGetDirectionName = "/schedule/lpu/"+ HOSPITAL_ID + "/specialties";

        ResponseEntity<String> responseEntityGetDoctorName = new ResponseEntity<>(JsonUtils.getUniversalJson(anotherDoctorId, DOCTOR_NAME), HttpStatus.OK);
        ResponseEntity<String> responseEntityGetDirectionName = new ResponseEntity<>(JsonUtils.getUniversalJson(SP_ID, SP_NAME), HttpStatus.OK);

        when(restTemplate.getForEntity(urlForGetDoctorName, String.class)).thenReturn(responseEntityGetDoctorName);
        when(restTemplate.getForEntity(urlForGetDirectionName, String.class)).thenReturn(responseEntityGetDirectionName);

        TrackingInfoRsDto trackingInfoRsDto = gorzdravService.getTrackingInfo(HOSPITAL_ID, SP_ID, DOCTOR_ID);
        assertNotNull(trackingInfoRsDto,"Empty");
        assertAll("Checking the response fields",
                () -> assertEquals(trackingInfoRsDto.getDirectionName(), SP_NAME),
                () -> assertEquals(trackingInfoRsDto.getDoctorName(), "Нет информации"));
    }

    @Test
    void getTrackingInfoTestNoInfoAboutDirection() throws JSONException {
        Long anotherDirectionId = RandomUtils.nextLong();
        String urlForGetDoctorName = "/schedule/lpu/"+ HOSPITAL_ID + "/speciality/" + SP_ID + "/doctors";
        String urlForGetDirectionName = "/schedule/lpu/"+ HOSPITAL_ID + "/specialties";

        ResponseEntity<String> responseEntityGetDoctorName = new ResponseEntity<>(JsonUtils.getUniversalJson(DOCTOR_ID, DOCTOR_NAME), HttpStatus.OK);
        ResponseEntity<String> responseEntityGetDirectionName = new ResponseEntity<>(JsonUtils.getUniversalJson(anotherDirectionId, SP_NAME), HttpStatus.OK);

        when(restTemplate.getForEntity(urlForGetDoctorName, String.class)).thenReturn(responseEntityGetDoctorName);
        when(restTemplate.getForEntity(urlForGetDirectionName, String.class)).thenReturn(responseEntityGetDirectionName);

        TrackingInfoRsDto trackingInfoRsDto = gorzdravService.getTrackingInfo(HOSPITAL_ID, SP_ID, DOCTOR_ID);
        assertNotNull(trackingInfoRsDto,"Empty");
        assertAll("Checking the response fields",
                () -> assertEquals(trackingInfoRsDto.getDirectionName(), "Нет информации"),
                () -> assertEquals(trackingInfoRsDto.getDoctorName(), DOCTOR_NAME));
    }

    @ParameterizedTest
    @ValueSource(ints = {503, 502})
    void getTrackingInfoTestBadHttpStatusGetDoctorName(int httpStatus) {
        String urlForGetDoctorName = "/schedule/lpu/"+ HOSPITAL_ID + "/speciality/" + SP_ID + "/doctors";

        ResponseEntity<String> responseEntityGetDoctorName = new ResponseEntity<>("", HttpStatus.valueOf(httpStatus));
        when(restTemplate.getForEntity(urlForGetDoctorName, String.class)).thenReturn(responseEntityGetDoctorName);
        assertThrows(BackendUnavailableException.class, () ->gorzdravService.getTrackingInfo(HOSPITAL_ID, SP_ID, DOCTOR_ID));
    }

    @ParameterizedTest
    @ValueSource(ints = {503, 502})
    void getTrackingInfoTestBadHttpStatusGetDirectionalName(int httpStatus) throws JSONException {
        String urlForGetDoctorName = "/schedule/lpu/"+ HOSPITAL_ID + "/speciality/" + SP_ID + "/doctors";
        String urlForGetDirectionName = "/schedule/lpu/"+ HOSPITAL_ID + "/specialties";

        ResponseEntity<String> responseEntityGetDoctorName = new ResponseEntity<>(JsonUtils.getUniversalJson(DOCTOR_ID, DOCTOR_NAME), HttpStatus.OK);
        ResponseEntity<String> responseEntityGetDirectionName = new ResponseEntity<>("", HttpStatus.valueOf(httpStatus));

        when(restTemplate.getForEntity(urlForGetDoctorName, String.class)).thenReturn(responseEntityGetDoctorName);
        when(restTemplate.getForEntity(urlForGetDirectionName, String.class)).thenReturn(responseEntityGetDirectionName);
        assertThrows(BackendUnavailableException.class, () ->gorzdravService.getTrackingInfo(HOSPITAL_ID, SP_ID, DOCTOR_ID));
    }
}
