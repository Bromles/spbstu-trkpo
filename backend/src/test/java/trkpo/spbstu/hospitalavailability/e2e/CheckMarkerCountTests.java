package trkpo.spbstu.hospitalavailability.e2e;

import org.junit.jupiter.api.Test;
import trkpo.spbstu.hospitalavailability.e2e.pages.MainPage;

import static org.assertj.core.api.Assertions.assertThat;

class CheckMarkerCountTests extends BaseTest {

    private static final String DEFAULT_EMAIL = "spbstu.trkpo@yandex.ru";
    private static final String DEFAULT_PWD = "5130904/00104";

    @Test
    void checkMarkerCountTests() {
        logger.info("Тест проверяет, что при выборе района количество маркеров на карте становится меньше," +
                " а при выборе больницы, маркер остается один");

        logger.info("Залогинемся юзером " + DEFAULT_EMAIL);
        unauthPage.clickLoginBtn()
                .inputLogin(DEFAULT_EMAIL)
                .inputPasswd(DEFAULT_PWD)
                .clickLoginBtn();

        logger.info("Веберем район, больницу, направление и врача");
        MainPage mainPage = new MainPage();
        int count = mainPage.getMapWrapper().getMarkerCount();
        mainPage.selectDistrict(1);
        assertThat(mainPage.getMapWrapper().getMarkerCount()).as("Количество маркеров не стало меньше")
                .isLessThan(count);
        mainPage.selectHospital(1);
        assertThat(mainPage.getMapWrapper().getMarkerCount()).as("Должен остаться 1 маркер")
                .isEqualTo(1);
        logger.info("Сбросим селектор районов");
        mainPage.selectDistrict(0);
        assertThat(mainPage.getMapWrapper().getMarkerCount()).as("Должен быть больше чем один маркер")
                .isGreaterThan(1);
        logger.info("Успешно проверили количество маркеров на карте");
    }
}
