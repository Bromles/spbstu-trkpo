package trkpo.spbstu.hospitalavailability.e2e.pages.mail;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import trkpo.spbstu.hospitalavailability.e2e.pages.BasePage;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class EmailUnauthPage extends BasePage {

    private static final By loginBtn = By.xpath("//*[contains(@class, 'ph-login svelte-1au561b')]");

    @Override
    protected void checkPage() {
        logger.info("Провалидируем страницу ..");
        $(loginBtn).shouldBe(visible.because("Нет кнопки Входа"));
        logger.info("Успешно открыли страницу ..ы");
    }

    public void clickLoginBtn() {
        $(loginBtn).click();
    }
}
