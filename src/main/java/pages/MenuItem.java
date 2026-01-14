package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public class MenuItem {
    private final WebElement element;
    private final Actions actions;
    private final By dropdownLocator;
    private final By textLocator;

    public MenuItem(WebDriver chrome, WebElement element) {
        this.element = element;
        this.actions = new Actions(chrome);
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
            return dropdown.isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }
}