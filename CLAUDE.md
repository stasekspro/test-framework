# Onliner UI Test Framework

## Project Overview
Selenium + JUnit 5 UI test framework for onliner.by. Written in Java 17 using Maven.

## Architecture

### Packages
- `elements/` — reusable UI elements: `BaseElement`, `Button`, `Label`, `MenuItem`
- `pages/` — Page Object classes: `BasePage`, `HomePage`, `CartPage`
- `tests/` — test classes: `BaseTest`, `OnlinerTests`

### Key Classes
- `BaseElement` — wraps WebElement, provides `isDisplayed()`, `getElement()`, `waitUntilVisible()`
- `BasePage` — abstract base for all pages, has `WebDriver chrome`, `WebDriverWait wait`, abstract `isPageLoaded()`
- `BaseTest` — JUnit 5 base: `@BeforeEach` opens Chrome + navigates to onliner.by, `@AfterEach` quits
- `HomePage` — main page with `currency`, `weather` labels, `openCart()`, `getCatalogMenuItems()`, `getMenuItem(int)`
- `CartPage` — cart page
- `MenuItem` — navigation menu item with `openDropdown()` and `isDropdownDisplayed()`

## Test Conventions
- One `@Test` per behavior
- Use `wait.until(driver -> page.isPageLoaded())` before interacting with any page
- Use `Assertions.assertAll()` for multiple assertions in one test
- Use `@ParameterizedTest` + `@CsvSource` for data-driven tests
- Test method names: camelCase describing the behavior (e.g. `displayCurrencyAndWeatherOnHomePage`)
- All elements created in Page Object constructors
- No hardcoded waits (`Thread.sleep`)
- No hardcoded paths (no `C:\\...`)

## Build & Test
```bash
mvn test                    # run all tests
mvn test -Dtest=TestName    # run specific test
```

## CI
GitHub Actions runs tests on push/PR to main using `xvfb-run --auto-servernum mvn -B test` on ubuntu-latest.

## Git Workflow
- Never push directly to `main`
- Create feature branch → PR → wait for green CI → request review
- Branch naming: `task{N}`
