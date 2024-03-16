package trkpo.spbstu.hospitalavailability.e2e;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance;
import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import trkpo.spbstu.hospitalavailability.HospitalAvailabilityApplication;
import trkpo.spbstu.hospitalavailability.e2e.pages.LoginPage;
import trkpo.spbstu.hospitalavailability.repository.ClientRepository;
import trkpo.spbstu.hospitalavailability.repository.DistrictRepository;
import trkpo.spbstu.hospitalavailability.repository.HospitalRepository;
import trkpo.spbstu.hospitalavailability.repository.TrackingRepository;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

@SpringBootTest(classes = HospitalAvailabilityApplication.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class E2eTestBase {

    protected static final Logger logger = LoggerFactory.getLogger(E2eTestBase.class);
    protected static final By LOGIN_BUTTON = By.xpath("//*[contains(text(), 'Вход | Регистрация')]");

    @Autowired
    protected HospitalRepository hospitalRepository;
    @Autowired
    protected DistrictRepository districtRepository;
    @Autowired
    protected ClientRepository clientRepository;
    @Autowired
    protected TrackingRepository trackingRepository;

    @BeforeEach
    public void startDriver() {
        WebDriverManager.chromedriver().setup();
        logger.info("Переходим на страницу главную");
        open("http://localhost:8123");
    }

    protected LoginPage goToLoginPage() {
        logger.info("Перейдем на страницу ввода логина/пароля");
        $(LOGIN_BUTTON).shouldBe(visible.because("Кнопка не найдена")).click();
        return new LoginPage();
    }
}
