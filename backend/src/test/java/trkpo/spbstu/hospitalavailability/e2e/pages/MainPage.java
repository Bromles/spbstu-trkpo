package trkpo.spbstu.hospitalavailability.e2e.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

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

    public String getUserInfo() {
        return $(userInfo).getText();
    }
}
