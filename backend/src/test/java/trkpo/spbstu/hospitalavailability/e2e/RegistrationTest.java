package trkpo.spbstu.hospitalavailability.e2e;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.junit.Test;
import org.openqa.selenium.By;
import trkpo.spbstu.hospitalavailability.e2e.pages.MainPage;
import trkpo.spbstu.hospitalavailability.e2e.pages.mail.EmailLoginPage;
import trkpo.spbstu.hospitalavailability.e2e.pages.mail.EmailMainPage;
import trkpo.spbstu.hospitalavailability.e2e.pages.mail.EmailUnauthPage;

import java.util.Set;

import static com.codeborne.selenide.Selenide.*;
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

        EmailMainPage emailMainPage = new EmailLoginPage()
                .inputLogin(emailLogin)
                .clickEnterPasswordBtn()
                .inputPassword(emailPwd)
                .clickLoginBtn();
        Selenide.switchTo().defaultContent();

        String link = emailMainPage.openLastMessageAndGetLing();
        open(link);
        String userInfo = new MainPage().getUserInfo();
        assertTrue(userInfo.contains(emailLogin + "@mail.ru"));
    }
}
