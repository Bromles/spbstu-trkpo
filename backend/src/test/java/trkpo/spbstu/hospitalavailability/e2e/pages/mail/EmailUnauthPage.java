package trkpo.spbstu.hospitalavailability.e2e.pages.mail;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class EmailUnauthPage {

    public WebDriver driver;
    @FindBy(xpath = "//*[contains(@class, 'ph-login svelte-1au561b')]")
    private WebElement loginBtn;


    public EmailUnauthPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    public void clickLoginBtn() {
        loginBtn.click();
    }
}
