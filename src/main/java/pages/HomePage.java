package pages;

import org.openqa.selenium.*;

public class HomePage extends BasePage {
    private final Button cartButton;

    public HomePage(WebDriver chrome) {
        super(chrome);
        cartButton = new Button(chrome, By.cssSelector("a.auth-bar__item--cart"), "Иконка корзины");
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
}