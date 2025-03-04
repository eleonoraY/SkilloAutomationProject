package Lecture20.page.factory;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ProfilePage{
    public static final String PAGE_URL_WITHOUT_USER_ID = "http://training.skillo-bg.com:4200/users/";
    private final WebDriver webDriver;

    @FindBy(tagName = "h2")
    private WebElement usernameText;

    public ProfilePage(WebDriver webDriver){
        this.webDriver = webDriver;
        PageFactory.initElements(webDriver, this);
    }

    public boolean isUrlLoaded(int userId){
        WebDriverWait explicitWait = new WebDriverWait(this.webDriver, Duration.ofSeconds(10));
        try{
            explicitWait.until(ExpectedConditions.urlToBe(this.PAGE_URL_WITHOUT_USER_ID + userId));
        }catch(TimeoutException ex) {
            return false;
        }
        return true;
    }

    public boolean isUsernameAsExpected(String username){
        WebDriverWait profileUsernameWait = new WebDriverWait(this.webDriver, Duration.ofSeconds(3));
        try{
            profileUsernameWait.until(ExpectedConditions.textToBePresentInElement(this.usernameText, username));
        }catch (TimeoutException exception){
            System.out.println("Username text is not present on profile page. Inner exception: " + exception);
            return false;
        }
        return true;
    }
}