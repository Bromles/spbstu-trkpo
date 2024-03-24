package trkpo.spbstu.hospitalavailability.e2e;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.By;
import trkpo.spbstu.hospitalavailability.e2e.pages.MainPage;
import trkpo.spbstu.hospitalavailability.e2e.pages.keycloak.KeycloakLoginPage;
import trkpo.spbstu.hospitalavailability.e2e.pages.mail.EmailLoginPage;
import trkpo.spbstu.hospitalavailability.e2e.pages.mail.EmailMainPage;
import trkpo.spbstu.hospitalavailability.e2e.pages.mail.EmailUnauthPage;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AuthTest extends BaseTest {

    @Test
    @Order(1)
    void validInputLoginTest() {
        //подготовленный пользователь kate_boriso2002@mail.ru
        unauthPage.clickLoginBtn()
                .inputLogin("kate_boriso2002@mail.ru")
                .inputPasswd("123456")
                .clickLoginBtn();

        MainPage mainPage = new MainPage();
        String userInfo = mainPage.getUserInfo();
        assertTrue(userInfo.contains("Borisova\nkate_boriso2002@mail.ru"));

        //выход
        mainPage.clickExit();
        assertTrue(unauthPage.isActiveLoginBtn());
    }

    @Test
    @Order(2)
    void invalidInputLoginTest() {
        KeycloakLoginPage keycloakLoginPage = unauthPage.clickLoginBtn();
        keycloakLoginPage.inputLogin("doesnotexist@mail.ru")
                .inputPasswd("123456")
                .clickLoginBtn();
        assertTrue(keycloakLoginPage.errorIsDisplayed());
    }

    @Test
    @Order(3)
    void validChangePassword() {
        //подготовленный пользователь test_testov2026@mail.ru
        String emailLogin = "test_testov2026";
        String emailPwd = "XiIsgUiAt23#";
        unauthPage.clickLoginBtn()
                .clickForgotPasswordRef()
                .inputEmail(emailLogin + "@mail.ru")
                .clickSubmitBtn();

        open("https://mail.ru/");
        new EmailUnauthPage().clickLoginBtn();
        SelenideElement frameElement = $(By.xpath("//iframe[@class='ag-popup__frame__layout__iframe']"));
        Selenide.switchTo().frame(frameElement);

        new EmailLoginPage()
                .inputLogin(emailLogin)
                .clickEnterPasswordBtn()
                .inputPassword(emailPwd)
                .clickLoginBtn();
        Selenide.switchTo().defaultContent();

        Selenide.sleep(5000);

        String link = new EmailMainPage(EmailMainPage.Purpose.RESET_PASSWORD).openLastMessageAndGetLink();
        open(link);

        String userInfo = new MainPage().getUserInfo();
        assertTrue(userInfo.contains(emailLogin + "@mail.ru"));
    }
}
