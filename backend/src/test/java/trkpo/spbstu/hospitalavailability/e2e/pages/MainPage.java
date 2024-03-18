package trkpo.spbstu.hospitalavailability.e2e.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class MainPage extends BasePage {

    public WebDriver driver;
    private static final By userInfo = By.xpath("//*[@id='root']//*[@class='_name_13u23_37']");

    @Override
    protected void checkPage() {
        $(userInfo).shouldBe(visible.because("Нет информации в шапке профиля"), Duration.of(30, ChronoUnit.SECONDS));
    }

    @FindBy(xpath = "//button[normalize-space()='Начать отслеживание']")
    private WebElement startTrackingBtn;
    @FindBy(xpath = "//button[normalize-space()='Выход']")
    private WebElement exitBtn;
    @FindBy(xpath = "//button[normalize-space()='Закончить отслеживание']")
    private WebElement stopTrackingBtn;
    @FindBy(xpath = "//*[@id='root']//*[@class='_trackingItem_18b88_1']")
    private WebElement tracking;

    public MainPage() {
        PageFactory.initElements(driver, this);
    }

    public String getUserInfo() {
        return $(userInfo).getText();
    }

    public void selectDistrict(int number) {
        Select districtSelect = new Select(driver.findElement(By.id("districtSelect")));
        districtSelect.selectByIndex(number);
    }

    public void selectHospital(int number) {
        Select hospitalSelect = new Select(driver.findElement(By.id("hospitalSelect")));
        hospitalSelect.selectByIndex(number);
    }

    public void selectDirection() {
        Select directionSelect = new Select(driver.findElement(By.id("directionSelect")));
        directionSelect.selectByVisibleText("Хирургия");
    }

    public void selectDoctor() {
        Select doctorSelect = new Select(driver.findElement(By.id("doctorSelect")));
        doctorSelect.selectByVisibleText("Палютин Максим Александрович");
    }

    public void clickStartTrackingBtn() {
        startTrackingBtn.click();
    }

    public String getTracking() {
        return tracking.getText();
    }

    public void clickExitBtn() {
        exitBtn.click();
    }

    public void clickStopTrackingBtn() {
        stopTrackingBtn.click();
    }
}
