# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Build & Test Commands
```bash
mvn clean compile
mvn test
mvn test -Dtest=OnlinerTests#methodName
```

## Architecture: Page Object Model

The project uses POM (Page Object Model) strictly — UI structure lives in `src/main/java/pages/`, tests live in `src/test/java/tests/`.

### Inheritance chain

```
BaseElement  ←  Button
BasePage     ←  HomePage, CartPage
BaseTest     ←  OnlinerTests
```

**`BaseElement`** — wraps a `By` locator and `WebDriver`; provides `waitUntilVisible()`, `isDisplayed()`, `getText()` using a 10-second explicit wait. Extended by `Button`, which adds `click()` (waits for clickability first).

**`BasePage`** — holds `WebDriver chrome` and a `WebDriverWait` (10 s). Requires subclasses to implement `isPageLoaded()`. `HomePage` checks for `.b-top-navigation`; `CartPage` checks for `.cart-form`.

**`BaseTest`** — JUnit `@BeforeEach`/`@AfterEach` manage the `ChromeDriver` lifecycle. Exposes `chrome`, `actions`, and `wait` to subclasses. `OnlinerTests` extends this and contains all actual test methods.

### Navigation flow

Tests call page methods that return the next page object (e.g., `homePage.openCart()` returns a `CartPage`), then assert on that page's state via its public methods.
