package Lecture17;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;

public class UserTests {
    private final String PASSWORD = "TGdd7EDby83jdAC";
    private WebDriver webDriver;
    @BeforeSuite
    protected final void setupTestSuite(){
        WebDriverManager.chromedriver().setup();
        WebDriverManager.firefoxdriver().setup();
        WebDriverManager.edgedriver().setup();
    }

    @BeforeMethod
    protected final void setUpTest(){
        this.webDriver = new ChromeDriver();
        //this.webDriver = new EdgeDriver();
        this.webDriver.manage().window().maximize();
        this.webDriver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));

        this.webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
    }
    @AfterMethod
    private final void tearDownTest(){
        if (this.webDriver != null) {
            this.webDriver.close();
        }
    }

    @DataProvider(name="getUsers")
    public Object[][] getUsers(){
        return new Object[][]{{"blasfsafasfabla", 1234, "User not found"},
                {"p0li0m", 5508, "Successful login!"},
                {"dbsdhsh", 1512, "Ivalid password"}};
    }

    @Test(dataProvider = "getUsers")
    public void LoginErrorMessageWrongUserAndPassword(String username, int userId, String errorMessage) throws InterruptedException {
        this.webDriver.navigate().to("http://training.skillo-bg.com:4200/posts/all");
        WebElement loginNavigationLink = this.webDriver.findElement(By.id("nav-link-login"));

        loginNavigationLink.click();
        /*
        Old way with error due to too fast selenium
        String actualLoadedPage = webDriver.getCurrentUrl();

        Assert.assertEquals(
                actualLoadedPage,
                "http://training.skillo-bg.com:4200/users/login",
                "Login page is not loaded");
        */
        // New way
        WebDriverWait explicitWait = new WebDriverWait(this.webDriver,Duration.ofSeconds(10));
        explicitWait.until(ExpectedConditions.urlToBe("http://training.skillo-bg.com:4200/users/login"));

        /* Old way
        WebElement username = webDriver.findElement(By.id("defaultLoginFormUsername"));
         */
        // new way
        WebElement usernameField = explicitWait.until(ExpectedConditions.visibilityOf(webDriver.findElement(By.id("defaultLoginFormUsername"))));

        usernameField.sendKeys(username);

        WebDriverWait explicitWaitPasswordField = new WebDriverWait(this.webDriver,Duration.ofSeconds(2));
        WebElement passwordField =
                explicitWaitPasswordField.until((ExpectedConditions.visibilityOf(this.webDriver.findElement(By.id("defaultLoginFormPassword")))));
        passwordField.sendKeys(this.PASSWORD);

        WebElement loginButton = this.webDriver.findElement(By.id("sign-in-button"));
        loginButton.click();

        WebDriverWait errorWait = new WebDriverWait(this.webDriver, Duration.ofMillis(1500));
        errorWait.until(
                ExpectedConditions.textToBe(
                        By.xpath("//*[@class='toast-message ng-star-inserted']")
                        ,errorMessage));
    }

    @Test(dataProvider = "getUsers")
    public void LoginUser(String username, int userId, String errorMessage) throws InterruptedException {
        this.webDriver.navigate().to("http://training.skillo-bg.com:4200/posts/all");
        WebElement loginNavigationLink = this.webDriver.findElement(By.id("nav-link-login"));

        loginNavigationLink.click();
        /*
        Old way with error due to too fast selenium
        String actualLoadedPage = webDriver.getCurrentUrl();

        Assert.assertEquals(
                actualLoadedPage,
                "http://training.skillo-bg.com:4200/users/login",
                "Login page is not loaded");
        */
        // New way
        WebDriverWait explicitWait = new WebDriverWait(this.webDriver,Duration.ofSeconds(10));
        explicitWait.until(ExpectedConditions.urlToBe("http://training.skillo-bg.com:4200/users/login"));

        /* Old way
        WebElement username = webDriver.findElement(By.id("defaultLoginFormUsername"));
         */
        // new way
        WebElement usernameField = explicitWait.until(ExpectedConditions.visibilityOf(webDriver.findElement(By.id("defaultLoginFormUsername"))));

        usernameField.sendKeys(username);

        WebDriverWait explicitWaitPasswordField = new WebDriverWait(this.webDriver,Duration.ofSeconds(2));
        WebElement passwordField =
                explicitWaitPasswordField.until((ExpectedConditions.visibilityOf(this.webDriver.findElement(By.id("defaultLoginFormPassword")))));
        passwordField.sendKeys(this.PASSWORD);

        WebElement loginButton = this.webDriver.findElement(By.id("sign-in-button"));
        loginButton.click();

        WebDriverWait errorWait = new WebDriverWait(this.webDriver, Duration.ofMillis(1500));
        errorWait.until(
                ExpectedConditions.textToBe(
                        By.xpath("//*[@class='toast-message ng-star-inserted']")
                        ,errorMessage));

        explicitWait.until(ExpectedConditions.urlToBe("http://training.skillo-bg.com:4200/posts/all"));

        WebDriverWait profilePageLinkWait = new WebDriverWait(this.webDriver, Duration.ofSeconds(3));
        WebElement profileLink = profilePageLinkWait.until(ExpectedConditions.elementToBeClickable(By.id("nav-link-profile")));
        profileLink.click();

        // new WebDriverWait(this.webDriver, Duration.ofSeconds(3)).until(ExpectedConditions.elementToBeClickable(By.id("nav-link-profile"))).click();

        explicitWait.until(ExpectedConditions.urlToBe("http://training.skillo-bg.com:4200/users/" + userId));

        WebDriverWait profileUsernameWait = new WebDriverWait(this.webDriver, Duration.ofSeconds(3));
        profileUsernameWait.until(ExpectedConditions.textToBe(By.tagName("h2"), username));
    }
}