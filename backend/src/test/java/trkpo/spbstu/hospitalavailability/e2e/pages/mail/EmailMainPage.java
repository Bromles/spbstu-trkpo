package trkpo.spbstu.hospitalavailability.e2e.pages.mail;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class EmailMainPage {
    public WebDriver driver;
    @FindBy(xpath = "//*[@id='app-canvas']//*[contains(text(), 'Сервер авторизации записи к докторам')]")
    private WebElement lastMessage;

    public EmailMainPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    public void openLastMessage() {
        lastMessage.click();
    }
}
