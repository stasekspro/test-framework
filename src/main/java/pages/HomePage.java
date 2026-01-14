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
                By.cssSelector("ul[class*=\"helpers_hide_desktop\"] span[class*=\"currency-amount\"]"),
                "Валюта");
        weather = new Label(chrome,
                By.cssSelector("ul[class*=\"helpers_hide_desktop\"] span[class*=\"js-weather\"]"),
                "Погода");
        catalogMenuLocator = By.cssSelector("ul.project-navigation__list.project-navigation__list_secondary li a");
        catalogMenuContainerLocator = By.cssSelector("ul.project-navigation__list.project-navigation__list_secondary");
        menuItemsLocator = By.cssSelector("li.b-main-navigation__item");
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

    public MenuItem getMenuItem(int index) {
        List<WebElement> menuItems = chrome.findElements(menuItemsLocator);
        if (index < 1 || index > menuItems.size()) {
            throw new IllegalArgumentException("Invalid menu index: " + index);
        }
        WebElement menuElement = menuItems.get(index - 1);
        return new MenuItem(chrome, menuElement);

    }
}