package trkpo.spbstu.hospitalavailability.e2e.pages.mail;

import org.openqa.selenium.By;
import trkpo.spbstu.hospitalavailability.e2e.pages.BasePage;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class EmailMainPage extends BasePage {
    private static final By lastMessage = By.xpath("//*[@id='app-canvas']//*[contains(text(), 'Сервер авторизации записи к докторам')]");
    private static final By link = By.xpath("//div[@class='letter__body']//a[1]");
    @Override
    protected void checkPage() {
        logger.info("Провалидируем главную страницу почты");
        $(lastMessage).shouldBe(visible.because("Нет сообщения"));
        logger.info("Успешно открыли главную страницу почты");
    }

    public String openLastMessageAndGetLing() {
        $(lastMessage).click();
        return $(link).shouldBe(visible.because("Нет ссылки")).getAttribute("href");
    }
}
