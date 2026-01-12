package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.ArrayList;
import java.util.List;

public class HomePage extends BasePage {
    private final Button cartButton;
    public final Label currency;
    public final Label weather;
    private final Actions actions;
    private final By catalogMenuLocator;
    private final By catalogMenuContainerLocator;
    private final By menuItemsLocator;
    private final By menuItemTextLocator;
    private final By menuDropdownLocator;

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
        catalogMenuLocator = By.cssSelector("ul.project-navigation__list.project-navigation__list_secondary li a");
        catalogMenuContainerLocator = By.cssSelector("ul.project-navigation__list.project-navigation__list_secondary");
        menuItemsLocator = By.cssSelector("li.b-main-navigation__item");
        menuItemTextLocator = By.cssSelector("span.b-main-navigation__text");
        menuDropdownLocator = By.cssSelector("div.b-main-navigation__dropdown");
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


    public List<String> getCatalogMenuItems() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(catalogMenuContainerLocator));
        List<WebElement> items = chrome.findElements(catalogMenuLocator);
        List<String> menuTexts = new ArrayList<>();
        for (WebElement element : items) {
            menuTexts.add(element.getText().trim());
        }
        return menuTexts;
    }

    public void hoverOnMenuItem(int menuIndex) {
        List<WebElement> menuItems = chrome.findElements(menuItemsLocator);
        if (menuIndex < 1 || menuIndex > menuItems.size()) {
            throw new IllegalArgumentException("Invalid menu index: " + menuIndex);
        }
        WebElement menuItem = menuItems.get(menuIndex - 1);
        WebElement span = menuItem.findElement(menuItemTextLocator);
        actions.moveToElement(span).perform();
    }

    public boolean isDropdownDisplayed(int menuIndex) {
        try {
            List<WebElement> menuItems = chrome.findElements(menuItemsLocator);
            WebElement menuItem = menuItems.get(menuIndex - 1);
            WebElement dropdown = menuItem.findElement(menuDropdownLocator);
            return dropdown.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}