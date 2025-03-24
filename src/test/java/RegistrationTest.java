import Lecture20.page.factory.*;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;
import Lecture20.page.factory.RegistrationPage;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

public class RegistrationTest {
    private WebDriver webDriver;
    private WebDriverWait wait;
    private static final String TEST_RESOURCES_DIR = "src/test/java/resources/";
    private static final String SCREENSHOT_DIR = TEST_RESOURCES_DIR.concat("screenshots/");

    @BeforeSuite
    protected void setupTestSuite() throws IOException {
        WebDriverManager.chromedriver().setup();
        cleanDirectory(SCREENSHOT_DIR);
    }

    @BeforeMethod
    protected void setUpTest() {
        webDriver = new ChromeDriver();
        webDriver.manage().window().maximize();
        webDriver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));
        webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
        webDriver.navigate().to("http://training.skillo-bg.com:4200/users/register");
    }

    @AfterMethod
    protected void tearDownTest(ITestResult testResult) {
        takeScreenshot(testResult);
        if (webDriver != null) {
            webDriver.quit();
        }
    }

    private void cleanDirectory(String directoryPath) throws IOException {
        File directory = new File(directoryPath);
        if (directory.exists() && directory.isDirectory()) {
            FileUtils.cleanDirectory(directory);
            System.out.printf("All files are deleted in Directory: %s%n", directoryPath);
        }
    }

    private void takeScreenshot(ITestResult testResult) {
        if (ITestResult.FAILURE == testResult.getStatus() && webDriver != null) {
            try {
                File screenshot = ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE);
                String testName = testResult.getName();
                for (Object param : testResult.getParameters()) {
                    testName = testName + "_" + param;
                }
                FileUtils.copyFile(screenshot, new File(SCREENSHOT_DIR.concat(testName).concat(".jpg")));
            } catch (IOException ex) {
                System.out.println("Unable to create a screenshot file: " + ex.getMessage());
            }
        }
    }

    @DataProvider(name = "userRegistrations")
    public Object[][] userRegistrations() {
        return new Object[][]{
                {"mihovaTestUser", "yax@amgens.com", "123456", "123456"},
                {"eliTestUser", "960@amgens.com", "123qwe", "123qwe"}
        };
    }

    @Test(dataProvider = "userRegistrations")
    public void testUserRegistration(String username, String email, String password, String confirmPassword) {
        Header header = new Header(webDriver);
        HomePage home = new HomePage(webDriver);
        RegistrationPage registrationPage = new RegistrationPage(webDriver);

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

