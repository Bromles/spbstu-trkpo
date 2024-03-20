package trkpo.spbstu.hospitalavailability.e2e;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.junit.Test;
import org.openqa.selenium.By;
import trkpo.spbstu.hospitalavailability.e2e.pages.MainPage;
import trkpo.spbstu.hospitalavailability.e2e.pages.keycloak.KeycloakLoginPage;
import trkpo.spbstu.hospitalavailability.e2e.pages.mail.EmailLoginPage;
import trkpo.spbstu.hospitalavailability.e2e.pages.mail.EmailMainPage;
import trkpo.spbstu.hospitalavailability.e2e.pages.mail.EmailUnauthPage;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static org.junit.Assert.assertTrue;

public class AuthTest extends BaseTest{

    @Test
    public void validInputLoginTest() {
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
    public void invalidInputLoginTest() {
        KeycloakLoginPage keycloakLoginPage = unauthPage.clickLoginBtn();
                keycloakLoginPage.inputLogin("doesnotexist@mail.ru")
                .inputPasswd("123456")
                .clickLoginBtn();
        assertTrue(keycloakLoginPage.errorIsDisplayed());
    }

//    @Test
//    public void validChangePassword() {
//        //подготовленный пользователь test_testov2026@mail.ru
//        String emailLogin = "test_testov2026";
//        String emailPwd = "XiIsgUiAt23#";
//        unauthPage.clickLoginBtn()
//                .clickForgotPasswordRef()
//                .inputEmail(emailLogin + "@mail.ru")
//                .clickSubmitBtn();
//
//        open("https://mail.ru/");
//        new EmailUnauthPage().clickLoginBtn();
//        SelenideElement frameElement = $(By.xpath("//iframe[@class='ag-popup__frame__layout__iframe']"));
//        Selenide.switchTo().frame(frameElement);
//
//        EmailMainPage emailMainPage = new EmailLoginPage()
//                .inputLogin(emailLogin)
//                .clickEnterPasswordBtn()
//                .inputPassword(emailPwd)
//                .clickLoginBtn();
//        Selenide.switchTo().defaultContent();
//
//        String link = emailMainPage.openLastMessageAndGetLing();
//        open(link);
//
//        //TODO
//
//    }
}
