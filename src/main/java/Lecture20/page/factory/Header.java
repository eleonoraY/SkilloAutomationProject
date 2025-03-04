package Lecture20.page.factory;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;

public class Header {
    private final WebDriver webDriver;
    @FindBy(id = "nav-link-profile")
    private WebElement profileLink;
    @FindBy(id = "nav-link-login")
    private WebElement loginLink;
    @FindBy(id = "nav-link-home")
    private WebElement homeLink;

    public Header(WebDriver webDriver){
        this.webDriver = webDriver;
        PageFactory.initElements(webDriver, this);
    }

    public void clickProfileLink(){
        WebDriverWait profilePageLinkWait = new WebDriverWait(this.webDriver, Duration.ofSeconds(3));
        profilePageLinkWait.until(ExpectedConditions.elementToBeClickable(this.profileLink));
        this.profileLink.click();
    }

    public void clickLoginLink(){
        WebDriverWait loginPageLinkWait = new WebDriverWait(this.webDriver, Duration.ofSeconds(3));
        loginPageLinkWait.until(ExpectedConditions.elementToBeClickable(this.loginLink));
        this.loginLink.click();
    }

    public void clickHomeLink(){
        WebDriverWait homePageLinkWait = new WebDriverWait(this.webDriver, Duration.ofSeconds(3));
        homePageLinkWait.until(ExpectedConditions.elementToBeClickable(this.homeLink));
        this.homeLink.click();
    }

    // Combine logic for similar methods based on same actions with external general method
    public void clickHomeLinkWithHandle(){
        waitAndClick(this.homeLink);
    }
    public void clickProfileLinkWithHandle(){
        waitAndClick(this.profileLink);
    }
    public void clickLoginLinkWithHandle(){
        waitAndClick(this.loginLink);
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

/*
    // logic demo for multiple elements with same actions
    public void clickMenuLink(String menuItem){
        WebElement elementToClick;
        switch (menuItem.toLowerCase()){
            case "login":
                elementToClick = this.loginLink;
                break;
            case "profile":
                elementToClick = this.profileLink;
                break;
            case "home":
                elementToClick = this.homeLink;
                break;
            default:
                System.out.println(menuItem + " menu option is not supported");
                return;
        }
        WebDriverWait pageLinkWait = new WebDriverWait(this.webDriver, Duration.ofSeconds(3));
        pageLinkWait.until(ExpectedConditions.elementToBeClickable(elementToClick));
        elementToClick.click();
    }

 */
}