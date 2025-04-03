package PageFactory;

import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;

public class LoginPage {
    public static final String PAGE_URL = "http://training.skillo-bg.com:4200/users/login";
    private final WebDriver webDriver;
    @FindBy(id="defaultLoginFormUsername")
    private WebElement usernameField;
    @FindBy(id="defaultLoginFormPassword")
    private WebElement passwordField;
    @FindBy(id="sign-in-button")
    private WebElement loginButton;
    @FindBy(xpath = "//input[@type='checkbox']")
    private WebElement rememberMeCheckBox;

    public LoginPage(WebDriver webDriver){
        this.webDriver = webDriver;
        PageFactory.initElements(webDriver, this);
    }

    public void populateUsername(String username){
        this.usernameField.sendKeys(username);
    }

    public void populatePassword(String password){
        this.passwordField.sendKeys(password);
    }

    public void clickSignInOnLoginPage(){
        this.loginButton.click();
    }

}