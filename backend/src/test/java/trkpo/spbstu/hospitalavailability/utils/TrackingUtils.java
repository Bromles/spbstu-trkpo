package trkpo.spbstu.hospitalavailability.utils;

import org.testcontainers.shaded.org.apache.commons.lang3.RandomUtils;
import trkpo.spbstu.hospitalavailability.entity.Tracking;

import java.time.LocalDateTime;

import static trkpo.spbstu.hospitalavailability.utils.HospitalUtils.getHospital;

public class TrackingUtils {
    public static Tracking getActiveTrackingWithDoctorId() {
        Tracking tracking = new Tracking();
        tracking.setClient(ClientUtils.getClient());
        tracking.setDate(LocalDateTime.now());
        tracking.setDoctorId(RandomUtils.nextLong());
        tracking.setFinished(false);
        tracking.setDirectionId(RandomUtils.nextLong());
        tracking.setHospital(getHospital());
        tracking.setId(RandomUtils.nextLong());
        return tracking;
    }
}
