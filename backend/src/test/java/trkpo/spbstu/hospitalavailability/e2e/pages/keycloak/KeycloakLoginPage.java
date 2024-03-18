package trkpo.spbstu.hospitalavailability.e2e.pages.keycloak;

import org.openqa.selenium.By;
import trkpo.spbstu.hospitalavailability.e2e.pages.BasePage;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class KeycloakLoginPage extends BasePage {

    private static final By loginBtn = By.xpath("//*[contains(@tabindex, '4')]");
    private static final By forgotPasswordRef = By.xpath("//*[contains(@tabindex, '5')]");
    private static final By registerRef = By.xpath("//*[contains(@tabindex, '6')]");
    private static final By loginField = By.id("username");
    private static final By passwordField = By.id("password");
    private static final By error = By.id("input-error");

    @Override
    protected void checkPage() {
        logger.info("Проволидируем страницу Логина");
        $(loginBtn).shouldBe(visible.because("Нет кнопки Логина"));
        $(forgotPasswordRef).shouldBe(visible.because("Нет кнопки смены пароля"));
        $(registerRef).shouldBe(visible.because("Нет кнопки регистрации"));
        $(loginField).shouldBe(visible.because("Нет поля ввода логина"));
        $(passwordField).shouldBe(visible.because("Нет кнопки ввода пароля"));
        logger.info("Успешно открыли страницу Логина");
    }

    public void clickLoginBtn() {
        $(loginBtn).click();
    }

    public void clickForgotPasswordRef() {
        $(forgotPasswordRef).click();
    }

    public KeycloakRegistrationPage clickRegisterRef() {
        $(registerRef).click();
        return new KeycloakRegistrationPage();
    }

    public KeycloakLoginPage inputLogin(String login) {
        $(loginField).sendKeys(login);
        return this;
    }

    public KeycloakLoginPage inputPasswd(String passwd) {
        $(passwordField).sendKeys(passwd);
        return this;
    }

    public boolean errorIsDisplayed() {
        return $(error).isDisplayed();
    }
}
