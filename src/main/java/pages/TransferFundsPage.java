package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

// Transfer Funds page: locators and actions to move money between accounts.
public class TransferFundsPage extends BasePage {

    private final By amountField = By.id("amount");
    private final By fromAccount = By.id("fromAccountId");
    private final By toAccount = By.id("toAccountId");
    private final By transferButton = By.cssSelector("#transferForm input[value='Transfer']");
    private final By resultTitle = By.cssSelector("#showResult h1.title");
    private final By amountResult = By.id("amountResult");
    private final By fromAccountResult = By.id("fromAccountIdResult");
    private final By toAccountResult = By.id("toAccountIdResult");
    private final By errorPanel = By.cssSelector("#showError p.error");

    // Creates the page object with the shared driver.
    public TransferFundsPage(WebDriver driver) {
        super(driver);
    }

    // Waits until the account dropdowns contain at least the given number of accounts.
    public void waitForAccounts(int count) {
        waitForOptions(fromAccount, count - 1);
        waitForOptions(toAccount, count - 1);
    }

    // Enters the amount, chooses both accounts and clicks Transfer.
    public void transfer(String amount, String fromAccountNumber, String toAccountNumber) {
        type(amountField, amount);
        selectByText(fromAccount, fromAccountNumber);
        selectByText(toAccount, toAccountNumber);
        click(transferButton);
    }

    // Enters only the amount (using the default accounts) and clicks Transfer.
    public void transferWithAmount(String amount) {
        type(amountField, amount);
        click(transferButton);
    }

    // Returns the heading shown after a successful transfer ("Transfer Complete!").
    public String getResultTitle() {
        return getText(resultTitle);
    }

    // Returns the transferred amount shown in the confirmation, e.g. "$50.00".
    public String getAmountResult() {
        return getText(amountResult);
    }

    // Returns the "from" account number shown in the confirmation.
    public String getFromAccountResult() {
        return getText(fromAccountResult);
    }

    // Returns the "to" account number shown in the confirmation.
    public String getToAccountResult() {
        return getText(toAccountResult);
    }

    // Returns true if the error panel appears after the transfer.
    public boolean isErrorDisplayed() {
        return isDisplayed(errorPanel);
    }

    // Returns true if the success panel is visible right now (no waiting).
    public boolean isTransferCompleteDisplayed() {
        return driver.findElement(resultTitle).isDisplayed();
    }
}
