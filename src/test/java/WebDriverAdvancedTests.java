package Lecture18;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class WebDriverAdvancedTests {
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
    }
    @AfterMethod
    private final void tearDownTest(){
        if (this.webDriver != null) {
            this.webDriver.close();
        }
    }

    @Test
    public void testCheckbox(){
       // this.webDriver.get("http://web.archive.org/web/20241224190259/https://demoqa.com/checkbox");
        this.webDriver.get("http://webdriveruniversity.com/Dropdown-Checkboxes-RadioButtons/index.html");
        WebElement labelCheckbox = this.webDriver.findElement(By.xpath("//*[@id='checkboxes']/label[text()='Option 1']"));
       //Check
        labelCheckbox.click();
        //labelCheckbox.click();
        WebElement checkbox = this.webDriver.findElement(By.xpath("//*[@id='checkboxes']/label[text()='Option 1']/input"));
        Assert.assertTrue(checkbox.isSelected(), "The checkbox is not selected");

        //Uncheck
        labelCheckbox.click();
        Assert.assertFalse(checkbox.isSelected(), "The checkbox is selected");

    }

    @Test
    public void testRadioButton(){
        // this.webDriver.get("http://web.archive.org/web/20241224190259/https://demoqa.com/checkbox");
        this.webDriver.get("http://webdriveruniversity.com/Dropdown-Checkboxes-RadioButtons/index.html");
        WebElement greenRadioButton = this.webDriver.findElement(By.xpath("//*[@id=\"radio-buttons\"]/input[@value='green']"));

        Assert.assertFalse(greenRadioButton.isSelected(), "The radiobutton Green is not selected");

        greenRadioButton.click();
        Assert.assertTrue(greenRadioButton.isSelected(), "The radiobutton Green is selected");

        //Add validations for rest of the radiobutton are not selected

        //validate blue radio button
        WebElement blueRadioButton = this.webDriver.findElement(By.xpath("//input[contains(@value,'blue')]"));
        Assert.assertFalse(blueRadioButton.isSelected(), "The radiobutton Blue is selected.");
        blueRadioButton.click();
        Assert.assertTrue(blueRadioButton.isSelected(), "The radiobutton Blue is not selected");

        //validate yellow radio button
        WebElement yellowRadioButton = this.webDriver.findElement(By.xpath("//form[@id='radio-buttons']/input[@value='yellow']"));
        Assert.assertFalse(yellowRadioButton.isSelected(), "The radiobutton Yellow is selected.");
        yellowRadioButton.click();
        Assert.assertTrue(yellowRadioButton.isSelected(), "The radiobutton Yellow is not selected");

        //validate orange radio button
        WebElement orangeRadioButton = this.webDriver.findElement(By.xpath("//*[@class='radio-buttons']/input[@value='orange']"));
        Assert.assertFalse(orangeRadioButton.isSelected(), "The radiobutton Orange is selected.");
        orangeRadioButton.click();
        Assert.assertTrue(orangeRadioButton.isSelected(), "The radiobutton Orange is not selected");

        //validate purple radio button
        WebElement purpleRadioButton = this.webDriver.findElement(By.xpath("//*[@class='radio-buttons']/input[@value='purple']"));
        Assert.assertFalse(purpleRadioButton.isSelected(), "The radiobutton Purple is selected.");
        purpleRadioButton.click();
        Assert.assertTrue(purpleRadioButton.isSelected(), "The radiobutton Purple is not selected");



    }

    @Test
    public void testSelect(){
        this.webDriver.get("http://webdriveruniversity.com/Dropdown-Checkboxes-RadioButtons/index.html");
        Select dropDown = new Select(this.webDriver.findElement(By.id("dropdowm-menu-1")));
        List<WebElement> options = dropDown.getOptions();

        for (WebElement option: options){
            System.out.println(option.getText());
        }

        Assert.assertEquals(dropDown.getFirstSelectedOption().getText(), "JAVA");
        dropDown.selectByIndex(0);

        Assert.assertEquals(dropDown.getFirstSelectedOption().getText(), "JAVA");

        dropDown.selectByIndex(3);

        Assert.assertEquals(dropDown.getFirstSelectedOption().getText(), "Python");


    }

    @Test
    public void testMouseHover() throws InterruptedException {
        ////*[@id="div-hover"]/div[1]/button

        this.webDriver.get("http://webdriveruniversity.com/Actions/index.html");
       // WebElement buttonWithHover = this.webDriver.findElement(By.xpath("*[@id=\"div-hover\"]/div[1]/button"));
        WebElement buttonWithHover = this.webDriver.findElement(By.xpath("//*[@class='dropbtn'][text()='Hover Over Me First!']"));

        Actions actions = new Actions(this.webDriver);

        actions.moveToElement(buttonWithHover).perform();


        //check how to get the visible text under
        WebDriverWait wait = new WebDriverWait(this.webDriver, Duration.ofSeconds(10));
        WebElement hiddenElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"div-hover\"]/div[1]/div/a")));
        System.out.println(hiddenElement.getText());

    }

    @Test
    public void testTable(){
        this.webDriver.get("http://webdriveruniversity.com/Data-Table/index.html");
       // this.webDriver.navigate().to("webdriveruniversity.com/Data-Table/index.html");
        WebElement table = this.webDriver.findElement(By.id("t01"));
       int indexLastNameColumn = 0;

        List<WebElement> tableHeaders = this.webDriver.findElements(By.xpath("//*[@id='t01']//th"));
        for (WebElement column : tableHeaders){
           String columnName = column.getText();
           if(columnName.equalsIgnoreCase("LastName")){
                indexLastNameColumn = tableHeaders.indexOf(column)+1;
           }
       }

        List<WebElement> firstNameRows = this.webDriver.findElements(By.xpath("//*[@id='t01']//tr/td[1]"));
       int indexSearchedLastName = 0;
       for (WebElement row : firstNameRows){
           String firstName = row.getText();
           if(firstName.equalsIgnoreCase("Jemma")){
               indexSearchedLastName = firstNameRows.indexOf(row)+1;
           }
       }


       List<WebElement> lastName = this.webDriver.findElements(By.xpath("//*[@id='t01']//tr/["+indexSearchedLastName+"]/td["+indexLastNameColumn+"]"));
    }
// идеята на таблиците е да ги сведем до двумерен масив


    @Test
    public void testTableSecondOption(){
        this.webDriver.get("http://webdriveruniversity.com/Data-Table/index.html");
        WebElement table = this.webDriver.findElement(By.id("t01"));

        List<WebElement> headerRow = table.findElements(By.tagName("th"));
        for (WebElement column : headerRow){
                String columnName = column.getText();
                  if(columnName.equalsIgnoreCase("lastName")){
                      System.out.println(headerRow.indexOf(column));
                      break;
                 }
            }
    }

    @Test
    public void testTableThirdOption(){
   this.webDriver.get("https://webdriveruniversity.com/Data-Table/index.html");
    WebElement table = this.webDriver.findElement(By.className("table table-light traversal-table"));

    WebElement headerRow = table.findElement(By.tagName("thead"));
    List<WebElement> headerElements = headerRow.findElements(By.tagName("th"));

    // get the index of column "First"

    WebElement  bodyRow = table.findElement(By.tagName("tbody"));
    List<WebElement> headerElementsOfBodyRows = bodyRow.findElements(By.tagName("th"));

    // get the first name of second row
    // locator for the name should have the td element and some indexes

    }

    @Test
    public void testAlert(){
        this.webDriver.get("https://webdriveruniversity.com/Popup-Alerts/index.html");
        this.webDriver.findElement(By.id("button4")).click();

        Alert alert = webDriver.switchTo().alert();
        System.out.println(alert.getText());
        //alert.accept();
        alert.dismiss();

        System.out.println(this.webDriver.findElement(By.id("confirm-alert-text")).getText());

    }

    @Test
    public void testWindows(){
        this.webDriver.get("https://demoqa.com/browser-windows");
        WebElement button = this.webDriver.findElement(By.id("tabButton"));
        button.click();

        String currentURL = this.webDriver.getCurrentUrl();
        Assert.assertEquals(currentURL,"https://demoqa.com/browser-windows");

        List<String> windows = new ArrayList<>(this.webDriver.getWindowHandles());
        String secondWindow = windows.get(1);

        this.webDriver.switchTo().window(secondWindow);
        this.webDriver.close();

        currentURL = this.webDriver.getCurrentUrl();
        Assert.assertEquals(currentURL,"https://demoqa.com/sample");


    }

    @Test
    public void testIFrame(){
        this.webDriver.get("https://demoqa.com/frames");

        WebElement h1Title = this.webDriver.findElement(By.tagName("h1"));
        Assert.assertEquals(h1Title.getText(), "Frames");

        this.webDriver.switchTo().frame("frame2");
        h1Title = this.webDriver.findElement(By.tagName("h1"));
        Assert.assertEquals(h1Title.getText(), "This is a sample page");

        this.webDriver.switchTo().defaultContent();
       // this.webDriver.switchTo().parentFrame();
        h1Title = this.webDriver.findElement(By.tagName("h1"));
        Assert.assertEquals(h1Title.getText(), "Frames");




    }

}
