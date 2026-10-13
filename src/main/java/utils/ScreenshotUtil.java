package utils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

// Takes browser screenshots for the report.
public class ScreenshotUtil {

    // Returns a screenshot as a Base64 string so it is embedded inside docs/index.html (no separate image files).
    public static String takeScreenshotAsBase64(WebDriver driver) {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
    }
}
