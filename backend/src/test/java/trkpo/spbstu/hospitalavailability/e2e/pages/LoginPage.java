package trkpo.spbstu.hospitalavailability.e2e.pages;

import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class LoginPage extends PageBase {

    private static final By USERNAME = By.id("username");
    private static final By PASSWORD = By.id("password");

    @Override
    protected void checkPage() {
        logger.info("Валидируем страницу входа");
        $(USERNAME).shouldBe(visible.because("Не найдено поле ввода логина"));
        $(PASSWORD).shouldBe(visible.because("Не найдено поле ввода пароля"));
    }
}
