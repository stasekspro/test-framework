package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class Button extends BaseElement {

    public Button(WebDriver chrome, By locator, String name) {
        super(chrome, locator, name);
    }
    public WebElement elementToBeClickable () {
        return  wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    public void click() {
        WebElement element = elementToBeClickable();
        element.click();
        System.out.println("Clicked on button: " + name);
    }
}