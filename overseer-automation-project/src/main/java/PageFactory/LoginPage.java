package PageFactory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {
    private final WebDriver webDriver;

    @FindBy(id = "placeholder_id_1")
    private WebElement usernameField;

    @FindBy(name = "placeholder_name_1")
    private WebElement passwordField;

    @FindBy(xpath = "//placeholder_xpath_1")
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
