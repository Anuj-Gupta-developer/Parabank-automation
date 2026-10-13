package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

// Register page: locators and actions to create a new ParaBank customer.
public class RegisterPage extends BasePage {

    private final By registerLink = By.cssSelector("#loginPanel a[href*='register.htm']");
    private final By firstName = By.id("customer.firstName");
    private final By lastName = By.id("customer.lastName");
    private final By street = By.id("customer.address.street");
    private final By city = By.id("customer.address.city");
    private final By state = By.id("customer.address.state");
    private final By zipCode = By.id("customer.address.zipCode");
    private final By phone = By.id("customer.phoneNumber");
    private final By ssn = By.id("customer.ssn");
    private final By username = By.id("customer.username");
    private final By password = By.id("customer.password");
    private final By confirmPassword = By.id("repeatedPassword");
    private final By registerButton = By.cssSelector("#customerForm input[value='Register']");
    private final By pageTitle = By.cssSelector("#rightPanel h1.title");
    private final By successMessage = By.cssSelector("#rightPanel p");

    // Creates the page object with the shared driver.
    public RegisterPage(WebDriver driver) {
        super(driver);
    }

    // Clicks the "Register" link on the home page to open the form.
    public void open() {
        click(registerLink);
    }

    // Fills the personal details section of the form.
    public void fillPersonalDetails(String first, String last, String streetText, String cityText,
                                    String stateText, String zip, String phoneText, String ssnText) {
        type(firstName, first);
        type(lastName, last);
        type(street, streetText);
        type(city, cityText);
        type(state, stateText);
        type(zipCode, zip);
        type(phone, phoneText);
        type(ssn, ssnText);
    }

    // Fills the username, password and confirm password fields.
    public void fillLoginDetails(String user, String pass, String confirmPass) {
        type(username, user);
        type(password, pass);
        type(confirmPassword, confirmPass);
    }

    // Clicks the Register button and waits until the next page has loaded.
    public void submit() {
        WebElement button = waitForVisible(registerButton);
        button.click();
        wait.until(ExpectedConditions.stalenessOf(button));
    }

    // Returns the heading shown after registering, e.g. "Welcome qa123".
    public String getWelcomeTitle() {
        return getText(pageTitle);
    }

    // Returns the message shown under the heading after registering.
    public String getSuccessMessage() {
        return getText(successMessage);
    }
}
