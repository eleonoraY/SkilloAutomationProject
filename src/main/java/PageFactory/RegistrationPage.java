package PageFactory;

import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class RegistrationPage {
    public static final String PAGE_URL = "http://training.skillo-bg.com:4200/users/register";
    private final WebDriver webDriver;

    @FindBy(xpath = "//input[@name='username']")
    private WebElement userNameRegistrationField;
    @FindBy(xpath = "//input[@placeholder='email']")
    private WebElement emailRegistrationField;
    @FindBy(id = "defaultRegisterFormPassword")
    private WebElement passwordRegistrationField;
    @FindBy(id = "defaultRegisterPhonePassword")
    private WebElement confirmPasswordRegistrationField;
    @FindBy(id = "sign-in-button")
    private WebElement signInButton;





    public RegistrationPage(WebDriver webDriver){
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

    public void enterUsername(String username){
        this.userNameRegistrationField.sendKeys(username);
    }
    public void enterEmail(String email){
        this.emailRegistrationField.sendKeys(email);
    }
    public void enterPassword(String password){
        this.passwordRegistrationField.sendKeys(password);
    }
    public void enterConfirmPassword(String confirmPassword){
        this.confirmPasswordRegistrationField.sendKeys(confirmPassword);
    }
    public void clickSignInOnRegistrationPage(){
        this.signInButton.click();
    }
}
