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
        waitForElementToBeClickable(this.profileLink, 3);
        this.profileLink.click();
    }


    public void clickNewPostLink(){
        waitForElementToBeClickable(this.newPostLink, 3);
        this.newPostLink.click();
    }


    public void clickProfileLinkWithHandle(){
        waitForElementToBeClickable(this.profileLink, 3);
        this.profileLink.click();
    }



    private void waitForElementToBeClickable(WebElement element, int timeoutSeconds){
        try{
            new WebDriverWait(webDriver, Duration.ofSeconds(timeoutSeconds)).until(ExpectedConditions.elementToBeClickable(element));
        } catch (TimeoutException e) {
            Assert.fail("Element was not clickable within " + timeoutSeconds + " seconds. Inner exception: " + e);
        }
    }

    private void waitForElementToBeVisible(WebElement element, int timeoutSeconds){
        try{
            new WebDriverWait(webDriver, Duration.ofSeconds(timeoutSeconds)).until(ExpectedConditions.visibilityOf(element));
        } catch (TimeoutException e) {
            Assert.fail("Element was not visible within " + timeoutSeconds + " seconds. Inner exception: " + e);
        }
    }

public void searchForUser(String username){
    searchInput.sendKeys(username);
    waitForElementToBeVisible(dropdownContainer, 15);
}

public WebElement waitForSearchResultToBeClickable(String username, int timeoutSeconds){
        WebElement result = getSearchResult(username);
        if(result==null) {
            Assert.fail("No search result found for user: " + username);
        }

        waitForElementToBeClickable(result, timeoutSeconds);
        return result;
}


public void clickOnSearchResult(String username){

   waitForElementToBeVisible(dropdownContainer, 10);

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