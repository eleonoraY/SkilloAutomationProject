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

import java.io.File;
import java.io.IOException;
import java.time.Duration;

public class FileStatusTest extends TestObject{
    private WebDriver webDriver;
    private WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
    private HomePage homePage;
    private Header header;
    private LoginPage loginPage;
    private ProfilePage profilePage;
    private final String USERNAME = "yovcheva.e@gmail.com";
    private final String PASSWORD = "123456";
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


    @Test
    public void testChangeFileStatusToPrivate(){
        WebElement uploadedFile = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='post-img']")));
        uploadedFile.click();

        WebElement lockButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//i[contains(@class,'fas fa-unlock ng-star-inserted')]")));
        lockButton.click();

        boolean isFileStillVisible = webDriver.findElements(By.xpath("//div[@class='post-img']" )).isEmpty();
        Assert.assertFalse(isFileStillVisible, "File should not be visible in the public list");
    }


}
