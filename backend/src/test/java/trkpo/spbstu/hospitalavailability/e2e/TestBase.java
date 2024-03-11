package trkpo.spbstu.hospitalavailability.e2e;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import trkpo.spbstu.hospitalavailability.HospitalAvailabilityApplication;
import trkpo.spbstu.hospitalavailability.repository.ClientRepository;
import trkpo.spbstu.hospitalavailability.repository.DistrictRepository;
import trkpo.spbstu.hospitalavailability.repository.HospitalRepository;
import trkpo.spbstu.hospitalavailability.repository.TrackingRepository;

@SpringBootTest(classes = HospitalAvailabilityApplication.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TestBase {

    protected static final Logger logger = LoggerFactory.getLogger(TestBase.class);
    protected WebDriver driver;

    @Autowired
    protected HospitalRepository hospitalRepository;
    @Autowired
    protected DistrictRepository districtRepository;
    @Autowired
    protected ClientRepository clientRepository;
    @Autowired
    protected TrackingRepository trackingRepository;

    @BeforeEach
    void startDriver() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options=new ChromeOptions();
        options.setHeadless(true);
        options.addArguments("window-size=1920,1200");
        driver=new ChromeDriver(options);
        logger.info("Переходим на страницу логина");
        driver.get("http://localhost:8123"); // куда заходить?
    }
}
