package trkpo.spbstu.hospitalavailability.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonUtils {

    public static String getHospitalBodySuccess(Long id, String lpuFullName, String lpuShortName, String address, String phone, Double longitude, Double latitude) throws JSONException {
        JSONObject hospital = new JSONObject();
        hospital.put("id", id);
        hospital.put("districtId", 1);
        hospital.put("lpuFullName", lpuFullName);
        hospital.put("lpuShortName", lpuShortName);
        hospital.put("address", address);
        hospital.put("phone", phone);
        hospital.put("longitude", longitude);
        hospital.put("latitude", latitude);
        JSONArray resultArray = new JSONArray();
        resultArray.put(hospital);
        JSONObject response = new JSONObject();
        response.put("result", resultArray);
        response.put("success", true);
        return response.toString();
    }

    public static String getSpecialties(Long id, String name, long count) throws JSONException {
        JSONObject specialties = new JSONObject();
        specialties.put("id", id);
        specialties.put("name", name);
        specialties.put("countFreeTicket", count);
        JSONArray resultArray = new JSONArray();
        resultArray.put(specialties);
        JSONObject response = new JSONObject();
        response.put("result", resultArray);
        response.put("success", true);
        return response.toString();
    }

    public static String getUniversalJson(Long id, String name) throws JSONException {
        JSONObject specialties = new JSONObject();
        specialties.put("id", id);
        specialties.put("name", name);
        JSONArray resultArray = new JSONArray();
        resultArray.put(specialties);
        JSONObject response = new JSONObject();
        response.put("result", resultArray);
        response.put("success", true);
        return response.toString();
    }
    public static String getBadJson(Long id) throws JSONException {
        JSONObject specialties = new JSONObject();
        specialties.put("id", id);
        JSONArray resultArray = new JSONArray();
        resultArray.put(specialties);
        JSONObject response = new JSONObject();
        response.put("result", resultArray);
        response.put("success", true);
        return response.toString();
    }
}
