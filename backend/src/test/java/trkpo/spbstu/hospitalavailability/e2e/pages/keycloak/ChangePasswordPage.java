package trkpo.spbstu.hospitalavailability.e2e.pages.keycloak;

import org.openqa.selenium.By;
import trkpo.spbstu.hospitalavailability.e2e.pages.BasePage;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class ChangePasswordPage extends BasePage {

    private static final By emailField = By.xpath("//*[@id='username']");
    private static final By sumitBtn = By.xpath("//*[@class='pf-c-button pf-m-primary pf-m-block btn-lg']");

    @Override
    protected void checkPage() {
        logger.info("Провалидируем страницу смены пароля");
        $(emailField).shouldBe(visible.because("Нет поля ввода почты"));
        $(sumitBtn).shouldBe(visible.because("Нет кнопки подтверждения"));
        logger.info("Успешно открыли страницу смены пароля");
    }

    public ChangePasswordPage inputEmail(String email) {
        $(emailField).sendKeys(email);
        return this;
    }

    public void clickSubmitBtn() {
        $(sumitBtn).click();
    }
}
