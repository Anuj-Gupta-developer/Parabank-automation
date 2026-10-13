package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.AccountsOverviewPage;
import pages.BillPayPage;

// Tests for paying a bill to a payee.
public class BillPayTest extends BaseTest {

    // Pays $25 to a payee and checks the confirmation shows the payee name and amount.
    @Test(description = "Bill Pay to a payee completes successfully")
    public void payBillSuccessfully() {
        registerNewUser();
        new AccountsOverviewPage(driver).goToBillPay();

        BillPayPage billPayPage = new BillPayPage(driver);
        billPayPage.fillPayeeDetails("Electric Company", "10 Power Road", "Austin", "TX", "73301", "5559876543");
        billPayPage.fillPaymentDetails("12345", "12345", "25");
        billPayPage.sendPayment();

        Assert.assertEquals(billPayPage.getResultTitle(), "Bill Payment Complete");
        Assert.assertEquals(billPayPage.getResultPayeeName(), "Electric Company");
        Assert.assertEquals(billPayPage.getResultAmount(), "$25.00");
    }

    // Enters two different account numbers and checks the mismatch error is shown.
    @Test(description = "Bill Pay with mismatched account numbers shows an error")
    public void payBillWithMismatchedAccounts() {
        registerNewUser();
        new AccountsOverviewPage(driver).goToBillPay();

        BillPayPage billPayPage = new BillPayPage(driver);
        billPayPage.fillPayeeDetails("Electric Company", "10 Power Road", "Austin", "TX", "73301", "5559876543");
        billPayPage.fillPaymentDetails("12345", "54321", "25");
        billPayPage.sendPayment();

        Assert.assertTrue(billPayPage.isAccountMismatchErrorDisplayed(), "Mismatch error should be visible");
        Assert.assertEquals(billPayPage.getAccountMismatchErrorText(), "The account numbers do not match.");
    }
}
