package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.AccountsOverviewPage;
import pages.OpenNewAccountPage;
import pages.TransferFundsPage;

// Tests for transferring money between the user's own accounts.
public class TransferFundsTest extends BaseTest {

    // Opens a second account, transfers $50 into it and checks the confirmation.
    @Test(description = "Transfer funds between own accounts")
    public void transferBetweenOwnAccounts() {
        registerNewUser();
        AccountsOverviewPage overviewPage = new AccountsOverviewPage(driver);
        overviewPage.open();
        String firstAccount = overviewPage.getFirstAccountNumber();

        overviewPage.goToOpenNewAccount();
        String secondAccount = new OpenNewAccountPage(driver).openNewAccount("SAVINGS");

        overviewPage.goToTransferFunds();
        TransferFundsPage transferPage = new TransferFundsPage(driver);
        transferPage.waitForAccounts(2);
        transferPage.transfer("50", firstAccount, secondAccount);

        Assert.assertEquals(transferPage.getResultTitle(), "Transfer Complete!");
        Assert.assertEquals(transferPage.getAmountResult(), "$50.00");
        Assert.assertEquals(transferPage.getFromAccountResult(), firstAccount);
        Assert.assertEquals(transferPage.getToAccountResult(), secondAccount);
    }

    // Test data for negative transfer: amounts the bank must reject.
    @DataProvider(name = "invalidAmounts")
    public Object[][] invalidAmounts() {
        return new Object[][]{
                {""},
                {"abc"}
        };
    }

    // Submits an empty or non-numeric amount and checks that an error is shown instead of a confirmation.
    @Test(dataProvider = "invalidAmounts", description = "Transfer with empty or invalid amount shows an error")
    public void transferWithInvalidAmount(String amount) {
        registerNewUser();
        AccountsOverviewPage overviewPage = new AccountsOverviewPage(driver);
        overviewPage.goToTransferFunds();

        TransferFundsPage transferPage = new TransferFundsPage(driver);
        transferPage.waitForAccounts(1);
        transferPage.transferWithAmount(amount);

        Assert.assertTrue(transferPage.isErrorDisplayed(), "Expected an error for amount: '" + amount + "'");
        Assert.assertFalse(transferPage.isTransferCompleteDisplayed(), "Transfer should not complete for amount: '" + amount + "'");
    }
}
