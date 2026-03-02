package PageFactory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CaseDetailsPage {
    private final WebDriver webDriver;

    @FindBy(id = "case-id-display")
    private WebElement caseIdLabel;

    @FindBy(id = "case-status-display")
    private WebElement statusLabel;

    @FindBy(id = "ruleset-name-display")
    private WebElement rulesetNameLabel;

    @FindBy(id = "triggered-rule-display")
    private WebElement triggeredRuleLabel;

    @FindBy(id = "transaction-details-section")
    private WebElement transactionDetailsSection;

    public CaseDetailsPage(WebDriver webDriver) {
        this.webDriver = webDriver;
        PageFactory.initElements(webDriver, this);
    }

    public String getCaseId() {
        return this.caseIdLabel.getText();
    }

    public String getStatus() {
        return this.statusLabel.getText();
    }

    public String getRulesetName() {
        return this.rulesetNameLabel.getText();
    }

    public String getTriggeredRuleName() {
        return this.triggeredRuleLabel.getText();
    }

    public WebElement getTransactionDetailsSection() {
        return this.transactionDetailsSection;
    }
}
