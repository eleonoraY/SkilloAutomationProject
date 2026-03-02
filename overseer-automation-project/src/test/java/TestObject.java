import PageFactory.*;
import ApiHelpers.PublicApiHelper;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;

import java.time.Duration;

public class TestObject {
    private static final ThreadLocal<WebDriver> threadLocalDriver = new ThreadLocal<>();
    protected WebDriverWait wait;

    // Page Objects
    protected LoginPage loginPage;
    protected CasesPage casesPage;
    protected CaseDetailsPage caseDetailsPage;

    // API Helpers
    protected PublicApiHelper publicApiHelper;

    protected WebDriver getDriver(){
        return threadLocalDriver.get();
    }

    @BeforeSuite
    public void setupTestSuite() {
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

        // Initialize Page Objects
        this.loginPage = new LoginPage(driver);
        this.casesPage = new CasesPage(driver);
        this.caseDetailsPage = new CaseDetailsPage(driver);

        // Initialize API Helper with placeholders
        this.publicApiHelper = new PublicApiHelper(
            "https://base-public-api-dev.azurewebsites.net",
            "placeholder_client_id",
            "placeholder_client_secret"
        );
    }

    @AfterMethod
    public void tearDownTest() {
        WebDriver driver = getDriver();
        if (driver != null) {
            driver.quit();
        }
        threadLocalDriver.remove();
    }
}
