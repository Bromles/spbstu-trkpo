package trkpo.spbstu.hospitalavailability.e2e.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class UnauthPage {

    public WebDriver driver;
    @FindBy(xpath = "//*[contains(text(), 'Вход | Регистрация')]")
    private WebElement loginBtn;

    public UnauthPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    public void clickLoginBtn() {
        loginBtn.click();
    }
}
