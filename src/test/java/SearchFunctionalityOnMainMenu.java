import Lecture20.page.factory.Header;
import Lecture20.page.factory.HomePage;
import Lecture20.page.factory.LoginPage;
import Lecture20.page.factory.ProfilePage;
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

public class SearchFunctionalityOnMainMenu {
    private WebDriver webDriver;
    private WebDriverWait wait;
    private static final String USERNAME = "yovcheva.e@gmail.com";
    private static final String PASSWORD = "123456";
    private static final String TARGET_USER = "TestUserUserUserUser";
    private HomePage homePage;
    private ProfilePage profilePage;
    private Header header;
    private LoginPage loginPage;
    public static final String TEST_RESOURCES_DIR = "src\\test\\java\\resources\\";
    public static final String SCREENSHOT_DIR = TEST_RESOURCES_DIR.concat("screenshots\\");

    @BeforeClass
    public void setupTestSuite() throws IOException {
        WebDriverManager.chromedriver().setup();
        cleanDirectory(SCREENSHOT_DIR);

        this.webDriver = new ChromeDriver();
        this.wait = new WebDriverWait(webDriver, Duration.ofSeconds(15));
        this.webDriver.manage().window().maximize();
        this.webDriver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
        this.webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

        this.webDriver.navigate().to(LoginPage.PAGE_URL);

        homePage = new HomePage(webDriver);
        profilePage = new ProfilePage(webDriver);
        header = new Header(webDriver);
        loginPage = new LoginPage(webDriver);

        loginPage.populateUsername(USERNAME);
        loginPage.populatePassword(PASSWORD);
        loginPage.clickSignInOnLoginPage();

        Assert.assertTrue(homePage.isUrlLoaded(), "Home page is not loaded");
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

    private void cleanDirectory(String directoryPath) throws IOException {
        File directory = new File(directoryPath);
        if (!directory.exists() || !directory.isDirectory()) {
            System.out.printf("Directory %s does not exist.%n", directoryPath);
            return;
        }

        FileUtils.cleanDirectory(directory);
        System.out.printf("Cleaned directory: %s%n", directoryPath);
    }

    private void takeScreenshot(ITestResult testResult) {
        if (ITestResult.FAILURE == testResult.getStatus()) {
            try {
                TakesScreenshot takesScreenshot = (TakesScreenshot) webDriver;
                File screenshot = takesScreenshot.getScreenshotAs(OutputType.FILE);
                String testName = testResult.getName();
                FileUtils.copyFile(screenshot, new File(SCREENSHOT_DIR.concat(testName).concat(".jpg")));
            } catch (IOException ex) {
                System.out.println("Unable to create a screenshot file: " + ex.getMessage());
            }
        }
    }

    @Test(priority = 1)
    public void testSearchUser() {
        header.searchForUser(TARGET_USER);

        WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='dropdown-container']")));
        Assert.assertTrue(dropdown.isDisplayed(), "Dropdown with results is not visible");

        WebElement searchResult = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(text(), '" + TARGET_USER + "')]")));
        Assert.assertTrue(searchResult.isDisplayed(), "Search result does not contain expected username");
    }

    @Test(priority = 2)
    public void testClickOnSearchResult() {
        header.searchForUser(TARGET_USER);
        WebElement searchResult = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(text(), '" + TARGET_USER + "')]")));
        Assert.assertTrue(searchResult.isDisplayed(), "Search result does not contain expected username");

        header.clickOnSearchResult(TARGET_USER);
        Assert.assertTrue(profilePage.isUsernameAsExpected(TARGET_USER), "Did not navigate to the correct profile page");
    }

    @Test(priority = 3)
    public void testCaseInsensitiveSearch() {
        String caseInsensitiveUsername = "TESTUsEruseruseruser";
        header.searchForUser(caseInsensitiveUsername);

        WebElement searchResult = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(text(), '" + TARGET_USER + "')]")));
        Assert.assertTrue(searchResult.isDisplayed(), "Case-insensitive search did not return expected results");
    }

    @Test(priority = 4)
    public void testPartialSearch() {
        String partialUsername = "tes";
        header.searchForUser(partialUsername);

        WebElement searchResult = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(text(), '" + TARGET_USER + "')]")));
        Assert.assertTrue(searchResult.isDisplayed(), "Partial search did not return expected results");
    }
}
