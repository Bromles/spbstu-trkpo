package trkpo.spbstu.hospitalavailability.e2e.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class KeycloakRegistrationPage {
    //TODO
    public WebDriver driver;

    public KeycloakRegistrationPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }
}
