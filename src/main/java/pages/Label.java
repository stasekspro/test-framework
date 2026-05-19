package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class Label extends BaseElement {

    public Label(WebDriver chrome, By locator, String name) {
        super(chrome, locator, name);
    }
}