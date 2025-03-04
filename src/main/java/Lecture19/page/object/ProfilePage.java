package Lecture19.page.object;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ProfilePage {
    private final WebDriver webDriver;

    public ProfilePage(WebDriver webDriver){
        this.webDriver = webDriver;
    }

    public boolean isUrlLoaded(int userId){
        WebDriverWait explicitWait = new WebDriverWait(this.webDriver, Duration.ofSeconds(10));
        return explicitWait.until(ExpectedConditions.urlToBe("http://training.skillo-bg.com:4200/users/" + userId));
    }

    public boolean isUsernameAsExpected(String username){
        WebDriverWait profileUsernameWait = new WebDriverWait(this.webDriver, Duration.ofSeconds(3));
        return profileUsernameWait.until(ExpectedConditions.textToBe(By.tagName("h2"), username));
    }
}
