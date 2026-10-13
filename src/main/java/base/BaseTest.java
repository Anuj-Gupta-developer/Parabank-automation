package base;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import pages.RegisterPage;
import utils.ConfigReader;

// Parent class for every test: opens a fresh browser before each test and closes it after.
public class BaseTest {

    protected static final String PASSWORD = "Test@123";

    protected WebDriver driver;

    // Runs before each @Test: starts the browser and opens the ParaBank home page.
    @BeforeMethod
    public void setUp() {
        driver = DriverFactory.initDriver();
        driver.get(ConfigReader.get("baseUrl"));
    }

    // Runs after each @Test (even if it failed): closes the browser.
    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        DriverFactory.quitDriver();
    }

    // Builds a username that is unique on every run, e.g. "qa1760252400123".
    protected String uniqueUsername() {
        return "qa" + System.currentTimeMillis();
    }

    // Registers a brand-new user (ParaBank logs them in automatically) and returns the username.
    protected String registerNewUser() {
        String username = uniqueUsername();
        RegisterPage registerPage = new RegisterPage(driver);
        registerPage.open();
        registerPage.fillPersonalDetails("John", "Doe", "1 Main Street", "Austin", "TX", "73301", "5551234567", "123456789");
        registerPage.fillLoginDetails(username, PASSWORD, PASSWORD);
        registerPage.submit();
        return username;
    }
}
