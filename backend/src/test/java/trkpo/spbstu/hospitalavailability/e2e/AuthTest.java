package trkpo.spbstu.hospitalavailability.e2e;

import org.junit.Test;
import trkpo.spbstu.hospitalavailability.e2e.pages.MainPage;

import static org.junit.Assert.assertTrue;

public class AuthTest extends BaseTest{

    @Test
    public void validInputLoginTest() {
        MainPage mainPage = unauthPage.clickLoginBtn()
                .inputLogin("kate_boriso2002@mail.ru")
                .inputPasswd("123456")
                .clickLoginBtn();

        String userInfo = mainPage.getUserInfo();
        assertTrue(userInfo.contains("Borisova\nkate_boriso2002@mail.ru"));
    }

//    @Test
//    public void invalidInputLoginTest() {
//        unauthPage.clickLoginBtn();
//        loginPage.inputLogin("doesnotexist@mail.ru");
//        loginPage.inputPasswd("123456");
//        loginPage.clickLoginBtn();
//
//        WebElement error = driver.findElement(By.xpath("//*[@id='input-error']"));
//        assertNotNull(error);
//    }
}
