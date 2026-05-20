package elements;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class MenuItem {
    private final WebElement element;
    private final Actions actions;
    private final By dropdownLocator;
    private final By textLocator;
    private final WebDriverWait wait;

    public MenuItem(WebDriver chrome, WebElement element) {
        this.element = element;
        this.actions = new Actions(chrome);
        this.wait = new WebDriverWait(chrome, Duration.ofSeconds(5));
        this.dropdownLocator = By.cssSelector("div.b-main-navigation__dropdown");
        this.textLocator = By.cssSelector("span.b-main-navigation__text");
    }

    public void openDropdown() {
        WebElement span = element.findElement(textLocator);
        actions.moveToElement(span).perform();
    }

    public boolean isDropdownDisplayed() {
        try {
            WebElement dropdown = element.findElement(dropdownLocator);
            wait.until(ExpectedConditions.visibilityOf(dropdown));
            return dropdown.isDisplayed();
        } catch (NoSuchElementException | TimeoutException e) {
            return false;
        }
    }
}