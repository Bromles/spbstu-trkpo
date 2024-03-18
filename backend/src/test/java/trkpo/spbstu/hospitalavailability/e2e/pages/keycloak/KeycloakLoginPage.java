package trkpo.spbstu.hospitalavailability.e2e.pages.keycloak;

import org.openqa.selenium.By;
import trkpo.spbstu.hospitalavailability.e2e.pages.BasePage;
import trkpo.spbstu.hospitalavailability.e2e.pages.MainPage;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class KeycloakLoginPage extends BasePage {

    private static final By loginBtn = By.xpath("//*[contains(@tabindex, '4')]");
    private static final By forgotPasswordRef = By.xpath("//*[contains(@tabindex, '5')]");
    private static final By registerRef = By.xpath("//*[contains(@tabindex, '6')]");
    private static final By loginField = By.id("username");
    private static final By passwordField = By.id("password");

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

    public MainPage clickLoginBtn() {
        $(loginBtn).click();
        return new MainPage();
    }

    public void clickForgotPasswordRef() {
        $(forgotPasswordRef).click();
    }

    public void clickRegisterRef() {
        $(registerRef).click();
    }

    public KeycloakLoginPage inputLogin(String login) {
        $(loginField).sendKeys(login);
        return this;
    }

    public KeycloakLoginPage inputPasswd(String passwd) {
        $(passwordField).sendKeys(passwd);
        return this;
    }
}
