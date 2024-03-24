package trkpo.spbstu.hospitalavailability.e2e;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import trkpo.spbstu.hospitalavailability.e2e.pages.UnauthPage;

import static com.codeborne.selenide.Selenide.open;

public abstract class BaseTest {

    protected static UnauthPage unauthPage;

    @BeforeAll
    public static void startDriver() {
        if (isCi()) {
            System.setProperty("webdriver.gecko.driver", "geckodriver");
        } else {
            if (isWindows()) {
                System.setProperty("webdriver.gecko.driver", "./src/test/resources/drivers/geckodriver-win.exe");
                Configuration.browserBinary = "C:/Program Files/Mozilla Firefox/firefox.exe";
            } else if (isMac()) {
                System.setProperty("webdriver.gecko.driver", "./src/test/resources/drivers/geckodriver-macos");
            } else if (isLinux()) {
                System.setProperty("webdriver.gecko.driver", "./src/test/resources/drivers/geckodriver-linux");
            }
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

    private static String getOsName() {
        return System.getProperty("os.name", "").toLowerCase();
    }

    private static boolean isMac() {
        return getOsName().contains("mac");
    }

    private static boolean isLinux() {
        return getOsName().contains("linux");
    }

    private static boolean isWindows() {
        return getOsName().contains("windows");
    }

    private static boolean isCi() {
        return Boolean.parseBoolean(System.getProperty("isCI", "false"));
    }
}
