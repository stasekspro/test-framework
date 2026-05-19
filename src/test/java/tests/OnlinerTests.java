package tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertTrue;
import pages.HomePage;
import pages.MenuItem;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;


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
        HomePage homePage = new HomePage(chrome);
        wait.until(driver -> homePage.isPageLoaded());

        Assertions.assertAll(
                () -> Assertions.assertTrue(homePage.currency.isDisplayed(), "Курсов нет"),
                () -> Assertions.assertTrue(homePage.weather.isDisplayed(), "Погоды нет!"));
    }

    @Test
    public void displayCatalogMenuButtonsonHomePage() {
        HomePage homePage = new HomePage(chrome);
        List<String> actual = homePage.getCatalogMenuItems();

        actual.removeIf(String::isEmpty);
        assertTrue(!actual.isEmpty(), "Список товаров в меню пустой!");
        List<String> alwaysPresent = Arrays.asList(
                "Мобильные телефоны",
                "Ноутбуки",
                "Телевизоры",
                "Стиральные машины",
                "Мониторы"
        );

        for (String item : alwaysPresent) {
            assertTrue(actual.contains(item),
                    "Ожидаемый элемент не найден в меню: " + item);
        }
    }

    @ParameterizedTest(name = "Проверка дропдауна: {1}")
    @CsvSource({
            "2, Новости",
            "3, Автобарахолка",
            "4, Дома и квартиры"
    })
    public void checkDropdownOpensInHomePage(int menuIndex, String dropdownName) {
        HomePage homePage = new HomePage(chrome);
        MenuItem menuItem = homePage.getMenuItem(menuIndex);

        menuItem.openDropdown();
        assertTrue(menuItem.isDropdownDisplayed(),
                "Дропдаун '" + dropdownName + "' не открылся");
    }
}