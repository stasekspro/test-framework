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

### Inheritance & where things go

- Element wrappers (`Button`, `Label`, ...) extend `BaseElement` and live in `elements/`.
- Page classes extend `BasePage`, implement `isPageLoaded()`, and live in `pages/`.
- Test classes extend `BaseTest` and live in `tests/`.

New classes follow the same rule, so this stays accurate as the project grows.

### Navigation flow

Tests call page methods that return the next page object (e.g., `homePage.openCart()` returns a `CartPage`), then assert on that page's state via its public methods.