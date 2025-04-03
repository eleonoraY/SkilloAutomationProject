import PageFactory.Header;
import PageFactory.HomePage;
import PageFactory.LoginPage;
import PageFactory.ProfilePage;
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

public class FileStatusTest extends TestObject{



    @BeforeMethod
    public void setupTest(){
        super.setupTest();
        header.clickProfileLinkWithHandle();
        Assert.assertTrue(profilePage.isUrlLoaded(9206), "Profile page is not loaded");
    }


    @Test
    public void testChangeFileStatusToPrivate(){
        profilePage.clickUploadedFile();
        profilePage.clickLockButton();

        boolean isFileStillVisible = profilePage.isFileStillVisible();
        Assert.assertFalse(isFileStillVisible, "File should not be visible in the public list");
    }


}
