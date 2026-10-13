package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

// Accounts Overview page plus the left-side menu links available after login.
public class AccountsOverviewPage extends BasePage {

    private final By overviewLink = By.cssSelector("#leftPanel a[href*='overview.htm']");
    private final By openAccountLink = By.cssSelector("#leftPanel a[href*='openaccount.htm']");
    private final By transferFundsLink = By.cssSelector("#leftPanel a[href*='transfer.htm']");
    private final By billPayLink = By.cssSelector("#leftPanel a[href*='billpay.htm']");
    private final By logoutLink = By.cssSelector("#leftPanel a[href*='logout.htm']");
    private final By heading = By.cssSelector("#showOverview h1.title");
    private final By accountLinks = By.cssSelector("#accountTable tbody a");

    // Creates the page object with the shared driver.
    public AccountsOverviewPage(WebDriver driver) {
        super(driver);
    }

    // Clicks "Accounts Overview" in the left menu.
    public void open() {
        click(overviewLink);
    }

    // Returns the page heading text ("Accounts Overview").
    public String getHeading() {
        return getText(heading);
    }

    // Waits for the account table to load (via AJAX) and returns how many accounts it lists.
    public int getAccountCount() {
        return wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(accountLinks, 0)).size();
    }

    // Returns the first account number in the table.
    public String getFirstAccountNumber() {
        return wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(accountLinks, 0)).get(0).getText().trim();
    }

    // Clicks "Open New Account" in the left menu.
    public void goToOpenNewAccount() {
        click(openAccountLink);
    }

    // Clicks "Transfer Funds" in the left menu.
    public void goToTransferFunds() {
        click(transferFundsLink);
    }

    // Clicks "Bill Pay" in the left menu.
    public void goToBillPay() {
        click(billPayLink);
    }

    // Clicks "Log Out" in the left menu.
    public void logout() {
        click(logoutLink);
    }
}
