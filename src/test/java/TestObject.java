import PageFactory.*;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
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

    private static final ThreadLocal<WebDriver> threadLocalDriver = new ThreadLocal<>();
    protected WebDriverWait wait;
    protected HomePage homePage;
    protected Header header;
    protected ProfilePage profilePage;
    protected LoginPage loginPage;
    protected NewPostPage newPostPage;

    protected final String USERNAME = "yovcheva.e@gmail.com";
    protected final String PASSWORD = "123456";

    protected WebDriver getDriver(){
        return threadLocalDriver.get();
    }

    @BeforeSuite
    public void setupTestSuite() throws IOException {
        cleanDirectory(SCREENSHOT_DIR);
        WebDriverManager.chromedriver().setup();
    }


    @BeforeMethod
    public void setupTest() {
        WebDriver driver = new ChromeDriver();
        threadLocalDriver.set(driver);

        driver.manage().window().maximize();
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        this.homePage = new HomePage(driver);
        this.profilePage = new ProfilePage(driver);
        this.header = new Header(driver);
        this.newPostPage = new NewPostPage(driver);
        this.loginPage = new LoginPage(driver);

        driver.navigate().to(LoginPage.PAGE_URL);
        this.loginPage.populateUsername(USERNAME);
        this.loginPage.populatePassword(PASSWORD);
        this.loginPage.clickSignInOnLoginPage();

        Assert.assertTrue(homePage.isUrlLoaded(), "Home page is not loaded");
    }

    @AfterMethod
    public void tearDownTest(ITestResult testResult) {
        takeScreenshot(testResult);

        WebDriver driver = getDriver();
        if (driver != null) {
            driver.quit();
        }
        threadLocalDriver.remove();

    }

    @AfterClass

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
                TakesScreenshot takesScreenshot = (TakesScreenshot) getDriver();
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
