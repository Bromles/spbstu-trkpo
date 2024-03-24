package trkpo.spbstu.hospitalavailability.e2e;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance;
import trkpo.spbstu.hospitalavailability.e2e.pages.UnauthPage;

import static com.codeborne.selenide.Selenide.open;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class BaseTest {

    protected static UnauthPage unauthPage;

    @BeforeAll
    public static void startDriver() {
        if (System.getProperty("os.name", "").contains("Windows")) {
            System.setProperty("webdriver.gecko.driver", "./src/test/resources/geckodriver.exe");
            Configuration.browserBinary = "C:/Program Files/Mozilla Firefox/firefox.exe";
        } else {
            System.setProperty("webdriver.gecko.driver", "./src/test/resources/geckodriver");
        }

        Configuration.browser = "firefox";
        Configuration.baseUrl = "http://localhost:4200";
        Configuration.browserSize = "1920x1080";
        Configuration.timeout = 10000;
    }

    @BeforeEach
    public void startPage() {
        open("/");
        unauthPage = new UnauthPage();
    }
}
