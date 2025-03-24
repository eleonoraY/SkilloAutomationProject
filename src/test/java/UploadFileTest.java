import Lecture20.page.factory.*;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

public class UploadFileTest {
    private WebDriver webDriver;
    private WebDriverWait wait;
    private HomePage homePage;
    private Header header;
    private LoginPage loginPage;
    private NewPostPage newPostPage;
    private final String USERNAME = "yovcheva.e@gmail.com";
    private final String PASSWORD = "123456";

    private final String TEST_FILE_PATH = "C:/Users/Eleonora Yovcheva/Desktop/Testing files/testfile.jpg";
    private final String FILE_DESCRIPTION = "Sweet bug file";

    public static final String TEST_RESOURCES_DIR = "src\\test\\java\\resources\\";
    public static final String SCREENSHOT_DIR = TEST_RESOURCES_DIR.concat("screenshots\\");

    @BeforeSuite
    protected final void setupTestSuite() throws IOException {
        WebDriverManager.chromedriver().setup();
        cleanDirectory(SCREENSHOT_DIR);
        this.webDriver = new ChromeDriver();
        wait = new WebDriverWait(this.webDriver, Duration.ofSeconds(10));
        this.webDriver.manage().window().maximize();

        this.webDriver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
        this.webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        this.webDriver.navigate().to(LoginPage.PAGE_URL);

        newPostPage = new NewPostPage(this.webDriver);
        homePage = new HomePage(this.webDriver);
        header = new Header(this.webDriver);
        loginPage = new LoginPage(this.webDriver);
        loginPage.populateUsername(USERNAME);
        loginPage.populatePassword(PASSWORD);
        loginPage.clickSignInOnLoginPage();


        Assert.assertTrue(homePage.isUrlLoaded(), "Home page is not loaded");
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
    public void testUploadFile(){
        header.clickNewPostLink();
        WebElement uploadTitle = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h3[.='Post a picture to share with your awesome followers']")));
        Assert.assertTrue(uploadTitle.isDisplayed(), "Upload page title is not visible.");

        newPostPage.uploadFile(TEST_FILE_PATH);
       WebElement uploadedFileInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@placeholder='testfile.jpg']")));
       Assert.assertTrue(uploadedFileInput.isDisplayed(), "Uploaded file input is not visible");

        newPostPage.enterCaption(FILE_DESCRIPTION);
        newPostPage.submitPost();


        Assert.assertTrue(newPostPage.isPostCreationAlertDisplayed(), "Post creation alert was not displayed.");


    }
}
