import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class TestObject {
    public static final String TEST_RESOURCES_DIR = "src\\test\\resources\\";
    public static final String REPORTS_DIR = TEST_RESOURCES_DIR.concat("reports\\");
   // public static final String DOWNLOAD_DIR = TEST_RESOURCES_DIR.concat("download\\");
    public static final String SCREENSHOT_DIR = TEST_RESOURCES_DIR.concat("screenshot\\");
    private WebDriver webDriver;
    @BeforeSuite
    protected void setupTestSuite() throws IOException {
        cleanDirectory(REPORTS_DIR);
        cleanDirectory(SCREENSHOT_DIR);
        WebDriverManager.chromedriver().setup();
        WebDriverManager.firefoxdriver().setup();
        WebDriverManager.edgedriver().setup();
    }

//    @BeforeMethod
//    protected void setUpTest(){
//        this.webDriver = new ChromeDriver(configChromeOptions());
//        this.webDriver.manage().window().maximize();
//        this.webDriver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(60));
//        this.webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(45));
//    }
    @AfterMethod
    protected void tearDownTest(ITestResult testResult){
        takeScreenshot(testResult);
        quitDriver();
    }

    @AfterSuite
    protected WebDriver getDriver(){
        return this.webDriver;
    }

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

//    private ChromeOptions configChromeOptions() {
//        Map<String, Object> prefs = new HashMap<>();
//        prefs.put("download.default_directory", System.getProperty("user.dir").concat("\\").concat(DOWNLOAD_DIR));
//        ChromeOptions chromeOptions = new ChromeOptions();
//        chromeOptions.setExperimentalOption("prefs", prefs);
//        return chromeOptions;
//    }

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
