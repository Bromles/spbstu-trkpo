package trkpo.spbstu.hospitalavailability.e2e.pages;

import com.codeborne.selenide.Selenide;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class MainPage extends BasePage {

    private static final By userInfo = By.xpath("//*[@id='root']//*[@class='_name_13u23_37']");
    private static final By startTrackingBtn = By.xpath("//button[normalize-space()='Начать отслеживание']");
    private static final By exitBtn = By.xpath("//button[normalize-space()='Выход']");
    private static final By stopTrackingBtn = By.xpath("//button[normalize-space()='Закончить отслеживание']");
    private static final By trackingItem = By.xpath(".//*[contains(@class, 'trackingItem')]");
    private static final By trackingContainer = By.xpath("//*[contains(@class, 'tracking_container_content')]");
    private static final By districtSelector = By.id("districtSelect");
    private static final By hospitalSelector = By.id("hospitalSelect");
    private static final By directionSelector = By.id("directionSelect");
    private static final By doctorSelector = By.id("doctorSelect");

    @Override
    protected void checkPage() {
        $(userInfo).shouldBe(visible.because("Нет информации в шапке профиля"), Duration.of(30, ChronoUnit.SECONDS));
    }

    public String getUserInfo() {
        return $(userInfo).getText();
    }

    public void selectDistrict(int number) {
        $(districtSelector).shouldBe(visible.because("Нет селектора районов"), Duration.ofSeconds(10));
        Select districtSelect = new Select($(districtSelector));
        districtSelect.selectByIndex(number);
    }

    public void selectHospital(int number) {
        $(hospitalSelector).shouldBe(visible.because("Нет селектора больниц"), Duration.ofSeconds(10));
        Select hospitalSelect = new Select($(hospitalSelector));
        hospitalSelect.selectByIndex(number);

    }

    public void selectDirection(int number) {
        WebDriverWait wait = new WebDriverWait(Selenide.webdriver().driver().getWebDriver(), Duration.ofSeconds(30));
        wait.until((ExpectedCondition<Boolean>) driver -> {
            Select select = new Select($(directionSelector));
            return select.getOptions().size()>1;
        });
        Select directionSelect = new Select($(directionSelector));
        directionSelect.selectByIndex(number);
    }

    public void selectDoctor(int number) {
        $(doctorSelector).shouldBe(visible.because("Нет селектора докторов"), Duration.ofSeconds(10));
        Select doctorSelect = new Select($(doctorSelector));
        doctorSelect.selectByIndex(number);
    }

    public void clickStartTrackingBtn() {
        $(startTrackingBtn).click();
    }

    public int getTrackingCount() {
        return $(trackingContainer).$$(trackingItem).size();
    }

    public void clickStopTrackingBtn() {
        $(stopTrackingBtn).click();
    }

    public void clickExit() {
        $(exitBtn).click();
    }
}
