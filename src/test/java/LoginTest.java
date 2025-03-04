import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginTest {
    @Test
    public void LoginErrorMessageWrongUserAndPassword() throws InterruptedException {
        // Start block for open browser, maximize and open skillo site
        WebDriverManager.chromedriver().setup();
        ChromeDriver webDriver = new ChromeDriver();
        webDriver.manage().window().maximize();
        webDriver.get("http://training.skillo-bg.com:4200/posts/all");
        // End of block
        WebElement login = webDriver.findElement(By.id("nav-link-login"));

        System.out.println("Click on " + login.getText());

        login.click();

        Thread.sleep(500);

        String actualLoadedPage = webDriver.getCurrentUrl();

        Assert.assertEquals(
                actualLoadedPage,
                "http://training.skillo-bg.com:4200/users/login",
                "Login page is not loaded");


        WebElement username = webDriver.findElement(By.id("defaultLoginFormUsername"));
        username.sendKeys("blabla");
        //Assert.assertEquals(usernameAfterKeys.getText(), "blabla", "Username is not populated properly");

        WebElement password = webDriver.findElement(By.id("defaultLoginFormPassword"));
        password.sendKeys("123456");

        WebElement loginButton = webDriver.findElement(By.id("sign-in-button"));
        loginButton.click();

        Thread.sleep(1000);

        WebElement loginErrorMessage =
                webDriver.findElement(By.xpath("//*[@class='toast-message ng-star-inserted']"));

        String actualErrorMessage = loginErrorMessage.getText();
        Assert.assertEquals(
                actualErrorMessage,
                " User not found ",
                "Login error message is different than the expected");
    }
}