package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.ConfigReader;

import java.time.Duration;

// Parent of all page classes: holds the driver, the explicit wait and common reusable actions.
public class BasePage {

    protected WebDriver driver;
    protected WebDriverWait wait;

    // Stores the driver and creates a WebDriverWait using the timeout from config.properties.
    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(ConfigReader.getInt("timeout")));
    }

    // Waits until the element is visible and returns it.
    protected WebElement waitForVisible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    // Waits until the element is clickable, then clicks it.
    protected void click(By locator) {
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
    }

    // Waits for the field, clears it and types the text.
    protected void type(By locator, String text) {
        WebElement field = waitForVisible(locator);
        field.clear();
        field.sendKeys(text);
    }

    // Waits for the element and returns its visible text.
    protected String getText(By locator) {
        return waitForVisible(locator).getText().trim();
    }

    // Returns true if the element becomes visible within the timeout, false otherwise.
    protected boolean isDisplayed(By locator) {
        try {
            waitForVisible(locator);
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    // Waits until a dropdown has more than minOptions options (account dropdowns load via AJAX).
    protected void waitForOptions(By dropdown, int minOptions) {
        wait.until(d -> new Select(d.findElement(dropdown)).getOptions().size() > minOptions);
    }

    // Selects a dropdown option by its visible text.
    protected void selectByText(By dropdown, String text) {
        new Select(waitForVisible(dropdown)).selectByVisibleText(text);
    }
}
