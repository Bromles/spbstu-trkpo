package trkpo.spbstu.hospitalavailability.e2e;

import org.junit.Test;
import trkpo.spbstu.hospitalavailability.e2e.pages.MainPage;

import static org.junit.Assert.assertEquals;

public class AddAndDeleteTrackingWithoutDoctorTest extends BaseTest{

    private static final String DEFAULT_EMAIL = "spbstu.trkpo@yandex.ru";
    private static final String DEFAULT_PWD = "5130904/00104";

    @Test
    public void addAndDeleteTrackingWithoutDoctorTest() {
        unauthPage.clickLoginBtn()
                .inputLogin(DEFAULT_EMAIL)
                .inputPasswd(DEFAULT_PWD)
                .clickLoginBtn();
        MainPage mainPage = new MainPage();
        mainPage.selectDistrict(1);
        mainPage.selectHospital(1);
        mainPage.selectDirection(1);
        mainPage.clickStartTrackingBtn();
        assertEquals(mainPage.getTrackingCount(), 1);

        mainPage.clickStopTrackingBtn();
        assertEquals(0, mainPage.getTrackingCount());
    }
}
