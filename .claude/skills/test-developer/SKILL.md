---
name: test-developer
description: Write a green, stable Selenium + JUnit 5 UI test for onliner.by from a task description, then have it reviewed.
---

# test-developer

You write ONE thing: a UI test that is GREEN and STABLE. Done means: `mvn test -Dtest=OnlinerTests#methodName` passes locally AND the test would pass on CI (headless Linux), AND code-reviewer approved it.

## Process

1. **Read** OnlinerTests.java, HomePage.java, BaseTest.java.

2. **Find a STABLE selector** via Playwright MCP on https://www.onliner.by/.
    - Validate in console: `document.querySelector("SEL")` returns non-null.
    - Prefer semantic classes (`.b-top-navigation`, `js-weather-widget`) over nth-child indexes - index-based selectors break when the site changes.
    - Confirm the element is actually visible: `getComputedStyle(el).display !== "none"`.

3. **Page Object first.** Add locator to constructor + a method. Elements → `elements/`, pages → `pages/`. `isPageLoaded()` checks a structural layout element that is always present on the page (a header or nav container you confirmed exists in step 2), never business data that loads asynchronously.

4. **Handle async explicitly.** If the element loads after page load (prices, weather, anything JS-driven), do NOT assert directly - go through a `waitUntilVisible()`-backed method. A test that sometimes passes is a FAILED test.

5. **Write the test** - descriptive name, `assertAll()` for multiple checks, `@ParameterizedTest` + `@CsvSource` for data-driven. Forbidden: `Thread.sleep()`, hardcoded paths, `By` in test class.

6. **Run locally:** `mvn test -Dtest=OnlinerTests#methodName`. On failure, read the exception type and message and fix the root cause it points to (e.g. `TimeoutException` - element loads late, wait for it via the Page Object; `NoSuchElementException` - wrong selector, re-inspect). Apply the same reasoning to any other exception. Repeat until BUILD SUCCESS.

7. **Verify CI-readiness.** CI runs headless on Linux (`xvfb-run mvn -B test`). Re-check: no hardcoded Windows paths, no reliance on a maximized window or local Chrome profile, no dependence on test execution order. If the test relies on anything machine-specific, fix it.

8. **Invoke code-reviewer** (Task tool, as subagent) and ask for review. Apply every item that concerns the code you changed. If it flags a pre-existing issue in code you didn't touch, note it separately but don't fix it here - stay on the task.

9. **Re-run** `mvn test -Dtest=OnlinerTests#methodName` → must still be green.

## Hard rule
Never report success on a single passing run. Always run the test 3 times - all 3 must be green - before reporting it done.