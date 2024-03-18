package trkpo.spbstu.hospitalavailability.e2e.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class MainPage {

    public WebDriver driver;
    @FindBy(xpath = "//*[@id='root']//*[@class='_name_13u23_37']")
    private WebElement userInfo;

    public MainPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    public String getUserInfo() {
        return userInfo.getText();
    }
}
