import Lecture20.page.factory.Header;
import Lecture20.page.factory.HomePage;
import Lecture20.page.factory.LoginPage;
import Lecture20.page.factory.ProfilePage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.Set;

public class RememberMeFunctionality {
    private WebDriver webDriver;
    private WebDriverWait wait;
    private final String USERNAME = "yovcheva.e@gmail.com";
    private final String PASSWORD = "123456";
    private LoginPage loginPage;
    public static final String TEST_RESOURCES_DIR = "src\\test\\java\\resources\\";
    public static final String SCREENSHOT_DIR = TEST_RESOURCES_DIR.concat("screenshots\\");




    @BeforeSuite
    protected final void setUpTestSuite() throws IOException {
        WebDriverManager.chromedriver().setup();
        cleanDirectory(SCREENSHOT_DIR);
        this.webDriver = new ChromeDriver();
        wait = new WebDriverWait(this.webDriver, Duration.ofSeconds(10));
        this.webDriver.manage().window().maximize();
        this.webDriver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
        this.webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        this.webDriver.navigate().to(LoginPage.PAGE_URL);


        loginPage = new LoginPage(this.webDriver);


    }


    @AfterMethod
    protected void tearDownTest(ITestResult testResult){
        takeScreenshot(testResult);
        quitDriver();
    }


    @AfterSuite
    private void quitDriver(){
        if (this.webDriver != null) {
            this.webDriver.quit();
        }
    }
    private void cleanDirectory(String directoryPath) throws IOException {
        File directory = new File(directoryPath);

        Assert.assertTrue(directory.isDirectory(), "Invalid directory");

        FileUtils.cleanDirectory(directory);

        String[] fileList = directory.list();
        if (fileList != null && fileList.length == 0){
            System.out.printf("All files are deleted in Directory: %n%n", directoryPath);
        } else {
            System.out.printf("Unable to delete the files in Directory: %n%n", directoryPath);
        }
    }


    private void takeScreenshot(ITestResult testResult) {
        if (ITestResult.FAILURE == testResult.getStatus()) {
            try {
                TakesScreenshot takesScreenshot = (TakesScreenshot) webDriver;
                File screenshot = takesScreenshot.getScreenshotAs(OutputType.FILE);
                String testName = testResult.getName();
                for (Object param : testResult.getParameters()) {
                    if (!param.toString().isEmpty()) {
                        testName = testName + param;
                    }
                }
                FileUtils.copyFile(screenshot, new File(SCREENSHOT_DIR.concat(testName).concat(".jpg")));
            } catch (IOException ex) {
                System.out.println("Unable to create a screenshot file: " + ex.getMessage());
            }
        }
    }


    @Test
    public void RememberMeFunctionalityPositiveTestCase() throws InterruptedException {

        loginPage.populateUsername(USERNAME);
        loginPage.populatePassword(PASSWORD);

        WebElement rememberMeCheckBox = this.webDriver.findElement(By.xpath("//input[@type='checkbox']"));
        rememberMeCheckBox.click();
        Assert.assertTrue(rememberMeCheckBox.isSelected(), "Remember me checkbox is not selected");


        WebElement signInButton = this.webDriver.findElement(By.id("sign-in-button"));
        signInButton.click();

       Thread.sleep(1000);

        String currentPage = this.webDriver.getCurrentUrl();

        Assert.assertEquals(currentPage, "http://training.skillo-bg.com:4200/posts/all", "The user is not on the login page.");

        Set<Cookie> cookies = this.webDriver.manage().getCookies();

        this.webDriver.quit();

        //new session
        WebDriverManager.chromedriver().setup();
        ChromeDriver chromeDriverNewSession = new ChromeDriver();
        chromeDriverNewSession.get("http://training.skillo-bg.com:4200/users/login");


        for (Cookie cookie : cookies) {
            chromeDriverNewSession.manage().addCookie(cookie);
        }
        chromeDriverNewSession.navigate().refresh();
        Thread.sleep(2000);

        WebElement usernameField = chromeDriverNewSession.findElement(By.id("defaultLoginFormUsername"));
        String userEmail = usernameField.getAttribute("value");
        Assert.assertEquals(userEmail, "yovcheva.e@gmail.com", "Remember Me has not saved username.");
        chromeDriverNewSession.quit();
    }
}
