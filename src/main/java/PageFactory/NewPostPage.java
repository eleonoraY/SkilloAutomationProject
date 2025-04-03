package Lecture20.page.factory;

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
    public static final String PAGE_URL = "http://training.skillo-bg.com:4200/posts/create";

    @FindBy(xpath = "//input[@type='file']")
    private WebElement fileUploadInput;

    @FindBy(id = "choose-file")
    private WebElement browseButton;

    @FindBy(xpath = "//input[@placeholder='Enter you post caption here']")
    private WebElement captionInput;

    @FindBy(xpath = "//input[@type='checkbox']")
    private WebElement fileStatusToggle;

    @FindBy(id = "create-post")
    private WebElement submitButton;

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

//    public void setPrivateStatus(){
//        if(!visibilityToggle.isSelected()){
//            visibilityTottle.click();
//        }
//    }

//    public void navigateTo(){
//        this.webDriver.get(PAGE_URL);
//    }

    public void submitPost(){
        submitButton.click();
    }

    public boolean isPostCreationAlertDisplayed() {
        try {
            WebDriverWait wait = new WebDriverWait(this.webDriver, Duration.ofSeconds(10));
            WebElement alertMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(text(), 'Post created!')]")));
            return alertMessage.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}
