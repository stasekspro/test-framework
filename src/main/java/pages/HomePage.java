package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.ArrayList;
import java.util.List;

public class HomePage extends BasePage {
    private final Button cartButton;
    public final Label currency;
    public final Label weather;
    private final By catalogMenuLocator;
    private final By catalogMenuContainerLocator;
    private final By menuItemsLocator;


    public HomePage(WebDriver chrome) {
        super(chrome);

        cartButton = new Button(chrome, By.cssSelector("a.auth-bar__item--cart"), "Иконка корзины");
        currency = new Label(chrome,
                By.cssSelector("li.top-informer-currency a.b-top-navigation-informers__link"),
                "Валюта");
        weather = new Label(chrome,
                By.cssSelector("li.top-informer-weather.js-weather-widget a.b-top-navigation-informers__link"),
                "Погода");
        catalogMenuLocator = By.cssSelector("ul.project-navigation__list.project-navigation__list_secondary li a");
        catalogMenuContainerLocator = By.cssSelector("ul.project-navigation__list.project-navigation__list_secondary");
        menuItemsLocator = By.cssSelector("li.b-main-navigation__item");
    }

    @Override
    public boolean isPageLoaded() {
        try {
            WebElement topNavigation = chrome.findElement(By.cssSelector(".b-top-navigation"));
            WebElement currencyWidget = chrome.findElement(By.cssSelector("li.top-informer-currency a.b-top-navigation-informers__link"));
            WebElement weatherWidget = chrome.findElement(By.cssSelector("li.top-informer-weather a.b-top-navigation-informers__link"));
            return topNavigation.isDisplayed()
                    && currencyWidget.isDisplayed()
                    && weatherWidget.isDisplayed();
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


    public List<String> getCatalogMenuItems() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(catalogMenuContainerLocator));
        List<WebElement> items = chrome.findElements(catalogMenuLocator);
        List<String> menuTexts = new ArrayList<>();
        for (WebElement element : items) {
            menuTexts.add(element.getText().trim());
        }
        return menuTexts;
    }

    public MenuItem getMenuItem(int index) {
        List<WebElement> menuItems = chrome.findElements(menuItemsLocator);
        if (index < 1 || index > menuItems.size()) {
            throw new IllegalArgumentException("Invalid menu index: " + index);
        }
        WebElement menuElement = menuItems.get(index - 1);
        return new MenuItem(chrome, menuElement);

    }
}