package trkpo.spbstu.hospitalavailability.e2e;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import trkpo.spbstu.hospitalavailability.e2e.pages.MainPage;
import trkpo.spbstu.hospitalavailability.e2e.pages.keycloak.KeycloakRegistrationPage;
import trkpo.spbstu.hospitalavailability.e2e.pages.mail.EmailLoginPage;
import trkpo.spbstu.hospitalavailability.e2e.pages.mail.EmailMainPage;
import trkpo.spbstu.hospitalavailability.e2e.pages.mail.EmailUnauthPage;

import java.util.Set;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RegistrationTest extends BaseTest {

    @Test
    void validInputRegistrationTest() {
        String emailLogin = "test_testov2025";
        String emailPwd = "u2BeyRrOBo2&";
        String pwd = "123456";
        KeycloakRegistrationPage keycloakRegistrationPage = unauthPage.clickLoginBtn().clickRegisterRef();

        keycloakRegistrationPage
                .inputFirstName("qwerty")
                .inputLastName("asdf")
                .inputEmail(emailLogin + "@mail.ru")
                .inputPasswd(pwd)
                .inputPasswdConfirm(pwd)
                .clickRegisterBtn();

        WebElement emailError = keycloakRegistrationPage.getEmailError();

        if (Set.of("E-mail already exists.", "E-mail уже существует.").contains(emailError.getText().trim())) {
            return;
        }

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
        String link = new EmailMainPage(EmailMainPage.Purpose.VERIFY_EMAIL).openLastMessageAndGetLink();
        open(link);
        String userInfo = new MainPage().getUserInfo();
        assertTrue(userInfo.contains(emailLogin + "@mail.ru"));
    }

    @Test
    void invalidInputRegistrationTest() {
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
