package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.ArrayList;
import java.util.List;

public class HomePage extends BasePage {
    private final Button cartButton;
    private final Label currency;
    private final Label weather;
    private final Actions actions;

    public HomePage(WebDriver chrome) {
        super(chrome);
        this.actions = new Actions(chrome);

        cartButton = new Button(chrome, By.cssSelector("a.auth-bar__item--cart"), "Иконка корзины");
        currency = new Label(chrome,
                By.cssSelector("ul[class*=\"helpers_hide_desktop\"] span[class*=\"currency-amount\"]"),
                "Валюта");
        weather = new Label(chrome,
                By.cssSelector("ul[class*=\"helpers_hide_desktop\"] span[class*=\"js-weather\"]"),
                "Погода");
    }

    @Override
    public boolean isPageLoaded() {
        try {
            WebElement topNavigation = chrome.findElement(By.cssSelector(".b-top-navigation"));
            return topNavigation.isDisplayed();
        } catch (NoSuchElementException | TimeoutException e) {
            return false;
        }
    }

    public CartPage openCart() {
        cartButton.click();
        CartPage cartPage = new CartPage(chrome);
        wait.until(chrome -> cartPage.isPageLoaded());
        return cartPage;
    }

    public boolean isCurrencyDisplayed() {
        return currency.isDisplayed();
    }

    public boolean isWeatherDisplayed() {
        return weather.isDisplayed();
    }

    public List<String> getCatalogMenuItems() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("ul.project-navigation__list.project-navigation__list_secondary")));

        List<WebElement> items = chrome.findElements(
                By.cssSelector("ul.project-navigation__list.project-navigation__list_secondary li a"));

        List<String> menuTexts = new ArrayList<>();
        for (WebElement element : items) {
            menuTexts.add(element.getText().trim());
        }

        return menuTexts;
    }

    public void hoverOnMenuItem(int menuIndex) {
        WebElement menuItem = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("li.b-main-navigation__item:nth-child(" + menuIndex + ") > span.b-main-navigation__text")
        ));
        actions.moveToElement(menuItem).perform();
    }

    public boolean isDropdownDisplayed(int menuIndex) {
        try {
            WebElement dropdown = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.cssSelector("li.b-main-navigation__item:nth-child(" + menuIndex + ") > div.b-main-navigation__dropdown")
            ));
            return dropdown.isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }
}