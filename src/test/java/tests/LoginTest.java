package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.AccountsOverviewPage;
import pages.LoginPage;

// Tests for logging in (positive and negative) and for the Accounts Overview page after login.
public class LoginTest extends BaseTest {

    // Registers a fresh user, logs out, then logs back in with the same credentials.
    @Test(description = "Login with valid credentials opens Accounts Overview")
    public void loginWithValidCredentials() {
        String username = registerNewUser();
        AccountsOverviewPage overviewPage = new AccountsOverviewPage(driver);
        overviewPage.logout();

        new LoginPage(driver).login(username, PASSWORD);

        Assert.assertEquals(overviewPage.getHeading(), "Accounts Overview");
    }

    // Logs in as a fresh user and checks the overview table lists at least one account.
    @Test(description = "Accounts Overview shows the user's accounts after login")
    public void accountsOverviewLoadsAfterLogin() {
        String username = registerNewUser();
        AccountsOverviewPage overviewPage = new AccountsOverviewPage(driver);
        overviewPage.logout();

        new LoginPage(driver).login(username, PASSWORD);

        Assert.assertEquals(overviewPage.getHeading(), "Accounts Overview");
        Assert.assertTrue(overviewPage.getAccountCount() >= 1, "Expected at least one account in the table");
        Assert.assertFalse(overviewPage.getFirstAccountNumber().isEmpty(), "Account number should not be empty");
    }

    // Test data for negative login: username, password, expected error message.
    @DataProvider(name = "invalidLoginData")
    public Object[][] invalidLoginData() {
        return new Object[][]{
                {"john", "wrongPassword123", "The username and password could not be verified."},
                {"", "", "Please enter a username and password."},
                {"john", "", "Please enter a username and password."}
        };
    }

    // Tries each invalid combination from the DataProvider and checks the error message.
    @Test(dataProvider = "invalidLoginData", description = "Login with invalid or empty credentials shows an error")
    public void loginWithInvalidCredentials(String username, String password, String expectedError) {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(username, password);

        Assert.assertEquals(loginPage.getErrorMessage(), expectedError);
    }
}
