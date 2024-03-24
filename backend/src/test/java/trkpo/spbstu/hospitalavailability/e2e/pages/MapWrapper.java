package trkpo.spbstu.hospitalavailability.e2e.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.codeborne.selenide.Selenide.$;

public class MapWrapper {

    protected static final Logger logger = LoggerFactory.getLogger(BasePage.class);
    private final WebElement item;
    private static final By places = By.xpath("//*[@class = 'ymaps-2-1-79-places-pane']");
    private static final By place = By.xpath(".//*[contains(@class, 'placemark-overlay')]");

    public MapWrapper(WebElement item) {
        this.item = item;
    }

    public int getMarkerCount() {
        $(item).scrollIntoView(true);
        return $(places).$$(place).size();
    }
}
