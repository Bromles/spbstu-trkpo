package trkpo.spbstu.hospitalavailability.e2e;

import org.junit.Test;
import trkpo.spbstu.hospitalavailability.e2e.pages.MainPage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class TrackingTest extends BaseTest {

    private String DEFAULT_EMAIL = "spbstu.trkpo@yandex.ru";
    private String DEFAULT_PWD = "5130904/00104";

    private MainPage mainPage = new MainPage();

    private void signIn() {
        unauthPage.clickLoginBtn()
                .inputLogin(DEFAULT_EMAIL)
                .inputPasswd(DEFAULT_PWD)
                .clickLoginBtn();
    }

    private void stopSession() {
        mainPage.clickStopTrackingBtn();
        mainPage.clickExitBtn();
    }

    // Добавление нового отслеживания с использованием селекторов с указанием врача
    @Test
    public void addTrackingWithDoctorTest() {
        signIn();
        mainPage.selectDistrict(1);
        mainPage.selectHospital(1);
        mainPage.selectDirection();
        mainPage.selectDoctor();
        mainPage.clickStartTrackingBtn();

        assertNotNull(mainPage.getTracking());

        stopSession();
    }

    // Добавление нового отслеживания с использованием селекторов без указания врача
    @Test
    public void addTrackingWithoutDoctorTest() {
        signIn();

        mainPage.selectDistrict(1);
        mainPage.selectHospital(1);
        mainPage.selectDirection();
        mainPage.clickStartTrackingBtn();

        assertNotNull(mainPage.getTracking());

        stopSession();
    }

    // Удаление отслеживания
    @Test
    public void deleteTrackingTest() {
        signIn();

        mainPage.selectDistrict(1);
        mainPage.selectHospital(1);
        mainPage.selectDirection();
        mainPage.clickStartTrackingBtn();
        mainPage.clickStopTrackingBtn();

        assertEquals("", mainPage.getTracking());

        mainPage.clickExitBtn();
    }
}

