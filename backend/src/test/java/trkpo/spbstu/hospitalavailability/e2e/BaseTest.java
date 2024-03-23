package trkpo.spbstu.hospitalavailability.e2e;

import org.junit.BeforeClass;
import org.junit.jupiter.api.TestInstance;
import com.codeborne.selenide.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import trkpo.spbstu.hospitalavailability.e2e.pages.BasePage;
import trkpo.spbstu.hospitalavailability.e2e.pages.UnauthPage;

import static com.codeborne.selenide.Selenide.open;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BaseTest {

    protected static UnauthPage unauthPage;
    protected static final Logger logger = LoggerFactory.getLogger(BasePage.class);

    @BeforeClass
    public static void startDriver() {
        System.setProperty("webdriver.gecko.driver", "./src/test/resources/geckodriver");
        Configuration.browser = "firefox";
        Configuration.baseUrl = "http://localhost:4200";
        Configuration.browserSize = "1920x1080";
        Configuration.remoteConnectionTimeout = 10000;
        open("/");
        unauthPage = new UnauthPage();
    }
}
