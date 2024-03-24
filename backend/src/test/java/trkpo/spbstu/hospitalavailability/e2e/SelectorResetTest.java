package trkpo.spbstu.hospitalavailability.e2e;

import org.junit.jupiter.api.Test;
import trkpo.spbstu.hospitalavailability.e2e.pages.MainPage;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SelectorResetTest extends BaseTest{

    private static final String DEFAULT_EMAIL = "spbstu.trkpo@yandex.ru";
    private static final String DEFAULT_PWD = "5130904/00104";
    private static final String EXPECTED_DISTRICT = "Выберите район";
    private static final String EXPECTED_HOSPITAL = "Выберите учреждение";
    private static final String EXPECTED_DOCTOR = "Выберите доктора (опционально)";
    private static final String EXPECTED_DIRECTION = "Выберите направление";

    @Test
    void selectorResetTest() {
        logger.info("Тест проверяет, что при сбросе селектора, все селекторы ниже сбрасываются до начального");

        logger.info("Залогинемся юзером " + DEFAULT_EMAIL);
        unauthPage.clickLoginBtn()
                .inputLogin(DEFAULT_EMAIL)
                .inputPasswd(DEFAULT_PWD)
                .clickLoginBtn();

        logger.info("Веберем район, больницу, направление и врача");
        MainPage mainPage = new MainPage();
        mainPage.selectDistrict(1);
        mainPage.selectHospital(1);
        mainPage.selectDirection(1);
        mainPage.selectDoctor(1);

        logger.info("Проверим, что можно сохранить отслеживание, что кнопка кликабельна");
        assertTrue(mainPage.checkStartTrackingBtnIsEnabled(), "Кнопка не кликабельна");

        logger.info("Сбросим селектор районов");
        mainPage.selectDistrict(0);

        logger.info("Проверим, что  остальные селекторы сбросились и кнопка сохранить отслеживание не кликабельна");
        assertThat(mainPage.getSelectedDistrict()).isEqualTo(EXPECTED_DISTRICT);
        assertTrue(mainPage.checkDistrictSelectorIsEnabled(), "Нельзя выбрать район");

        assertThat(mainPage.getSelectedHospital()).isEqualTo(EXPECTED_HOSPITAL);
        assertFalse(mainPage.checkHospitalSelectorIsEnabled(), "Можно выбрать больницу без указания района");

        assertThat(mainPage.getSelectedDirection()).isEqualTo(EXPECTED_DIRECTION);
        assertFalse(mainPage.checkDirectionSelectorIsEnabled(), "Можно выбрать направление без указания больницы");

        assertThat(mainPage.getSelectedDoctor()).isEqualTo(EXPECTED_DOCTOR);
        assertFalse(mainPage.checkDoctorSelectorIsEnabled(), "Можно выбрать врача без указания направления");

        assertFalse(mainPage.checkStartTrackingBtnIsEnabled(), "Кнопка не кликабельна");
        logger.info("Успешно проверили сброс селектора");

        mainPage.clickExit();
        assertTrue(unauthPage.isActiveLoginBtn());
    }
}
