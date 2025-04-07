import PageFactory.Header;
import PageFactory.HomePage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;
import PageFactory.RegistrationPage;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

public class RegistrationTest extends TestObject{

    @BeforeMethod
    public void setupTest() {
        super.setupTest();
        getDriver().navigate().to("http://training.skillo-bg.com:4200/users/register");
    }

    @DataProvider(name = "userRegistrations")
    public Object[][] userRegistrations() {
        return new Object[][]{
                {"testuser05", "testuser05@mail.com", "Pass@1234", "Pass@1234"},
                {"user_test05", "user.test05@mail.com", "SecurePass1!", "SecurePass1!"}
        };
    }

    @Test(dataProvider = "userRegistrations")
    public void testUserRegistration(String username, String email, String password, String confirmPassword) {
        Header header = new Header(getDriver());
        HomePage home = new HomePage(getDriver());
        RegistrationPage registrationPage = new RegistrationPage(getDriver());

        Assert.assertTrue(registrationPage.isUrlLoaded(), "The registration page is not loaded");

        registrationPage.enterUsername(username);
        registrationPage.enterEmail(email);
        registrationPage.enterPassword(password);
        registrationPage.enterConfirmPassword(confirmPassword);
        registrationPage.clickSignInOnRegistrationPage();

        Assert.assertTrue(home.isUrlLoaded(), "The home page is not loaded");

        header.clickProfileLink();
        WebElement userName = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2[contains(text(),'" + username + "')]")));
        Assert.assertTrue(userName.isDisplayed(), "Username is not visible");
    }
}

