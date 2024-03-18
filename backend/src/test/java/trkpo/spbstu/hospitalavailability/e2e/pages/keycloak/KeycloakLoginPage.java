package trkpo.spbstu.hospitalavailability.e2e.pages.keycloak;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class KeycloakLoginPage {
    public WebDriver driver;
    @FindBy(xpath = "//*[contains(@tabindex, '4')]")
    private WebElement loginBtn;

    @FindBy(xpath = "//*[contains(@tabindex, '5')]")
    private WebElement forgotPasswordRef;
    @FindBy(xpath = "//*[contains(@tabindex, '6')]")
    private WebElement registerRef;

    @FindBy(xpath = "//*[contains(@id, 'username')]")
    private WebElement loginField;
    @FindBy(xpath = "//*[contains(@id, 'password')]")
    private WebElement passwordField;

    public KeycloakLoginPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    public void clickLoginBtn() {
        loginBtn.click();
    }

    public void clickForgotPasswordRef() {
        forgotPasswordRef.click();
    }

    public void clickRegisterRef() {
        registerRef.click();
    }

    public void inputLogin(String login) {
        loginField.sendKeys(login);
    }

    public void inputPasswd(String passwd) {
        passwordField.sendKeys(passwd);
    }
}
