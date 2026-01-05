package tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
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
        HomePage homePage = new HomePage(chrome);

        Assertions.assertAll(
                () -> Assertions.assertTrue(homePage.isCurrencyDisplayed(), "Курсов нет"),
                () -> Assertions.assertTrue(homePage.isWeatherDisplayed(), "Погоды нет!"));
    }


    @Test
    public void displayCatalogMenuButtonsonHomePage() {
        HomePage homePage = new HomePage(chrome);
        List<String> actual = homePage.getCatalogMenuItems();
        List<String> expected = Arrays.asList(
                "Время исполнения желаний",
                "Мобильные телефоны",
                "Видеокарты",
                "Телевизоры",
                "Ноутбуки",
                "Планшеты",
                "Мониторы",
                "Стиральные машины",
                "Компьютеры"
        );

        Assertions.assertEquals(expected, actual, "Элементы меню отличаются от ожидаемых!");
    }

    @ParameterizedTest(name = "Проверка дропдауна: {1}")
    @CsvSource({
            "2, Новости",
            "3, Автобарахолка",
            "4, Дома и квартиры"
    })
    public void checkDropdownOpensInHomePage(int menuIndex, String dropdownName) {
        HomePage homePage = new HomePage(chrome);

        homePage.hoverOnMenuItem(menuIndex);
        assertTrue(homePage.isDropdownDisplayed(menuIndex),
                "Дропдаун '" + dropdownName + "' не открылся");
    }
}