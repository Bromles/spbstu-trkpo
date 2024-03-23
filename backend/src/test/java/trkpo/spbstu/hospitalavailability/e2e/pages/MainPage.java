package trkpo.spbstu.hospitalavailability.e2e.pages;

import com.codeborne.selenide.Selenide;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;

import static com.codeborne.selenide.Condition.enabled;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

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
    private static final By mapContainer = By.xpath("//*[@class = '_map_tjinb_1']");

    @Override
    protected void checkPage() {
        $(userInfo).shouldBe(visible.because("Нет информации в шапке профиля"),  Duration.ofSeconds(10));
        $(mapContainer).shouldBe(visible.because("Нет карты"), Duration.ofSeconds(30));
    }

    public String getUserInfo() {
        return $(userInfo).getText();
    }

    public void selectDistrict(int number) {
        $(districtSelector).shouldBe(enabled.because("Нельзя выбрать район"), Duration.ofSeconds(10));
        Select districtSelect = new Select($(districtSelector));
        districtSelect.selectByIndex(number);
    }

    public void selectHospital(int number) {
        $(hospitalSelector).shouldBe(enabled.because("Нельзя выбрать больницу"), Duration.ofSeconds(10));
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
        $(doctorSelector).shouldBe(enabled.because("Cелектор не кликабельны"), Duration.ofSeconds(10));
        Select doctorSelect = new Select($(doctorSelector));
        doctorSelect.selectByIndex(number);
    }

    public void clickStartTrackingBtn() {
        $(startTrackingBtn).shouldBe(enabled.because("Кнопка не кликаьельна")).click();
    }

    public boolean checkStartTrackingBtnIsEnabled() {
        return $(startTrackingBtn).isEnabled();
    }

    public boolean checkDistrictSelectorIsEnabled() {
        return $(districtSelector).isEnabled();
    }

    public boolean checkHospitalSelectorIsEnabled() {
       return $(hospitalSelector).isEnabled();
    }

    public boolean checkDirectionSelectorIsEnabled() {
        return $(directionSelector).isEnabled();
    }
    public boolean checkDoctorSelectorIsEnabled() {
        return $(doctorSelector).isEnabled();
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

    public String getSelectedDistrict() {
        List<WebElement> elemnts = new Select($(districtSelector)).getAllSelectedOptions();
        assertThat(elemnts).size().isEqualTo(1);
        return elemnts.get(0).getText();
    }

    public String getSelectedHospital() {
        List<WebElement> elemnts = new Select($(hospitalSelector)).getAllSelectedOptions();
        assertThat(elemnts).size().isEqualTo(1);
        return elemnts.get(0).getText();
    }

    public String getSelectedDoctor() {
        List<WebElement> elemnts = new Select($(doctorSelector)).getAllSelectedOptions();
        assertThat(elemnts).size().isEqualTo(1);
        return elemnts.get(0).getText();
    }

    public String getSelectedDirection() {
        List<WebElement> elemnts = new Select($(directionSelector)).getAllSelectedOptions();
        assertThat(elemnts).size().isEqualTo(1);
        return elemnts.get(0).getText();
    }

    public MapWrapper getMapWrapper() {
        logger.info("");
        return new MapWrapper($(mapContainer));
    }
}
