package PageFactory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {
    public static final String PAGE_URL = "http://placeholder-url.com/login";
    private final WebDriver webDriver;

    @FindBy(id = "username_placeholder")
    private WebElement usernameField;

    @FindBy(id = "password_placeholder")
    private WebElement passwordField;

    @FindBy(id = "login_button_placeholder")
    private WebElement loginButton;

    public LoginPage(WebDriver webDriver) {
        this.webDriver = webDriver;
        PageFactory.initElements(webDriver, this);
    }

    public void enterUsername(String username) {
        this.usernameField.sendKeys(username);
    }

    public void enterPassword(String password) {
        this.passwordField.sendKeys(password);
    }

    public void clickLoginButton() {
        this.loginButton.click();
    }
}
