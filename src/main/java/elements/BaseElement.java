package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public abstract class BaseElement {

    protected WebDriver chrome;
    protected WebDriverWait wait;
    protected By locator;
    protected String name;

    public BaseElement(WebDriver chrome, By locator, String name) {
        this.chrome = chrome;
        this.wait = new WebDriverWait(chrome, Duration.ofSeconds(10));
        this.locator = locator;
        this.name = name;
    }

    protected WebElement getElement() {
        return chrome.findElement(locator);
    }

    public void waitUntilVisible() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public boolean isDisplayed() {
        try {
            return getElement().isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public String getText() {
        waitUntilVisible();
        return getElement().getText();
    }
}