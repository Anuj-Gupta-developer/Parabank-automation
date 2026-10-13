package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.RegisterPage;

// Tests for creating a new ParaBank customer.
public class RegisterTest extends BaseTest {

    // Registers a user with a unique username and checks the welcome heading and success message.
    @Test(description = "Register a new user with a unique username")
    public void registerNewUserSuccessfully() {
        String username = registerNewUser();

        RegisterPage registerPage = new RegisterPage(driver);
        Assert.assertEquals(registerPage.getWelcomeTitle(), "Welcome " + username);
        Assert.assertTrue(registerPage.getSuccessMessage().contains("Your account was created successfully"),
                "Unexpected message: " + registerPage.getSuccessMessage());
    }
}
