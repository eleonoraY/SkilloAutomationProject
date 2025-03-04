package Lecture20.page.factory;

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
    @FindBy(xpath = "//p[@class='h4 mb-4']")
    private WebElement singInTitle;
    @FindBy(xpath = "//*[@class='toast-message ng-star-inserted']")
    private WebElement singInMessage;

    public LoginPage(WebDriver webDriver){
        this.webDriver = webDriver;
        PageFactory.initElements(webDriver, this);
    }

    public boolean isUrlLoaded(){
        WebDriverWait explicitWait = new WebDriverWait(this.webDriver, Duration.ofSeconds(10));
        try{
            explicitWait.until(ExpectedConditions.urlToBe(PAGE_URL));
        }catch(TimeoutException ex) {
            return false;
        }
        return true;
    }

    public void populateUsername(String username){
        this.usernameField.sendKeys(username);
    }

    public void populatePassword(String password){
        this.passwordField.sendKeys(password);
    }

    public void clickSignIn(){
        this.loginButton.click();
    }

    public String getSignInElementText(){
        String singInText = "";

        WebDriverWait explicitWait = new WebDriverWait(this.webDriver, Duration.ofSeconds(10));

        try{
            explicitWait.until(ExpectedConditions.visibilityOf(this.singInTitle));
            singInText = this.singInTitle.getText();
        }catch (TimeoutException exception){
            System.out.println("Title element on Sing In is not loaded. Inner exception: " + exception);
        }

        return singInText;
    }

    public void onSignInMessage(String message){
        WebDriverWait signInMessageWait = new WebDriverWait(this.webDriver, Duration.ofMillis(1500));
        try {
            signInMessageWait.until(
                    ExpectedConditions.textToBePresentInElement(
                            this.singInMessage
                            , message));
        }catch(TimeoutException exception){
            Assert.fail("Sign in message is not present. Inner exception: " + exception);
        }
    }

    public String getSignInMessage(){
        String singInMessageText = "";
        WebDriverWait signInMessageWait = new WebDriverWait(this.webDriver, Duration.ofMillis(1500));
        try {
            signInMessageWait.until(ExpectedConditions.visibilityOf(this.singInMessage));
            singInMessageText = this.singInMessage.getText();
        }catch(TimeoutException exception){
            System.out.println("Sign in message is not visible. Inner exception: " + exception);
            return singInMessageText;
        }
        return singInMessageText;
    }
}