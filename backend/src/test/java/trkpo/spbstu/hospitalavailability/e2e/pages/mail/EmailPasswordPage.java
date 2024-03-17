package trkpo.spbstu.hospitalavailability.e2e.pages.mail;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class EmailPasswordPage {
    public WebDriver driver;
    @FindBy(xpath = "//*[contains(@name, 'password')]")
    private WebElement passwordField;
    @FindBy(xpath = "//*[contains(@data-test-id, 'submit-button')]")
    private WebElement loginBtn;

    public EmailPasswordPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    public void inputPassword(String pwd) {
        passwordField.sendKeys(pwd);
    }

    public void clickLoginBtn() {
        loginBtn.click();
    }
}
