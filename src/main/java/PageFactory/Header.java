package PageFactory;

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
import java.util.List;

public class Header {
    private final WebDriver webDriver;
    @FindBy(id = "nav-link-profile")
    private WebElement profileLink;
    @FindBy(id = "search-bar")
    private WebElement searchInput;
    @FindBy(id="nav-link-new-post")
    private WebElement newPostLink;
    @FindBy(xpath = "//div[@class='dropdown-container']")
    private WebElement dropdownContainer;
    @FindBy(xpath = "//div[@class='dropdown-container']//a")
    private List<WebElement> searchResults;


    public Header(WebDriver webDriver){
        this.webDriver = webDriver;
        PageFactory.initElements(webDriver, this);
    }

    public void clickProfileLink(){
        WebDriverWait profilePageLinkWait = new WebDriverWait(this.webDriver, Duration.ofSeconds(3));
        profilePageLinkWait.until(ExpectedConditions.elementToBeClickable(this.profileLink));
        this.profileLink.click();
    }


    public void clickNewPostLink(){
        WebDriverWait newPostLinkWait = new WebDriverWait(this.webDriver, Duration.ofSeconds(3));
        newPostLinkWait.until(ExpectedConditions.elementToBeClickable(this.newPostLink));
        this.newPostLink.click();
    }


    public void clickProfileLinkWithHandle(){
        waitAndClick(this.profileLink);
    }

    private void waitAndClick(WebElement element){
        try {
            WebDriverWait pageLinkWait = new WebDriverWait(this.webDriver, Duration.ofSeconds(3));
            pageLinkWait.until(ExpectedConditions.elementToBeClickable(element));
        }catch(TimeoutException exception){
            Assert.fail("Header navigation link is not found. Inner exception: " + exception);
        }
        element.click();
    }

public void searchForUser(String username){
    searchInput.sendKeys(username);

    WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(15));
    wait.until(ExpectedConditions.visibilityOf(dropdownContainer));
}


public void clickOnSearchResult(String username){

    WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
    wait.until(ExpectedConditions.visibilityOf(dropdownContainer));

    for (WebElement result : searchResults) {
        if (result.getText().contains(username)) {
            result.click();
            break;
        }
    }
}

    public WebElement getSearchResult(String username) {
        for (WebElement result : searchResults) {
            if (result.getText().contains(username)) {
                return result;
            }
        }
        return null;
    }

    public WebElement getDropdownContainer() {
        return dropdownContainer;
    }

}