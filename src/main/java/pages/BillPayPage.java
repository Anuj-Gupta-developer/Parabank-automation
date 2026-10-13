package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

// Bill Pay page: locators and actions to pay a bill to a payee.
public class BillPayPage extends BasePage {

    private final By payeeName = By.name("payee.name");
    private final By street = By.name("payee.address.street");
    private final By city = By.name("payee.address.city");
    private final By state = By.name("payee.address.state");
    private final By zipCode = By.name("payee.address.zipCode");
    private final By phone = By.name("payee.phoneNumber");
    private final By accountNumber = By.name("payee.accountNumber");
    private final By verifyAccountNumber = By.name("verifyAccount");
    private final By amount = By.name("amount");
    private final By fromAccount = By.name("fromAccountId");
    private final By sendPaymentButton = By.cssSelector("#billpayForm input[value='Send Payment']");
    private final By resultTitle = By.cssSelector("#billpayResult h1.title");
    private final By resultPayeeName = By.id("payeeName");
    private final By resultAmount = By.cssSelector("#billpayResult #amount");
    private final By accountMismatchError = By.id("validationModel-verifyAccount-mismatch");

    // Creates the page object with the shared driver.
    public BillPayPage(WebDriver driver) {
        super(driver);
    }

    // Fills the payee's name, address and phone number.
    public void fillPayeeDetails(String name, String streetText, String cityText,
                                 String stateText, String zip, String phoneText) {
        type(payeeName, name);
        type(street, streetText);
        type(city, cityText);
        type(state, stateText);
        type(zipCode, zip);
        type(phone, phoneText);
    }

    // Fills the account number, the verify account number and the amount.
    public void fillPaymentDetails(String account, String verifyAccount, String amountText) {
        type(accountNumber, account);
        type(verifyAccountNumber, verifyAccount);
        type(amount, amountText);
    }

    // Waits for the "From account" dropdown to load, then clicks Send Payment.
    public void sendPayment() {
        waitForOptions(fromAccount, 0);
        click(sendPaymentButton);
    }

    // Returns the heading shown after a successful payment ("Bill Payment Complete").
    public String getResultTitle() {
        return getText(resultTitle);
    }

    // Returns the payee name shown in the confirmation.
    public String getResultPayeeName() {
        return getText(resultPayeeName);
    }

    // Returns the amount shown in the confirmation, e.g. "$25.00".
    public String getResultAmount() {
        return getText(resultAmount);
    }

    // Returns true if "The account numbers do not match." is shown.
    public boolean isAccountMismatchErrorDisplayed() {
        return isDisplayed(accountMismatchError);
    }

    // Returns the account mismatch error text.
    public String getAccountMismatchErrorText() {
        return getText(accountMismatchError);
    }
}
