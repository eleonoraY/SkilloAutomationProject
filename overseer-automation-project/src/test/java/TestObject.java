import PageFactory.*;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.IOException;
import java.time.Duration;

public class TestObject {
    public static final String TEST_RESOURCES_DIR = "src/test/java/resources/";
    public static final String SCREENSHOT_DIR = TEST_RESOURCES_DIR.concat("screenshots/");

    private static final ThreadLocal<WebDriver> threadLocalDriver = new ThreadLocal<>();
    protected WebDriverWait wait;

    // Page Objects
    protected LoginPage loginPage;

    // Placeholder constants
    protected final String USERNAME = "placeholder_user";
    protected final String PASSWORD = "placeholder_password";

    protected WebDriver getDriver(){
        return threadLocalDriver.get();
    }

    @BeforeSuite
    public void setupTestSuite() throws IOException {
        // Reference project calls cleanDirectory(SCREENSHOT_DIR);
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

        this.loginPage = new LoginPage(driver);

        // Structural example of initial setup
        // driver.navigate().to(LoginPage.PAGE_URL);
    }

    @AfterMethod
    public void tearDownTest(ITestResult testResult) {
        // takeScreenshot(testResult);

        WebDriver driver = getDriver();
        if (driver != null) {
            driver.quit();
        }
        threadLocalDriver.remove();
    }

    @AfterClass
    public void cleanDirectory(String directoryPath) throws IOException {
        // Placeholder for directory cleaning logic
    }

    private void takeScreenshot(ITestResult testResult){
        // Placeholder for screenshot taking logic
    }
}
