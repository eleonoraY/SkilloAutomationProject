import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import java.time.Duration;

public class WindowHandling {

    private WebDriver webDriver;

    @BeforeSuite
    protected final void setupTestSuite() {
        WebDriverManager.chromedriver().setup();
        WebDriverManager.firefoxdriver().setup();
        WebDriverManager.edgedriver().setup();
    }

    @BeforeMethod
    protected final void setUpTest() {
        this.webDriver = new ChromeDriver();
        //this.webDriver = new EdgeDriver();
        this.webDriver.manage().window().maximize();
        this.webDriver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));

        this.webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
    }

    @AfterMethod
    private final void tearDownTest() {
        if (this.webDriver != null) {
            this.webDriver.close();
        }
    }

    @Test
    public void windows() {
        this.webDriver.get("https://www.lambdatest.com/selenium-playground/window-popup-modal-demo");
        String parentWindow = webDriver.getWindowHandle();
        System.out.println(parentWindow);
        webDriver .findElement(By.linkText("Follow On Twitter")).click();
        String title = webDriver.getTitle();
        System.out.println(title);
        webDriver.close();
    }
}
