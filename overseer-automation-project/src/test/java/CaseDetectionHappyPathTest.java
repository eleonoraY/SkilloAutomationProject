import PageFactory.CaseDetailsPage;
import PageFactory.CasesPage;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

public class CaseDetectionHappyPathTest extends TestObject {

    @Test
    public void testCaseCreatedWhenTransactionMatchesEnabledRule() {
        // Arrange
        String clientA = publicApiHelper.createClient("{\"name\": \"Client A\"}");
        String clientB = publicApiHelper.createClient("{\"name\": \"Client B\"}");
        String accountA = publicApiHelper.createAccount(clientA, "{\"currency\": \"USD\"}");
        String accountB = publicApiHelper.createAccount(clientB, "{\"currency\": \"USD\"}");

        // Create transactions matching the 'multiple_low_val_trans' rule (e.g., 5 transactions)
        String lastTransactionId = "";
        for (int i = 0; i < 5; i++) {
            lastTransactionId = "TXN_" + System.currentTimeMillis() + i;
            publicApiHelper.createTransaction("{\"amount\": 50, \"from\": \"" + accountA + "\", \"to\": \"" + accountB + "\", \"id\": \"" + lastTransactionId + "\"}");
        }

        // Act
        // Login as Co-worker is handled in TestObject or via loginPage
        // For this skeleton, assume we are already logged in or navigating to the cases page
        getDriver().navigate().to(CasesPage.PAGE_URL);

        // Smart Polling for Case creation
        wait.until(ExpectedConditions.visibilityOf(casesPage.getCasesTable()));
        // In a real test, we would poll the table until the transaction ID appears
        // wait.until(d -> casesPage.getCaseRowByTransactionId(lastTransactionId).isDisplayed());

        casesPage.clickOnCase(lastTransactionId);

        // Assert
        Assert.assertEquals(caseDetailsPage.getStatus(), "Open", "Case status should be Open");
        Assert.assertEquals(caseDetailsPage.getTriggeredRuleName(), "multiple_low_val_trans", "Incorrect triggered rule name");
        // Additional assertions for Transaction ID and Client Information
    }
}
