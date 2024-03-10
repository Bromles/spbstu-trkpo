package trkpo.spbstu.hospitalavailability.e2e;

import java.util.concurrent.TimeUnit;

import org.junit.BeforeClass;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import trkpo.spbstu.hospitalavailability.e2e.pages.KeycloakLoginPage;
import trkpo.spbstu.hospitalavailability.e2e.pages.UnauthPage;

public class RegistrationTest {

    public static WebDriver driver;
    public static UnauthPage unauthPage;
    public static KeycloakLoginPage loginPage;

    @BeforeClass
    public static void setup() {
        System.setProperty("webdriver.gecko.driver", "/Users/kate-bor/Downloads/geckodriver");

        driver = new FirefoxDriver();
        unauthPage = new UnauthPage(driver);
        loginPage = new KeycloakLoginPage(driver);

        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        driver.get("http://localhost:4200/");

    }

}
