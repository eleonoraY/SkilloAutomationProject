package PageFactory;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.time.Instant;


public class NewPostPage {
    private final WebDriver webDriver;

    @FindBy(xpath = "//input[@type='file']")
    private WebElement fileUploadInput;

    @FindBy(xpath = "//input[@placeholder='Enter you post caption here']")
    private WebElement captionInput;

    @FindBy(id = "create-post")
    private WebElement submitButton;

    @FindBy(xpath = "//h3[.='Post a picture to share with your awesome followers']")
    private WebElement uploadTitle;

    @FindBy(xpath = "//input[@placeholder='testfile.jpg']")
    private WebElement uploadedFileInput;

    @FindBy(xpath = "//div[contains(text(), 'Post created!')]")
    private WebElement postCreationAlertMessage;

    public NewPostPage(WebDriver webDriver){
        this.webDriver = webDriver;
        PageFactory.initElements(webDriver, this);
    }


    public void uploadFile(String filePath){
        fileUploadInput.sendKeys(filePath);
    }

    public void enterCaption(String caption){
        captionInput.sendKeys(caption);
    }


    public void submitPost(){
        submitButton.click();
    }

    public boolean isUploadTitleDisplayed() {
        return uploadTitle.isDisplayed();
    }

    public boolean isUploadedFileInputDisplayed() {
        return uploadedFileInput.isDisplayed();
    }

    public WebElement getUploadTitle() {
        return uploadTitle;
    }

    public WebElement getUploadedFileInput() {
        return uploadedFileInput;
    }

    public boolean isPostCreationAlertDisplayed() {
        try {
            return postCreationAlertMessage.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}
