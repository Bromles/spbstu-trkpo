package trkpo.spbstu.hospitalavailability.service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.github.dockerjava.zerodep.shaded.org.apache.hc.client5.http.classic.methods.HttpGet;
import com.github.dockerjava.zerodep.shaded.org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import com.github.dockerjava.zerodep.shaded.org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import com.github.dockerjava.zerodep.shaded.org.apache.hc.client5.http.impl.classic.HttpClients;
import com.github.dockerjava.zerodep.shaded.org.apache.hc.core5.http.ParseException;
import com.github.dockerjava.zerodep.shaded.org.apache.hc.core5.http.io.entity.EntityUtils;

import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import trkpo.spbstu.hospitalavailability.dto.GorzdravHospitalRsDto;
import trkpo.spbstu.hospitalavailability.exception.BackendUnavailableException;
import trkpo.spbstu.hospitalavailability.exception.ForbiddenException;

@Service
@RequiredArgsConstructor
public class GorzdravService {
    private static final Logger logger = Logger.getLogger(GorzdravService.class.getName());
    private Long errorNum;

    public List<GorzdravHospitalRsDto> getHospitals() {
        errorNum = 0L;
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            final HttpGet httpGet = new HttpGet("https://gorzdrav.spb.ru/_api/api/v2/shared/lpus");

            try (CloseableHttpResponse response = httpClient.execute(httpGet)) {
                if (response.getCode() == 502 || response.getCode() == 503) {
                    logger.warning(response.getCode() + " " + response.getReasonPhrase());
                    throw new BackendUnavailableException("Gorzdrav is unavailable: " + response.getReasonPhrase());
                }
                String responseBody = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
                JSONArray array = new JSONObject(responseBody).getJSONArray("result");

                List<GorzdravHospitalRsDto> hospitalRs = new ArrayList<>();
                for (int i = 0; i < array.length(); i++) {
                    JSONObject jsObj = array.getJSONObject(i);
                    var hosp = convertToDto(jsObj);
                    if (hosp != null) {
                        hospitalRs.add(hosp);
                    }
                }
                return hospitalRs;

            } catch (ParseException e) {
                throw new ForbiddenException("Cannot parse json from Gorzdrav: " + e);
            }
        } catch (IOException e) {
            throw new ForbiddenException("Cannot create default http client for Gorzdrav: " + e);
        }
    }

    private GorzdravHospitalRsDto convertToDto(JSONObject jsObj) {
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
            logger.warning("cannot parse JSONObject, error number: " + errorNum);
        }
        return null;
    }
}
