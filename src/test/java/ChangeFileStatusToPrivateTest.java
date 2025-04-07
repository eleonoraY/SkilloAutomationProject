import org.testng.Assert;
import org.testng.annotations.*;

public class ChangeFileStatusToPrivateTest extends TestObject{

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
