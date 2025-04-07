import PageFactory.Header;
import PageFactory.HomePage;
import PageFactory.LoginPage;
import PageFactory.ProfilePage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

public class SearchFunctionalityOnMainMenu extends TestObject{
    private static final String TARGET_USER = "TestUserUserUserUser";


    @Test(priority = 1)
    public void testSearchUser() {
        header.searchForUser(TARGET_USER);

        WebElement dropdown = header.getDropdownContainer();
        Assert.assertTrue(dropdown.isDisplayed(), "Dropdown with results is not visible");

        WebElement searchResult = header.waitForSearchResultToBeClickable(TARGET_USER, 10);
        Assert.assertTrue(searchResult.isDisplayed(), "Search result does not contain expected username");
    }

    @Test(priority = 2)
    public void testClickOnSearchResult() {
        header.searchForUser(TARGET_USER);
        WebElement searchResult = header.waitForSearchResultToBeClickable(TARGET_USER, 10);
        Assert.assertTrue(searchResult.isDisplayed(), "Search result does not contain expected username");

        header.clickOnSearchResult(TARGET_USER);
        Assert.assertTrue(profilePage.isUsernameAsExpected(TARGET_USER), "Did not navigate to the correct profile page");
    }

    @Test(priority = 3)
    public void testCaseInsensitiveSearch() {
        String caseInsensitiveUsername = "TESTUsEruseruseruser";
        header.searchForUser(caseInsensitiveUsername);

        WebElement searchResult = header.waitForSearchResultToBeClickable(TARGET_USER, 10);
        Assert.assertTrue(searchResult.isDisplayed(), "Case-insensitive search did not return expected results");
    }

    @Test(priority = 4)
    public void testPartialSearch() {
        String partialUsername = "tes";
        header.searchForUser(partialUsername);

        WebElement searchResult = header.waitForSearchResultToBeClickable(TARGET_USER, 10);
        Assert.assertTrue(searchResult.isDisplayed(), "Partial search did not return expected results");
    }
}
