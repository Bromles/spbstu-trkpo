package trkpo.spbstu.hospitalavailability.e2e.pages.keycloak;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class KeycloakRegistrationPage {
    public WebDriver driver;
    @FindBy(xpath = "//*[contains(@id, 'firstName')]")
    private WebElement firstNameField;
    @FindBy(xpath = "//*[contains(@id, 'lastName')]")
    private WebElement lastNameField;
    @FindBy(xpath = "//*[contains(@id, 'email')]")
    private WebElement emailField;
    @FindBy(xpath = "//*[contains(@id, 'password')]")
    private WebElement passwordField;
    @FindBy(xpath = "//*[contains(@id, 'password-confirm')]")
    private WebElement passwordConfirmField;
    @FindBy(xpath = "//*[contains(@type, 'submit')]")
    private WebElement submit;


    public KeycloakRegistrationPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    public void inputFirstName(String firstName) {
        firstNameField.sendKeys(firstName);
    }

    public void inputLastName(String lastName) {
        lastNameField.sendKeys(lastName);
    }

    public void inputEmail(String email) {
        emailField.sendKeys(email);
    }

    public void inputPasswd(String passwd) {
        passwordField.sendKeys(passwd);
    }

    public void inputPasswdConfirm(String passwd) {
        passwordConfirmField.sendKeys(passwd);
    }

    public void clickRegisterBtn() {
        submit.click();
    }
}
