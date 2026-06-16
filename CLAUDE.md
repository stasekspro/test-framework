# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

Selenium WebDriver UI test framework for onliner.by, built with Java 17, Maven, JUnit 5 (Jupiter), and Selenium 4. Tests run against a live Chrome browser.

## Build & Test Commands

```bash
# Compile
mvn clean compile

# Run all tests
mvn test

# Run a single test class
mvn test -Dtest=OnlinerTests

# Run a single test method
mvn test -Dtest=OnlinerTests#openCartPageWhenCartIconClicked
```

No linting or formatting plugins are configured.

## Local Setup Requirement

`BaseTest.java` has hardcoded Chrome paths for Windows:
- `C:\chromedriver-win64\chromedriver.exe` — ChromeDriver binary
- `C:\Program Files\Google\Chrome\Application\chrome.exe` — Chrome binary

These must exist locally for tests to run. WebDriverManager is included as a dependency but not yet wired up to replace these hardcoded paths.

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
