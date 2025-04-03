import PageFactory.Header;
import PageFactory.HomePage;
import PageFactory.LoginPage;
import PageFactory.NewPostPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

public class UploadFileTest extends TestObject{
    private NewPostPage newPostPage;
    private final String TEST_FILE_PATH = "C:/Users/Eleonora Yovcheva/Desktop/Testing files/testfile.jpg";
    private final String FILE_DESCRIPTION = "Sweet bug file";


    @BeforeMethod
    public void setupTest(){
        super.setupTest();
        newPostPage = new NewPostPage(this.webDriver);
    }


    @Test
    public void testUploadFile(){
        header.clickNewPostLink();

        wait.until(ExpectedConditions.visibilityOf(newPostPage.getUploadTitle()));
        Assert.assertTrue(newPostPage.isUploadTitleDisplayed(), "Upload page title is not visible.");


        newPostPage.uploadFile(TEST_FILE_PATH);

        wait.until(ExpectedConditions.visibilityOf(newPostPage.getUploadedFileInput()));
        Assert.assertTrue(newPostPage.isUploadedFileInputDisplayed(), "Uploaded file input is not visible");

        newPostPage.enterCaption(FILE_DESCRIPTION);
        newPostPage.submitPost();


        Assert.assertTrue(newPostPage.isPostCreationAlertDisplayed(), "Post creation alert was not displayed.");


    }
}
