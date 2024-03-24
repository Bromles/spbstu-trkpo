package trkpo.spbstu.hospitalavailability.e2e.pages.mail;

import org.openqa.selenium.By;
import trkpo.spbstu.hospitalavailability.e2e.pages.BasePage;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class EmailMainPage extends BasePage {
    private static final By lastEmailMessage = By.xpath("//*[@id='app-canvas']//*[contains(text(), 'Verify email') or contains(text(), 'Подтверждение email')]");
    private static final By lastPasswordMessage = By.xpath("//*[@id='app-canvas']//*[contains(text(), 'Reset password') or contains(text(), 'Сброс пароля')]");
    private static final By link = By.xpath("//div[@class='letter__body']//a[1]");

    private final Purpose purpose;

    public EmailMainPage(Purpose purpose) {
        this.purpose = purpose;
    }

    @Override
    protected void checkPage() {
        logger.info("Провалидируем главную страницу почты");

        if (this.purpose == Purpose.VERIFY_EMAIL) {
            $(lastEmailMessage).shouldBe(visible.because("Нет сообщения"));
        } else {
            $(lastPasswordMessage).shouldBe(visible.because("Нет сообщения"));
        }

        logger.info("Успешно открыли главную страницу почты");
    }

    public String openLastMessageAndGetLink() {
        if (this.purpose == Purpose.VERIFY_EMAIL) {
            $(lastEmailMessage).click();
        } else {
            $(lastPasswordMessage).click();
        }

        return $(link).shouldBe(visible.because("Нет ссылки")).getAttribute("href");
    }

    public enum Purpose {
        VERIFY_EMAIL,
        RESET_PASSWORD
    }
}
