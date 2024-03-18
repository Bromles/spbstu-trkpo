package trkpo.spbstu.hospitalavailability.e2e.pages;

import com.codeborne.selenide.Condition;
import org.openqa.selenium.By;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class MainPage extends BasePage {

    private static final By userInfo = By.xpath("//*[@id='root']//*[@class='_name_13u23_37']");
    private static final By districtSelect = By.id("districtSelect");

    @Override
    protected void checkPage() {
        $(userInfo).shouldBe(visible.because("Нет информации в шапке профиля"), Duration.of(30, ChronoUnit.SECONDS));
    }

    public String getUserInfo() {
        return $(userInfo).getText();
    }

    public MainPage selectDistrict(String district) {
        $(districtSelect).shouldBe(visible.because("Нет селектора районов")).click();
        $(districtSelect).$(By.xpath("//*[contains(text(), district)]"))
                .shouldBe(visible.because("Нет такого района")).click();
        return this;
    }
}
