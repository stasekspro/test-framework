package tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertTrue;

import pages.CartPage;
import pages.HomePage;
import elements.MenuItem;


public class OnlinerTests extends BaseTest {
    @Test
    public void openCartPageWhenCartIconClicked() {
        HomePage homePage = new HomePage(chrome);
        CartPage cartPage = homePage.openCart();

        assertTrue(cartPage.isPageLoaded(), "Страница корзины не загрузилась");
        assertTrue(cartPage.isCartFormDisplayed(), "Форма корзины не отображается");
    }

    @Test
    public void displayCurrencyAndWeatherOnHomePage() {
        HomePage homePage = new HomePage(chrome);

        Assertions.assertAll(
                () -> Assertions.assertTrue(homePage.isCurrencyLoaded(), "Курсов нет"),
                () -> Assertions.assertTrue(homePage.isWeatherLoaded(), "Погоды нет!"));
    }

    @Test
    public void displayCatalogMenuButtonsonHomePage() {
        HomePage homePage = new HomePage(chrome);
        List<String> menuItems = homePage.getCatalogMenuItems();

        assertTrue(!menuItems.isEmpty(), "Список товаров в меню пустой!");
        List<String> expectedMenuItems = Arrays.asList(
                "Мобильные телефоны",
                "Ноутбуки",
                "Телевизоры",
                "Стиральные машины",
                "Мониторы"
        );

        for (String item : expectedMenuItems) {
            assertTrue(menuItems.contains(item),
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