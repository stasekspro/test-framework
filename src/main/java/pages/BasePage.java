package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public abstract class BasePage {

    protected WebDriver chrome;
    protected WebDriverWait wait;

    public BasePage(WebDriver chrome) {
        this.chrome = chrome;
        this.wait = new WebDriverWait(chrome, Duration.ofSeconds(10));
    }


    public abstract boolean isPageLoaded();
}