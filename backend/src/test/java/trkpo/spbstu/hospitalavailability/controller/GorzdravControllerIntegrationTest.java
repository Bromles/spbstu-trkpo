package trkpo.spbstu.hospitalavailability.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import trkpo.spbstu.hospitalavailability.entity.District;
import trkpo.spbstu.hospitalavailability.repository.DistrictRepository;

import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class GorzdravControllerIntegrationTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private DistrictRepository districtRepository;

    @AfterEach
    public void resetDb() {
        districtRepository.deleteAll();
    }

    @Test
    void whenValidInput_thenReturns200() {
        District district = new District();
        district.setName("SomeDistrict");
        districtRepository.saveAndFlush(district);

        ResponseEntity<List> responseEntity =
                restTemplate.getForEntity("/v1/gorzdrav/district", List.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(Objects.requireNonNull(responseEntity.getBody()).size()).isEqualTo(1);
    }

}
