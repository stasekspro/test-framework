package tests;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BaseTest {
    protected WebDriver chrome;
    protected Actions actions;
    protected WebDriverWait wait;

    @BeforeEach
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "C:\\chromedriver-win64\\chromedriver.exe");

        ChromeOptions options = new ChromeOptions();
        options.setBinary("C:\\Program Files\\Google\\Chrome\\Application\\chrome.exe");
        options.addArguments("--start-maximized");

        chrome = new ChromeDriver(options);
        actions = new Actions(chrome);
        wait = new WebDriverWait(chrome, Duration.ofSeconds(10));
        chrome.get("https://www.onliner.by/");
    }

    @AfterEach
    public void tearDown() {
        if (chrome != null) {
            chrome.quit();
        }
    }
}