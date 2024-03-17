package trkpo.spbstu.hospitalavailability.e2e;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import trkpo.spbstu.hospitalavailability.e2e.pages.MainPage;
import trkpo.spbstu.hospitalavailability.e2e.pages.mail.EmailLoginPage;
import trkpo.spbstu.hospitalavailability.e2e.pages.mail.EmailMainPage;
import trkpo.spbstu.hospitalavailability.e2e.pages.mail.EmailUnauthPage;
import trkpo.spbstu.hospitalavailability.e2e.pages.mail.EmailPasswordPage;
import trkpo.spbstu.hospitalavailability.e2e.pages.keycloak.KeycloakLoginPage;
import trkpo.spbstu.hospitalavailability.e2e.pages.keycloak.KeycloakRegistrationPage;
import trkpo.spbstu.hospitalavailability.e2e.pages.UnauthPage;

import static org.junit.Assert.assertTrue;

public class RegistrationTest {

    public static WebDriver driver;
    public static UnauthPage unauthPage;
    public static MainPage mainPage;
    public static KeycloakLoginPage loginPage;
    public static KeycloakRegistrationPage registrationPage;
    public static EmailUnauthPage emailUnauthPage;
    public static EmailLoginPage emailLoginPage;
    public static EmailPasswordPage emailPasswordPage;
    public static EmailMainPage emailMainPage;

    @BeforeClass
    public static void setup() {
        System.setProperty("webdriver.gecko.driver", "/Users/kate-bor/Downloads/geckodriver");

        driver = new FirefoxDriver();
        unauthPage = new UnauthPage(driver);
        mainPage = new MainPage(driver);
        loginPage = new KeycloakLoginPage(driver);
        registrationPage = new KeycloakRegistrationPage(driver);
        emailUnauthPage = new EmailUnauthPage(driver);
        emailLoginPage = new EmailLoginPage(driver);
        emailPasswordPage = new EmailPasswordPage(driver);
        emailMainPage = new EmailMainPage(driver);

        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        driver.get("http://localhost:4200/");

    }

    @Test
    public void validInputRegistrationTest() {
        String emailLogin = "test_testov2025";
        String emailPwd = "u2BeyRrOBo2&";
        String pwd = "123456";
        unauthPage.clickLoginBtn();

        loginPage.clickRegisterRef();

        registrationPage.inputFirstName("qwerty");
        registrationPage.inputLastName("asdf");
        registrationPage.inputEmail(emailLogin + "@mail.ru");
        registrationPage.inputPasswd(pwd);
        registrationPage.inputPasswdConfirm(pwd);
        registrationPage.clickRegisterBtn();

        String verificationTitle = driver.findElement(By.xpath("//*[@id='kc-page-title']")).getText().trim();
        assertTrue(Set.of("Подтверждение адреса E-mail", "Email verification").contains(verificationTitle));

        driver.get("https://mail.ru/");
        emailUnauthPage.clickLoginBtn();
        WebElement frameElement = driver.findElement(By.xpath("//iframe[@class='ag-popup__frame__layout__iframe']"));
        driver.switchTo().frame(frameElement);
        emailLoginPage.inputLogin(emailLogin);
        emailLoginPage.clickEnterPasswordBtn();

        emailPasswordPage.inputPassword(emailPwd);
        emailPasswordPage.clickLoginBtn();

        driver.switchTo().defaultContent();

        //читаем последнее сообщение
        emailMainPage.openLastMessage();
        WebElement verificationLink = driver.findElement(By.xpath("//div[@class='letter__body']//a[1]"));
        driver.get(verificationLink.getAttribute("href"));

        WebDriverWait wait = new WebDriverWait(driver, Duration.of(10, ChronoUnit.SECONDS));
        wait.until((ExpectedCondition<Boolean>) driver -> !mainPage.getUserInfo().equals(""));
        String userInfo = mainPage.getUserInfo();
        assertTrue(userInfo.contains(emailLogin + "@mail.ru"));
    }
}
