package trkpo.spbstu.hospitalavailability.e2e.pages;

import org.openqa.selenium.By;
import trkpo.spbstu.hospitalavailability.e2e.pages.keycloak.KeycloakLoginPage;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class UnauthPage extends BasePage{

    private static final By loginBtn = By.xpath("//*[contains(text(), 'Вход | Регистрация')]");
    @Override
    protected void checkPage() {
        logger.info("Провалидируем анонимную страницу");
        $(loginBtn).shouldBe(visible.because("Нет кнопик Входа/Регистрации"));
        logger.info("Успешно открыли анонимную страницу");
    }

    public KeycloakLoginPage clickLoginBtn() {
        $(loginBtn).click();
        return new KeycloakLoginPage();
    }
}
