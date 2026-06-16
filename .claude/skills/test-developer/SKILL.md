---
name: test-developer
description: Write a green, stable Selenium + JUnit 5 UI test for onliner.by from a task description, then have it reviewed.
---

# test-developer

You write ONE thing: a UI test that is GREEN and STABLE. Done means: `mvn test` passes locally AND the test would pass on CI (headless Linux), AND code-reviewer approved it.

## Project map
- Site: https://www.onliner.by/
- Stack: Java 17, Maven, JUnit 5.10.2, Selenium 4.25.0
- Tests: `src/test/java/tests/OnlinerTests.java` (extends `BaseTest` → gives you `chrome`, `wait` 10s, `actions`)
- Pages: `src/main/java/pages/` — `BasePage` (abstract, `isPageLoaded()`), `HomePage`, `CartPage`
- Elements: `src/main/java/elements/` — `BaseElement` (has `waitUntilVisible()`, `isDisplayed()`, `getText()`), `Button`, `Label`, `MenuItem`

## The pattern (follow exactly)

Test — thin, no locators, no waits:
```java
@Test
public void openCartPageWhenCartIconClicked() {
    HomePage homePage = new HomePage(chrome);
    CartPage cartPage = homePage.openCart();
    assertTrue(cartPage.isPageLoaded(), "Страница корзины не загрузилась");
}
```

Page Object — owns locators (in constructor) and waits:
```java
currency = new Label(chrome, By.cssSelector("li.top-informer-currency a..."), "Валюта");
```

Async element — wrap the wait, return boolean (this is how we handle elements that load late via JS):
```java
public boolean isWeatherLoaded() {
    try { weather.waitUntilVisible(); return true; }
    catch (TimeoutException e) { return false; }
}
```

## Process

1. **Read** CLAUDE.md, OnlinerTests.java, HomePage.java, BaseTest.java.

2. **Find a STABLE selector** via Playwright MCP on https://www.onliner.by/.
   - Validate in console: `document.querySelector("SEL")` returns non-null.
   - Prefer semantic classes (`.b-top-navigation`, `js-weather-widget`) over nth-child indexes — index-based selectors break when the site changes.
   - Confirm the element is actually visible: `getComputedStyle(el).display !== "none"`.

3. **Page Object first.** Add locator to constructor + a method. Elements → `elements/`, pages → `pages/`. `isPageLoaded()` checks ONLY a structural anchor (`.b-top-navigation`), never business data.

4. **Handle async explicitly.** If the element loads after page load (prices, weather, anything JS-driven), do NOT assert directly — go through a `waitUntilVisible()`-backed method. A test that sometimes passes is a FAILED test.

5. **Write the test** — descriptive name, `assertAll()` for multiple checks, `@ParameterizedTest` + `@CsvSource` for data-driven. Forbidden: `Thread.sleep()`, hardcoded paths, `By` in test class.

6. **Run locally:** `mvn test`. Read the stacktrace, fix the ROOT cause:
   - `TimeoutException` → element loads late → use waitUntilVisible() in the Page Object
   - `NoSuchElementException` → wrong selector → re-inspect in browser
   - Repeat until BUILD SUCCESS.

7. **Verify CI-readiness.** CI runs headless on Linux (`xvfb-run mvn -B test`). Re-check: no hardcoded Windows paths, no reliance on a maximized window or local Chrome profile, no dependence on test execution order. If the test relies on anything machine-specific, fix it.

8. **Invoke code-reviewer** (Task tool, as subagent): paste your diff, ask for review. Apply every CRITICAL item.

9. **Re-run** `mvn test` → must still be green. Then commit (never commit `.idea/`, `screenshot*.png`, `target/`).

## Hard rule
Never report success on a test you ran only once and it happened to pass. If it touches an async element, run it 3 times — all 3 must be green.
