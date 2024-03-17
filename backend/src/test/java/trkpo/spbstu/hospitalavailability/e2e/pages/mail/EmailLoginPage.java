package trkpo.spbstu.hospitalavailability.e2e.pages.mail;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class EmailLoginPage {
    public WebDriver driver;
    @FindBy(xpath = "//*[@name='username']")
    private WebElement loginField;
    @FindBy(xpath = "//*[contains(@data-test-id, 'next-button')]")
    private WebElement nextStepBtn;

    public EmailLoginPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    public void inputLogin(String login) {
        loginField.sendKeys(login);
    }

    public void clickEnterPasswordBtn() {
        nextStepBtn.click();
    }
}
