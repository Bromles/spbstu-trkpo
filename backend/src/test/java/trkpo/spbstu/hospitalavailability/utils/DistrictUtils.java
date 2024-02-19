package trkpo.spbstu.hospitalavailability.utils;

import org.testcontainers.shaded.org.apache.commons.lang3.RandomUtils;
import trkpo.spbstu.hospitalavailability.entity.District;

import java.util.Random;

public class DistrictUtils {
    public static District getDistrict() {
        District district = new District();
        district.setId(RandomUtils.nextLong());
        district.setName("testDistrict");
        district.setGorzdravId(RandomUtils.nextLong());
        return district;
    }
}
