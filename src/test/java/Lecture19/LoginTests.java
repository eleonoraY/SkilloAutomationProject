import Lecture19.page.object.Header;
import Lecture19.page.object.HomePage;
import Lecture19.page.object.LoginPage;
import Lecture19.page.object.ProfilePage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;

public class LoginTests {
    private final String PASSWORD = "TGdd7EDby83jdAC";
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
        return new Object[][]{{"blasfsafasfabla", 1234, "User not found"},
                {"p0li0m", 5508, "Successful login!"},
                {"dbsdhsh", 1512, "Ivalid password"}};
    }

    @Test(dataProvider = "getUsers")
    public void LoginUser(String username, int userId, String errorMessage) {
        //initial page objects definition
        LoginPage login = new LoginPage(this.webDriver);
        ProfilePage profile = new ProfilePage(this.webDriver);
        Header header = new Header(this.webDriver);
        HomePage home = new HomePage(this.webDriver);

        //validate initial loading of page
        Assert.assertTrue(login.isUrlLoaded(), "The login page is not open");

        //validate the login form is loaded
        String signInTitle = login.getSignInElementText();
        Assert.assertEquals(signInTitle,"Sign in", "Login form is not loaded");

        //fill login form
        login.populatePassword(this.PASSWORD);
        login.populateUsername(username);
        login.clickSignIn();

        //hind for homework - add validation of error/success message of login
        String actualMessage = login.getLoginMessage();
        Assert.assertEquals(actualMessage, errorMessage, "Login message is incorrect!");

        if(!actualMessage.equals("Successful login!")){
            return;
        }

        //validate home page is loaded after login
        Assert.assertEquals(home.isUrlLoaded(), "Home page is not loaded");

        //navigate to profile page
        header.clickProfileLink();

        //validate profile page is loaded
        Assert.assertTrue(profile.isUrlLoaded(userId), "The user profile page is not loaded");

        //validate username is correct
        Assert.assertTrue(profile.isUsernameAsExpected(username), "The username is not as expected");
    }
}
