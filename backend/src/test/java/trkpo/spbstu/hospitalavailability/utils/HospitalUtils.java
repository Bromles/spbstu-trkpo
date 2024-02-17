package trkpo.spbstu.hospitalavailability.utils;

import org.testcontainers.shaded.org.apache.commons.lang3.RandomUtils;
import trkpo.spbstu.hospitalavailability.entity.Hospital;

public class HospitalUtils {
    public static Hospital getHospital() {
        Hospital hospital = new Hospital();
        hospital.setAddress("address");
        hospital.setLatitude(RandomUtils.nextDouble());
        hospital.setPhone("phone");
        hospital.setShortName("shortName");
        hospital.setDistrictId(RandomUtils.nextLong());
        hospital.setFullName("fullName");
        hospital.setDistrict(DistrictUtils.getDistrict());
        hospital.setId(RandomUtils.nextLong());
        hospital.setGorzdravId(RandomUtils.nextLong());
        return hospital;
    }
}
