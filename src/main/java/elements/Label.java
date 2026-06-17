package elements;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import pages.BaseElement;

public class Label extends BaseElement {

    public Label(WebDriver chrome, By locator, String name) {
        super(chrome, locator, name);
    }
}