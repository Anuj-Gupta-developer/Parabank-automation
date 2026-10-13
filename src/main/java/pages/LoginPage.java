package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

// Login panel on the ParaBank home page: locators and actions for logging in.
public class LoginPage extends BasePage {

    private final By usernameField = By.name("username");
    private final By passwordField = By.name("password");
    private final By loginButton = By.cssSelector("#loginPanel input[value='Log In']");
    private final By errorMessage = By.cssSelector("#rightPanel p.error");

    // Creates the page object with the shared driver.
    public LoginPage(WebDriver driver) {
        super(driver);
    }

    // Types the username and password and clicks Log In.
    public void login(String username, String password) {
        type(usernameField, username);
        type(passwordField, password);
        click(loginButton);
    }

    // Returns the error text shown after a failed login.
    public String getErrorMessage() {
        return getText(errorMessage);
    }
}
