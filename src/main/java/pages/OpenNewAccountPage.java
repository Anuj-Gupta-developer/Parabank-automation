package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

// Open New Account page: used to create a second account so funds can be transferred between own accounts.
public class OpenNewAccountPage extends BasePage {

    private final By accountType = By.id("type");
    private final By fromAccount = By.id("fromAccountId");
    private final By openAccountButton = By.cssSelector("#openAccountForm input[value='Open New Account']");
    private final By newAccountNumber = By.id("newAccountId");

    // Creates the page object with the shared driver.
    public OpenNewAccountPage(WebDriver driver) {
        super(driver);
    }

    // Opens a new account of the given type ("CHECKING" or "SAVINGS") and returns its account number.
    public String openNewAccount(String type) {
        selectByText(accountType, type);
        waitForOptions(fromAccount, 0);
        click(openAccountButton);
        wait.until(d -> !d.findElement(newAccountNumber).getText().isBlank());
        return getText(newAccountNumber);
    }
}
