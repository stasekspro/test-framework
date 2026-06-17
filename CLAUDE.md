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

### Navigation flow

Tests call page methods that return the next page object (e.g., `homePage.openCart()` returns a `CartPage`), then assert on that page's state via its public methods.
