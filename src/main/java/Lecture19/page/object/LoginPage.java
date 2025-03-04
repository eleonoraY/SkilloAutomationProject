package Lecture19.page.object;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage {
    private final WebDriver webDriver;

    public LoginPage(WebDriver webDriver){
        this.webDriver = webDriver;
    }


    public boolean isUrlLoaded(){
        WebDriverWait explicitWait = new WebDriverWait(this.webDriver, Duration.ofSeconds(10));
        return explicitWait.until(ExpectedConditions.urlToBe("http://training.skillo-bg.com:4200/users/login"));
    }

    public void populateUsername(String username){
        WebElement usernameField = webDriver.findElement(By.id("defaultLoginFormUsername"));
        usernameField.sendKeys(username);
    }

    public void populatePassword(String password){
        WebElement passwordField = webDriver.findElement(By.id("defaultLoginFormPassword"));
        passwordField.sendKeys(password);

    }

    public void clickSignIn(){
        WebElement loginButton = this.webDriver.findElement(By.id("sign-in-button"));
        loginButton.click();
    }

    public String getSignInElementText(){
        WebElement signInTitle = this.webDriver.findElement(By.xpath("//p[@class='h4 mb-4']"));
        WebDriverWait explicitwait = new WebDriverWait(this.webDriver, Duration.ofSeconds(10));
        explicitwait.until(ExpectedConditions.visibilityOf(signInTitle));
        return signInTitle.getText();
    }

    public String getLoginMessage(){
        WebDriverWait wait = new WebDriverWait(this.webDriver, Duration.ofSeconds(5));
        WebElement messageElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#toast-container")));
        return messageElement.getText();
    }
}
