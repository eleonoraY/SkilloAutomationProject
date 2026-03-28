package PageFactory;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class CasesPage {
    private final WebDriver webDriver;
    public static final String PAGE_URL = "https://base-dev.azurewebsites.net/cases/list";

    @FindBy(id = "cases-table-id")
    private WebElement casesTable;

    @FindBy(className = "case-row")
    private List<WebElement> caseRows;

    public CasesPage(WebDriver webDriver) {
        this.webDriver = webDriver;
        PageFactory.initElements(webDriver, this);
    }

    public WebElement getCasesTable() {
        return this.casesTable;
    }

    public List<WebElement> getCaseRows() {
        return this.caseRows;
    }

    public WebElement getCaseRowByTransactionId(String transactionId) {
        // Placeholder for locating a specific row by transaction ID
        return this.webDriver.findElement(By.xpath("//tr[contains(.,'" + transactionId + "')]"));
    }

    public void clickOnCase(String transactionId) {
        getCaseRowByTransactionId(transactionId).click();
    }
}
