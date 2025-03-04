package Lecture20;

import Lecture20.page.factory.Header;
import Lecture20.page.factory.HomePage;
import Lecture20.page.factory.LoginPage;
import Lecture20.page.factory.ProfilePage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;

public class LoginTests {
    //private final String PASSWORD = "TGdd7EDby83jdAC";
    private WebDriver webDriver;
    @BeforeSuite
    protected final void setupTestSuite(){
        WebDriverManager.chromedriver().setup();
        WebDriverManager.firefoxdriver().setup();
        WebDriverManager.edgedriver().setup();
    }

    @BeforeMethod
    protected final void setUpTest(){
        this.webDriver = new ChromeDriver();
        //this.webDriver = new EdgeDriver();
        this.webDriver.manage().window().maximize();
        this.webDriver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));

        this.webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        this.webDriver.navigate().to("http://training.skillo-bg.com:4200/users/login");
    }
    @AfterMethod
    private final void tearDownTest(){
        if (this.webDriver != null) {
            this.webDriver.close();
        }
    }

    @DataProvider(name="getUsers")
    public Object[][] getUsers(){
        return new Object[][]{
                {"vidko@test.com","123abc", 8335, "Successful login!"},
                {"vidko.v","123abc", 8335, "Successful login!"},
                {"p0li0m","TGdd7EDby83jdAC", 5508, "Successful login!"},
                {"dbsdhsh","sdhshs", 8333, "Successful login!"}};
    }

    @DataProvider(name="errorMessages")
    public Object[][] errorMessages(){
        return new Object[][]{
                // username
                {"blasfsafasfabla","asfsaf", "User not found"},
                {"dbsdhsh","asdasfsaf", "Ivalid password"},
                {"dbsdhsh","", "Password cannot be empty"},
                // email
                {"blasfsafasfabla@tezt.xom","asfsaf", "User not found"},
                {"blasfsafasfabla@.xom","asfsaf", "User not found"},
                {"blasfsafasfabla@","asfsaf", "User not found"},
                {"vidko@test.com","asdasfsaf", "Ivalid password"},
                {"viasfasfasfsadko@teafasfasfst.com","", "Password cannot be empty"},
                // valid for both email and username
                {"","TGdd7EDby83jdAC", "UsernameOrEmail cannot be empty"},
                {"","", "UsernameOrEmail cannot be empty"},
        };
    }

    @Test(dataProvider = "getUsers")
    public void LoginUser(String username, String password, int userId, String signInMessage){
        // initial page objects definition
        LoginPage login = new LoginPage(this.webDriver);
        ProfilePage profile = new ProfilePage(this.webDriver);
        Header header = new Header(this.webDriver);
        HomePage home = new HomePage(this.webDriver);

        // validate initial loading of page
        Assert.assertTrue(login.isUrlLoaded(),"The login page is not loaded");

        // validate the login form is loaded
        Assert.assertEquals(login.getSignInElementText(),"Sign in", "Login form is not loaded");

        // fill login form
        login.populatePassword(password);
        login.populateUsername(username);
        login.clickSignIn();

        // check the sing in message
        login.onSignInMessage(signInMessage);

        // validate home page is loaded after login
        Assert.assertTrue(home.isUrlLoaded(), "Home page is not loaded");

        // navigate to profile page
        header.clickProfileLinkWithHandle();

        // validate profile page is loaded
        Assert.assertTrue(profile.isUrlLoaded(userId),"The user profile page is not loaded");

        // validate username is correct
        Assert.assertTrue(profile.isUsernameAsExpected(username), "The username is not as expected");
    }

    @Test(dataProvider = "errorMessages")
    public void LoginErrorMessages(String username, String password, String signInMessageExpected){
        // initial page objects definition
        LoginPage login = new LoginPage(this.webDriver);
        ProfilePage profile = new ProfilePage(this.webDriver);
        Header header = new Header(this.webDriver);
        HomePage home = new HomePage(this.webDriver);

        // validate initial loading of page
        Assert.assertTrue(login.isUrlLoaded(),"The login page is not loaded");

        // validate the login form is loaded
        Assert.assertEquals(login.getSignInElementText(),"Sign in", "Login form is not loaded");

        // fill login form
        login.populatePassword(password);
        login.populateUsername(username);
        login.clickSignIn();

        // check the sing in message
        String singInMessageActual = login.getSignInMessage();

        // Final validation of the test
        Assert.assertEquals(singInMessageActual, signInMessageExpected, "Sing in error message is not as expected");
    }
}