package trkpo.spbstu.hospitalavailability.e2e.pages.keycloak;

import org.openqa.selenium.By;
import trkpo.spbstu.hospitalavailability.e2e.pages.BasePage;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class KeycloakRegistrationPage extends BasePage {

    private static final By firstNameField = By.id("firstName");
    private static final By lastNameField = By.id("lastName");
    private static final By emailField = By.id("email");
    private static final By passwordField = By.id("password");
    private static final By passwordConfirmField = By.id("password-confirm");
    private static final By submit =  By.xpath("//*[contains(@type, 'submit')]");

    @Override
    protected void checkPage() {
        logger.info("Провалидируем страницу Регистрации");
        $(firstNameField).shouldBe(visible.because("Нет поля ввода Имени"));
        $(lastNameField).shouldBe(visible.because("Нет поля ввода Фамилии"));
        $(emailField).shouldBe(visible.because("Нет поля ввода Почта"));
        $(passwordField).shouldBe(visible.because("Нет поля ввода Пароля"));
        $(passwordConfirmField).shouldBe(visible.because("Нет поля ввода Подтверждения пароля"));
        $(submit).shouldBe(visible.because("Нет кнопки Сохранить"));
        logger.info("Успешно открыли страницу регистрации");
    }

    public KeycloakRegistrationPage inputFirstName(String firstName) {
        $(firstNameField).sendKeys(firstName);
        return this;
    }

    public KeycloakRegistrationPage inputLastName(String lastName) {
        $(lastNameField).sendKeys(lastName);
        return this;

    }

    public KeycloakRegistrationPage inputEmail(String email) {
        $(emailField).sendKeys(email);
        return this;
    }

    public KeycloakRegistrationPage inputPasswd(String passwd) {
        $(passwordField).sendKeys(passwd);
        return this;
    }

    public KeycloakRegistrationPage inputPasswdConfirm(String passwd) {
        $(passwordConfirmField).sendKeys(passwd);
        return this;
    }

    public void clickRegisterBtn() {
        $(submit).click();
    }
}
