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
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

public class TestObject {
    public static final String TEST_RESOURCES_DIR = "src/test/java/resources/";
    public static final String SCREENSHOT_DIR = TEST_RESOURCES_DIR.concat("screenshots/");

    protected WebDriver webDriver;
    protected WebDriverWait wait;
    protected HomePage homePage;
    protected Header header;
    protected ProfilePage profilePage;
    protected LoginPage loginPage;

    protected final String USERNAME = "yovcheva.e@gmail.com";
    protected final String PASSWORD = "123456";

    @BeforeClass
    public void setupTestSuite() throws IOException {
        cleanDirectory(SCREENSHOT_DIR);

        WebDriverManager.chromedriver().setup();
        this.webDriver = new ChromeDriver();
        this.webDriver.manage().window().maximize();
        this.webDriver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
        this.webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        this.wait = new WebDriverWait(this.webDriver, Duration.ofSeconds(10));
    }

    @BeforeMethod
    public void setupTest() {
        wait = new WebDriverWait(this.webDriver, Duration.ofSeconds(10));
        this.homePage = new HomePage(this.webDriver);
        this.profilePage = new ProfilePage(this.webDriver);
        this.header = new Header(this.webDriver);
        this.loginPage = new LoginPage(webDriver);

        this.webDriver.navigate().to(LoginPage.PAGE_URL);

        this.loginPage.populateUsername(USERNAME);
        this.loginPage.populatePassword(PASSWORD);
        this.loginPage.clickSignInOnLoginPage();

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

    public void cleanDirectory(String directoryPath) throws IOException {
        File directory = new File(directoryPath);
        Assert.assertTrue(directory.isDirectory(), "Invalid directory");

        FileUtils.cleanDirectory(directory);

        String[] fileList = directory.list();
        if (fileList != null && fileList.length == 0){
            System.out.printf("All files are deleted in Directory: %s%n", directoryPath);
        } else {
            System.out.printf("Unable to delete the files in Directory: %s%n", directoryPath);
        }
    }

    private void takeScreenshot(ITestResult testResult){
        if (ITestResult.FAILURE == testResult.getStatus()){
            try {
                TakesScreenshot takesScreenshot = (TakesScreenshot) webDriver;
                File screenshot = takesScreenshot.getScreenshotAs(OutputType.FILE);
                String testName = testResult.getName();
                for(Object param : testResult.getParameters()){
                    if (!param.toString().isEmpty()){
                        testName = testName + param;
                    }
                }
                FileUtils.copyFile(screenshot, new File(SCREENSHOT_DIR.concat(testName).concat(".jpg")));
            } catch (IOException ex){
                System.out.println("Unable to create a screenshot file: " + ex.getMessage());
            }
        }
    }
}
