package Lecture16;


import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

import java.util.List;

public class BaseCommandTests {
    @Test
    public void testBrowserCommands() throws InterruptedException {
        WebDriverManager.chromedriver().setup();
        ChromeDriver webDriver = new ChromeDriver();

        // Maximize the browser window
        webDriver.manage().window().maximize();

        // open the url into browser
        webDriver.get("http://training.skillo-bg.com:4200/posts/all");

        // get page title
        String title = webDriver.getTitle();
        System.out.println("Page title is: " + title);

        //Thread.sleep(2000);
        // open the url into browser
        webDriver.get("http://training.skillo-bg.com:4200/users/login");

        // get current page url
        String url = webDriver.getCurrentUrl();
        System.out.println("The page url is: " + url);

        // close current window
        webDriver.close();

        // close all windows
        webDriver.quit();
    }

    @Test
    public void closeCurrentWindow(){
        WebDriverManager.chromedriver().setup();
        ChromeDriver webDriver = new ChromeDriver();

        // Maximize the browser window
        webDriver.manage().window().maximize();

        // open the url into browser
        webDriver.get("http://training.skillo-bg.com:4200/posts/all");

        // get information for current browse window
        Object[] windowHandles = webDriver.getWindowHandles().toArray();

        // open new browser window and load an url
        openUrlInNewWindow(webDriver, "http://training.skillo-bg.com:4200/users/login");

        // close current browser window
        webDriver.close();

        // switch back to the first browser window
        webDriver.switchTo().window((String) windowHandles[0]);

        // get browser current url
        System.out.println(webDriver.getCurrentUrl());
    }

    private void openUrlInNewWindow(ChromeDriver webDriver, String url){
        webDriver.switchTo().newWindow(WindowType.WINDOW);
        webDriver.get(url);
    }

    @Test
    public void testBrowserNavigationCommands() throws InterruptedException {
        WebDriverManager.chromedriver().setup();
        ChromeDriver webDriver = new ChromeDriver();

        // Maximize the browser window
        webDriver.manage().window().maximize();

        // open the url into browser
        webDriver.get("http://training.skillo-bg.com:4200/posts/all");

        // get page title
        String title = webDriver.getTitle();
        System.out.println("Page title is: " + title);

        //Thread.sleep(2000);
        // open the url into browser
        webDriver.navigate().to("http://training.skillo-bg.com:4200/users/login");

        // get current page url
        String url = webDriver.getCurrentUrl();
        System.out.println("The page url is: " + url);

        webDriver.navigate().back();

        // get current page url
        String urlAfterBack = webDriver.getCurrentUrl();
        System.out.println("The page url is: " + urlAfterBack);

        webDriver.navigate().forward();

        // get current page url
        String urlAfterForward = webDriver.getCurrentUrl();
        System.out.println("The page url is: " + urlAfterForward);

        // close current window
        webDriver.close();

        // close all windows
        webDriver.quit();
    }

    @Test
    public void findElement(){
        // Start block for open browser, maximize and open skillo site
        WebDriverManager.chromedriver().setup();
        ChromeDriver webDriver = new ChromeDriver();
        webDriver.manage().window().maximize();
        webDriver.get("http://training.skillo-bg.com:4200/posts/all");
        // End of block

        WebElement login = webDriver.findElement(By.id("nav-link-login"));

        System.out.println(login);

        webDriver.quit();
    }

    @Test
    public void findElements(){
        // Start block for open browser, maximize and open skillo site
        WebDriverManager.chromedriver().setup();
        ChromeDriver webDriver = new ChromeDriver();
        webDriver.manage().window().maximize();
        webDriver.get("http://training.skillo-bg.com:4200/posts/all");
        // End of block

        List<WebElement> navigationLinks = webDriver.findElements(By.xpath("//*[@class='nav-link']"));

        for (WebElement item : navigationLinks) {
            System.out.println(item);
        }

        webDriver.quit();
    }

    @Test
    public void getElements(){
        // Start block for open browser, maximize and open skillo site
        WebDriverManager.chromedriver().setup();
        ChromeDriver webDriver = new ChromeDriver();
        webDriver.manage().window().maximize();
        webDriver.get("http://training.skillo-bg.com:4200/posts/all");
        // End of block

        List<WebElement> navigationLinks = webDriver.findElements(By.xpath("//*[@class='nav-link']"));

        for (WebElement link : navigationLinks) {
            System.out.println("Text of menu link is: " + link.getText());
        }

        webDriver.quit();
    }

    @Test
    public void clickElement(){
        // Start block for open browser, maximize and open skillo site
        WebDriverManager.chromedriver().setup();
        ChromeDriver webDriver = new ChromeDriver();
        webDriver.manage().window().maximize();
        webDriver.get("http://training.skillo-bg.com:4200/posts/all");
        // End of block

        WebElement login = webDriver.findElement(By.id("nav-link-login"));

        System.out.println("Click on " + login.getText());

        login.click();

        System.out.println("Login page is open");

        webDriver.quit();
    }

    @Test
    public void sendKeysElement(){
        // Start block for open browser, maximize and open skillo site
        WebDriverManager.chromedriver().setup();
        ChromeDriver webDriver = new ChromeDriver();
        webDriver.manage().window().maximize();
        webDriver.get("http://training.skillo-bg.com:4200/posts/all");
        // End of block

        WebElement login = webDriver.findElement(By.id("nav-link-login"));

        System.out.println("Click on " + login.getText());

        login.click();

        System.out.println("Login page is open");

        WebElement username = webDriver.findElement(By.id("defaultLoginFormUsername"));
        WebElement password = webDriver.findElement(By.id("defaultLoginFormPassword"));

        username.sendKeys("blabla");
        password.sendKeys("123456");

        webDriver.quit();
    }
}