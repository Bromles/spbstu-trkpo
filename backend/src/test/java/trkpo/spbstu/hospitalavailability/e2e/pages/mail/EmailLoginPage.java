package trkpo.spbstu.hospitalavailability.e2e.pages.mail;

import org.openqa.selenium.By;
import trkpo.spbstu.hospitalavailability.e2e.pages.BasePage;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class EmailLoginPage extends BasePage {

    private static final By loginField =  By.name("username");
    private static final By nextStepBtn = By.xpath("//*[contains(@data-test-id, 'next-button')]");

    @Override
    protected void checkPage() {
        logger.info("Провалидируем страницу страницу ввода логина для входа в почту");
        $(loginField).shouldBe(visible.because("Нет поля ввода Логина"));
        $(nextStepBtn).shouldBe(visible.because("Нет кнопки следующего шага"));
        logger.info("Успешно открыли страницу ввода логина для входа в почту");
    }

    public EmailLoginPage inputLogin(String login) {
        $(loginField).sendKeys(login);
        return this;
    }

    public EmailPasswordPage clickEnterPasswordBtn() {
        $(nextStepBtn).click();
        return new EmailPasswordPage();
    }
}
