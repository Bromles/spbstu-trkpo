package trkpo.spbstu.hospitalavailability.e2e;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.junit.Test;
import org.openqa.selenium.By;
import trkpo.spbstu.hospitalavailability.e2e.pages.MainPage;
import trkpo.spbstu.hospitalavailability.e2e.pages.keycloak.KeycloakRegistrationPage;
import trkpo.spbstu.hospitalavailability.e2e.pages.mail.EmailLoginPage;
import trkpo.spbstu.hospitalavailability.e2e.pages.mail.EmailMainPage;
import trkpo.spbstu.hospitalavailability.e2e.pages.mail.EmailUnauthPage;

import java.util.Set;

import static com.codeborne.selenide.Selenide.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class RegistrationTest extends BaseTest{

    @Test
    public void validInputRegistrationTest() {
        String emailLogin = "test_testov2025";
        String emailPwd = "u2BeyRrOBo2&";
        String pwd = "123456";
        unauthPage.clickLoginBtn()
                .clickRegisterRef()
                .inputFirstName("qwerty")
                .inputLastName("asdf")
                .inputEmail(emailLogin + "@mail.ru")
                .inputPasswd(pwd)
                .inputPasswdConfirm(pwd)
                .clickRegisterBtn();

        String verificationTitle = $(By.xpath("//*[@id='kc-page-title']")).getText().trim();
        assertTrue(Set.of("Подтверждение адреса E-mail", "Email verification").contains(verificationTitle));

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
        String link = new EmailMainPage().openLastMessageAndGetLing();
        open(link);
        String userInfo = new MainPage().getUserInfo();
        assertTrue(userInfo.contains(emailLogin + "@mail.ru"));
    }

    @Test
    public void invalidInputRegistrationTest() {
        KeycloakRegistrationPage registrationPage = unauthPage.clickLoginBtn()
                .clickRegisterRef()
                .inputFirstName("qwerty")
                .inputLastName("asdf")
                .inputEmail("doesnotexist@mail.ru")
                .inputPasswd("123")
                .inputPasswdConfirm("123");

        registrationPage.clickRegisterBtn();

        assertTrue(Set.of("Некорректный пароль: длина пароля должна быть не менее 6 символов(а).",
                                "Invalid password: minimum length 6.")
                        .contains(registrationPage.getPwdError().getText().trim()));

        //длинный пароль
        registrationPage.inputPasswd("12345678912345678912345");
        registrationPage.inputPasswdConfirm("12345678912345678912345");
        registrationPage.clickRegisterBtn();
        assertEquals("Invalid password: maximum length 20.", registrationPage.getPwdError().getText().trim());

        //некорректная почта
        registrationPage.cleanInputEmail();
        registrationPage.inputEmail("doesnotexist.mail.ru");
        registrationPage.inputPasswd("12345678");
        registrationPage.inputPasswdConfirm("12345678");
        registrationPage.clickRegisterBtn();

        assertTrue(Set.of("Invalid email address.", "Неправильный E-mail.")
                .contains(registrationPage.getEmailError().getText().trim()));
    }
}
