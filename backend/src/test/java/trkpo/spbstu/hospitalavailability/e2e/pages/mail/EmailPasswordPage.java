package trkpo.spbstu.hospitalavailability.e2e.pages.mail;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import trkpo.spbstu.hospitalavailability.e2e.pages.BasePage;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class EmailPasswordPage extends BasePage {
    private static final By passwordField =  By.xpath("//*[contains(@name, 'password')]");
    private static final By loginBtn = By.xpath("//*[contains(@data-test-id, 'submit-button')]");

    @Override
    protected void checkPage() {
        logger.info("Провалидируем страницу ввода пароля для почты");
        $(passwordField).shouldBe(visible.because("Нет поле ввода пароля"));
        $(loginBtn).shouldBe(visible.because("Нет кнопки Входа"));
        logger.info("Успешно открыли страницу ввода пароля для почты");
    }

    public void inputPassword(String pwd) {
        $(passwordField).sendKeys(pwd);
    }

    public void clickLoginBtn() {
        $(loginBtn).click();
    }
}
