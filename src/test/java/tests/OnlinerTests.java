package tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertTrue;
import pages.HomePage;



public class OnlinerTests extends BaseTest {
    @Test
    public void openCartPageWhenCartIconClicked() {
        pages.HomePage homePage = new HomePage(chrome);
        pages.CartPage cartPage = homePage.openCart();

        assertTrue(cartPage.isPageLoaded(), "Страница корзины не загрузилась");
        assertTrue(cartPage.isCartFormDisplayed(), "Форма корзины не отображается");
    }

    @Test
    public void displayCurrencyAndWeatherOnHomePage() {

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector(".b-top-navigation")));
        WebElement courses = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("ul[class*=\"helpers_hide_desktop\"] span[class*=\"currency-amount\"]")));
        WebElement weather = chrome.findElement(
                By.cssSelector("ul[class*=\"helpers_hide_desktop\"] span[class*=\"js-weather\"]"));
        Assertions.assertAll(
                () -> Assertions.assertTrue(courses.isDisplayed(), "Курсов нет"),
                () -> Assertions.assertTrue(weather.isDisplayed(), "Погоды нет!"));
    }


    @Test
    public void displayCatalogMenuButtonsonHomePage() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("ul.project-navigation__list.project-navigation__list_secondary")));

        List<WebElement> items = chrome.findElements(By.cssSelector("ul.project-navigation__list.project-navigation__list_secondary li a"));
        List<String> actual = new ArrayList<>();
        for (WebElement element : items) {
            actual.add(element.getText().trim());
        }

        List<String> expected = Arrays.asList(
                "Apple iPhone",
                "Мобильные телефоны",
                "Автомобильные шины",
                "Телевизоры",
                "Стиральные машины",
                "Холодильники",
                "Ноутбуки",
                "Мониторы",
                "Видеокарты",
                "Планшеты"
        );

        Assertions.assertEquals(expected, actual, "Элементы меню отличаются от ожидаемых!");
    }

    @ParameterizedTest(name = "Проверка дропдауна: {1}")
    @CsvSource({
            "1, Новости",
            "3, Автобарахолка",
            "5, Дома и квартиры"
    })

    public void checkDropdownOpensInHomePage(int menuIndex, String dropdownName) {
        WebElement menuItem = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("li.b-main-navigation__item:nth-child(" + menuIndex + ") > span.b-main-navigation__text")
        ));

        actions.moveToElement(menuItem).perform();

        WebElement dropdown = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("li.b-main-navigation__item:nth-child(" + menuIndex + ") > div.b-main-navigation__dropdown")
        ));

        assertTrue(dropdown.isDisplayed(), "Дропдаун '" + dropdownName + "' не открылся");
    }
}