package base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import utils.ConfigReader;

import java.time.Duration;

// Creates, returns and closes the single WebDriver used by the tests.
public class DriverFactory {

    private static WebDriver driver;

    // Starts the browser named in config.properties (headless if configured).
    public static WebDriver initDriver() {
        String browser = ConfigReader.get("browser").toLowerCase();
        boolean headless = ConfigReader.getBoolean("headless");

        switch (browser) {
            case "firefox":
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                if (headless) {
                    firefoxOptions.addArguments("-headless", "--width=1920", "--height=1080");
                }
                driver = new FirefoxDriver(firefoxOptions);
                break;

            case "edge":
                EdgeOptions edgeOptions = new EdgeOptions();
                if (headless) {
                    edgeOptions.addArguments("--headless=new", "--window-size=1920,1080");
                }
                driver = new EdgeDriver(edgeOptions);
                break;

            case "chrome":
                ChromeOptions chromeOptions = new ChromeOptions();
                if (headless) {
                    chromeOptions.addArguments("--headless=new", "--window-size=1920,1080");
                }
                // Needed for Chrome to start reliably on Linux CI machines
                chromeOptions.addArguments("--no-sandbox", "--disable-dev-shm-usage");
                driver = new ChromeDriver(chromeOptions);
                break;

            default:
                throw new IllegalArgumentException("Unsupported browser in config.properties: " + browser);
        }

        if (!headless) {
            driver.manage().window().maximize();
        }
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
        return driver;
    }

    // Returns the current driver so pages, tests and the listener can use it.
    public static WebDriver getDriver() {
        return driver;
    }

    // Closes the browser and clears the reference.
    public static void quitDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
}
