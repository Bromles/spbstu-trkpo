package trkpo.spbstu.hospitalavailability;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class HospitalAvailabilityApplication {
    public static void main(String[] args) {
        SpringApplication.run(HospitalAvailabilityApplication.class, args);
    }
}
