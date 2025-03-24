package Lecture20.page.factory;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;

public class ProfilePage{
    public static final String PAGE_URL = "http://training.skillo-bg.com:4200/users/9206";
    public static final String PAGE_URL_WITHOUT_USER_ID = "http://training.skillo-bg.com:4200/users/";
    private final WebDriver webDriver;
    private final Header header;

    @FindBy(tagName = "h2")
    private WebElement usernameText;

    @FindBy(xpath = "//button[contains(text(),'Follow')]")
    private WebElement followButton;

    @FindBy(xpath = "//button[contains(text(),'Unfollow')]")
    private WebElement unFollowButton;

    @FindBy(id="following")
    private WebElement followingLink;

    @FindBy(xpath = "//li[@id='following']//strong")
    private WebElement followingCount;



    public ProfilePage(WebDriver webDriver){
        this.webDriver = webDriver;
        PageFactory.initElements(webDriver, this);
       this.header = new Header(webDriver);
    }

    public boolean isUrlLoaded(int userId){
        WebDriverWait explicitWait = new WebDriverWait(this.webDriver, Duration.ofSeconds(10));
        try{
            explicitWait.until(ExpectedConditions.urlToBe(this.PAGE_URL_WITHOUT_USER_ID + userId));
        }catch(TimeoutException ex) {
            return false;
        }
        return true;
    }

    public boolean isUsernameAsExpected(String username){
        WebDriverWait profileUsernameWait = new WebDriverWait(this.webDriver, Duration.ofSeconds(3));
        try{
            profileUsernameWait.until(ExpectedConditions.textToBePresentInElement(this.usernameText, username));
        }catch (TimeoutException exception){
            System.out.println("Username text is not present on profile page. Inner exception: " + exception);
            return false;
        }
        return true;
    }

    public void searchAndOpenUserProfile(String username){
        WebElement searchInput = this.webDriver.findElement(By.id("search-bar"));
        searchInput.sendKeys(username);

        WebDriverWait wait = new WebDriverWait(this.webDriver, Duration.ofSeconds(15));
        WebElement dropdown = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='dropdown-container']")));
        Assert.assertTrue(dropdown.isDisplayed(), "Dropdown is not displayed");



        WebElement userProfile = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(text(), '" + username + "')]")));
        Assert.assertTrue(userProfile.isDisplayed(), "User label is not displayed");

        userProfile.click();

    }

    public void clickFollowButton(){
        WebDriverWait wait = new WebDriverWait(this.webDriver, Duration.ofSeconds(15));
        wait.until(ExpectedConditions.elementToBeClickable(followButton));
        followButton.click();
    }

    public void clickUnfollowButton(){
        WebDriverWait wait = new WebDriverWait(this.webDriver, Duration.ofSeconds(20));
        wait.until(ExpectedConditions.elementToBeClickable(unFollowButton));
        unFollowButton.click();
    }

    private boolean isElementVisible(WebElement element){
        WebDriverWait wait = new WebDriverWait(this.webDriver, Duration.ofSeconds(5));
        try {
            wait.until(ExpectedConditions.visibilityOf(element));
            return true;
        } catch(TimeoutException e){
            return false;
        }
    }

    public boolean isUnfollowButtonVisible(){
        return isElementVisible(unFollowButton);
    }

    public boolean isFollowButtonVisible(){
       return isElementVisible(followButton);
    }

    public int getFollowingCount(){
        WebDriverWait wait = new WebDriverWait(this.webDriver, Duration.ofSeconds(5));
        try {
            wait.until(ExpectedConditions.visibilityOf(followingCount));
            return Integer.parseInt(followingCount.getText().trim());
        } catch (TimeoutException e){
            System.out.println("Following count is not visible. Inner exception: " + e);
            return -1;
        }
    }
    public WebElement getFollowingCountElement() {
        WebDriverWait wait = new WebDriverWait(this.webDriver, Duration.ofSeconds(5));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@class='following-count']")));
    }

    public void navigateToMyProfile(){
        header.clickProfileLink();
    }

    
}