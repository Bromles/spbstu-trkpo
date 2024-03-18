package trkpo.spbstu.hospitalavailability.e2e;

import org.junit.Test;
import trkpo.spbstu.hospitalavailability.e2e.pages.MainPage;
import trkpo.spbstu.hospitalavailability.e2e.pages.keycloak.KeycloakLoginPage;

import static org.junit.Assert.assertTrue;

public class AuthTest extends BaseTest{

    @Test
    public void validInputLoginTest() {
        unauthPage.clickLoginBtn()
                .inputLogin("kate_boriso2002@mail.ru")
                .inputPasswd("123456")
                .clickLoginBtn();

        String userInfo = new MainPage().getUserInfo();
        assertTrue(userInfo.contains("Borisova\nkate_boriso2002@mail.ru"));
    }

    @Test
    public void invalidInputLoginTest() {
        KeycloakLoginPage keycloakLoginPage = unauthPage.clickLoginBtn();
                keycloakLoginPage.inputLogin("doesnotexist@mail.ru")
                .inputPasswd("123456")
                .clickLoginBtn();
        assertTrue(keycloakLoginPage.errorIsDisplayed());
    }
}
