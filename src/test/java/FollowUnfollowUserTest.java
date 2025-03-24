import Lecture20.page.factory.Header;
import Lecture20.page.factory.HomePage;
import Lecture20.page.factory.LoginPage;
import Lecture20.page.factory.ProfilePage;
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

public class FollowUnfollowUserTest {
    private WebDriver webDriver;
    private WebDriverWait wait;
    private final String USERNAME = "yovcheva.e@gmail.com";
    private final String PASSWORD = "123456";
    private HomePage homePage;
    private ProfilePage profilePage;
    private Header header;
    public static final String TEST_RESOURCES_DIR = "src\\test\\java\\resources\\";
    public static final String SCREENSHOT_DIR = TEST_RESOURCES_DIR.concat("screenshots\\");

    @BeforeClass
    public void setupTestSuite() throws IOException {
        WebDriverManager.chromedriver().setup();
        cleanDirectory(SCREENSHOT_DIR);
        this.webDriver = new ChromeDriver();
        this.webDriver.manage().window().maximize();
        this.webDriver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
        this.webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        this.webDriver.navigate().to(LoginPage.PAGE_URL);

        homePage = new HomePage(this.webDriver);
        profilePage = new ProfilePage(this.webDriver);
        header = new Header(this.webDriver);

        LoginPage loginPage = new LoginPage(this.webDriver);
        loginPage.populateUsername(USERNAME);
        loginPage.populatePassword(PASSWORD);
        loginPage.clickSignInOnLoginPage();

        Assert.assertTrue(homePage.isUrlLoaded(), "Home page is not loaded");
        header.clickProfileLinkWithHandle();
        Assert.assertTrue(profilePage.isUrlLoaded(9206), "Profile page is not loaded");
    }

    @BeforeMethod
    public void setUpTest() {
        wait = new WebDriverWait(this.webDriver, Duration.ofSeconds(10));
    }

    @AfterMethod
    public void tearDownTest(ITestResult testResult) {
        takeScreenshot(testResult);
    }

    @AfterClass
    public void quitDriver() {
        if (this.webDriver != null) {
            this.webDriver.quit();
        }
    }

    public WebDriver getDriver() {
        return this.webDriver;
    }

    private void cleanDirectory(String directoryPath) throws IOException {
        File directory = new File(directoryPath);
        if (directory.exists() && directory.isDirectory()) {
            FileUtils.cleanDirectory(directory);
            System.out.printf("All files deleted in Directory: %s%n", directoryPath);
        }
    }

    private void takeScreenshot(ITestResult testResult) {
        if (ITestResult.FAILURE == testResult.getStatus()) {
            try {
                File screenshot = ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE);
                String testName = testResult.getName();

                if (testResult.getParameters().length > 0) {
                    testName += "_" + String.join("_", (CharSequence[]) testResult.getParameters());
                }

                FileUtils.copyFile(screenshot, new File(SCREENSHOT_DIR + testName + ".jpg"));
            } catch (IOException ex) {
                System.out.println("Unable to create a screenshot file: " + ex.getMessage());
            }
        }
    }

    @DataProvider(name = "userData")
    public Object[][] userData() {
        return new Object[][]{
                {"TestUserUserUserUser"}
//                {"PeterParker"},
//                {"ludmil1234"}
        };
    }

    @Test(priority = 1, dataProvider = "userData")
    public void testFollowUser(String targetUser) {
        profilePage.searchAndOpenUserProfile(targetUser);

        int initialFollowingCount = profilePage.getFollowingCount();
        Assert.assertEquals(initialFollowingCount, 0, "Initial following count should be 0");

        profilePage.clickFollowButton();
        Assert.assertTrue(profilePage.isUnfollowButtonVisible(), "Follow button did not change to Unfollow");

        profilePage.navigateToMyProfile();
        Assert.assertTrue(profilePage.isUrlLoaded(9206), "Profile page was not opened");

        Assert.assertEquals(profilePage.getFollowingCount(), initialFollowingCount + 1, "Following count did not increase");
    }

    @Test(priority = 2, dataProvider = "userData")
    public void testUnfollowUser(String targetUser) {
        profilePage.searchAndOpenUserProfile(targetUser);

        int initialFollowingCount = profilePage.getFollowingCount();
        Assert.assertTrue(initialFollowingCount > 0, "Initial following count should be greater than 0");

        profilePage.clickUnfollowButton();
        Assert.assertTrue(profilePage.isFollowButtonVisible(), "Unfollow button did not change to Follow");

        profilePage.navigateToMyProfile();
        Assert.assertTrue(profilePage.isUrlLoaded(9206), "Profile page was not opened");
        Assert.assertEquals(profilePage.getFollowingCount(), initialFollowingCount - 1, "Following count did not decrease");
    }
}

