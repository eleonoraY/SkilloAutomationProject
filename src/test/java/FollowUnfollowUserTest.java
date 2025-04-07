import PageFactory.Header;
import PageFactory.HomePage;
import PageFactory.LoginPage;
import PageFactory.ProfilePage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

public class FollowUnfollowUserTest extends TestObject {

    @BeforeMethod
    public void setupTest() {
        super.setupTest();
        profilePage.clearSearchBar();

    }

    @DataProvider(name = "userData")
    public Object[][] userData() {
        return new Object[][]{
                {"TonyStark"},
                {"andyzlzlzl"},
                {"Mariel"}
        };
    }

    @Test(priority = 1, dataProvider = "userData")
    public void testFollowUser(String targetUser) {
        profilePage.searchAndOpenUserProfile(targetUser);

        int initialFollowingCount = profilePage.getFollowingCount();

        if (profilePage.isUnfollowButtonVisible()) {
            profilePage.clickUnfollowButton();
            profilePage.waitForFollowingCountToUpdate(initialFollowingCount);
            Assert.assertFalse(profilePage.isUnfollowButtonVisible(), "Unfollow button should not be visible after unfollowing");
            initialFollowingCount = profilePage.getFollowingCount();
        }

        profilePage.clickFollowButton();
        profilePage.waitForFollowingCountToUpdate(initialFollowingCount);

        Assert.assertTrue(profilePage.isUnfollowButtonVisible(), "Follow button did not change to Unfollow");

        profilePage.navigateToMyProfile();
        Assert.assertTrue(profilePage.isUrlLoaded(9206), "Profile page was not opened");

        Assert.assertEquals(profilePage.getFollowingCount(), initialFollowingCount + 1, "Following count did not increase correctly");
    }


    @Test(priority = 2, dataProvider = "userData")
    public void testUnfollowUser(String targetUser) {

        profilePage.navigateToMyProfile();
        Assert.assertTrue(profilePage.isUrlLoaded(9206), "Profile page was not opened");
        int initialFollowingCount = profilePage.getFollowingCount();
        Assert.assertTrue(initialFollowingCount > 0, "Initial following count should be greater than 0");

        profilePage.searchAndOpenUserProfile(targetUser);
        profilePage.clickUnfollowButton();
        profilePage.waitForFollowingCountToUpdate(initialFollowingCount);

        Assert.assertTrue(profilePage.isFollowButtonVisible(), "Unfollow button did not change to Follow");

        profilePage.navigateToMyProfile();
        Assert.assertTrue(profilePage.isUrlLoaded(9206), "Profile page was not opened");
        Assert.assertEquals(profilePage.getFollowingCount(), initialFollowingCount - 1, "Following count did not decrease");
    }
}

