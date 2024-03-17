package trkpo.spbstu.hospitalavailability.e2e;

import java.util.concurrent.TimeUnit;

import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import trkpo.spbstu.hospitalavailability.e2e.pages.keycloak.KeycloakLoginPage;
import trkpo.spbstu.hospitalavailability.e2e.pages.MainPage;
import trkpo.spbstu.hospitalavailability.e2e.pages.UnauthPage;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class AuthTest {

    public static WebDriver driver;
    public static UnauthPage unauthPage;
    public static KeycloakLoginPage loginPage;
    public static MainPage mainPage;

    @BeforeClass
    public static void setup() {
        System.setProperty("webdriver.gecko.driver", "/Users/kate-bor/Downloads/geckodriver");

        driver = new FirefoxDriver();
        unauthPage = new UnauthPage(driver);
        loginPage = new KeycloakLoginPage(driver);
        mainPage = new MainPage(driver);

        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        driver.get("http://localhost:4200/");

    }

    @Test
    public void validInputLoginTest() {
        unauthPage.clickLoginBtn();
        loginPage.inputLogin("kate_boriso2002@mail.ru");
        loginPage.inputPasswd("123456");
        loginPage.clickLoginBtn();

        String userInfo = mainPage.getUserInfo();
        assertTrue(userInfo.contains("kate_boriso2002@mail.ru"));
    }

    @Test
    public void invalidInputLoginTest() {
        unauthPage.clickLoginBtn();
        loginPage.inputLogin("doesnotexist@mail.ru");
        loginPage.inputPasswd("123456");
        loginPage.clickLoginBtn();

        WebElement error = driver.findElement(By.xpath("//*[@id='input-error']"));
        assertNotNull(error);
    }
}
