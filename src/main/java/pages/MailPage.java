package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;

import static org.testng.Assert.assertTrue;
import static pages.locators.MailPage.*;

public class MailPage  extends BasePage<MailPage>{

    @FindBy(xpath = INBOX)
    WebElement inbox;

    @FindBy(xpath = COMPOSE_BUTTON)
    WebElement composeButton;



    public MailPage(WebDriver beanDriver) {
        super(beanDriver);
    }

    public MailForm raiseMailCompose(){
        clickIfVisible(composeButton);
        return (MailForm) this.new MailForm(driver).get();
    }

    @Step("Open Inbox")
    public void openInbox(){
        clickIfVisible(inbox);
    }

    @Step("Open received mail by body part: {0}")
    public void openMailByBodyPart(String part){
        click(getElementBy(By.xpath("//*[contains(text(),'"+part+"')]/..")));
    }

    @Step("Verify opened email to has valid body: {0}")
    public boolean checkOpenedEmailBody(String body){
       return webElementIsVisible(getElementBy(By.xpath(String.format(MAIL_BODY_TEXT,body ))));
    }

    @Step("Get amount of received mails")
    public String getAmountOfReceivedMails(){
        waitSeconds(1);
        String inboxText = inbox.getText();
        return inboxText.substring(inboxText.indexOf("(")+1,inboxText.indexOf(")"));
    }

    public void markFirstMailCheckBox(){
        clickIfVisible(getElementBy(By.xpath(LAST_MAIL_CHECKBOX)));
    }

    public void clickDeleteButton(){
        waitSeconds(1);
        Actions acts = new Actions(driver).contextClick(getElementBy(By.xpath(LAST_MAIL_CHECKBOX))).sendKeys(Keys.ARROW_UP).sendKeys(Keys.ENTER);
        acts.perform();
    }

    @Step("Remove last received Email")
    public void removeLastReceivedMail(){
        markFirstMailCheckBox();
        clickDeleteButton();
    }
    @Step("Check if NO Emails labels is shown")
    public boolean isInboxEmpty(){
        return webElementIsVisible(getElementBy(By.xpath(NO_MAILS_LABEL)));
    }

    @Override
    protected void load() {
        clickWithJS(inbox);
    }

    @Override
    protected void isLoaded() {
        assertTrue(webElementIsVisible(inbox),"Inbox appears");
        assertTrue(webElementIsVisible(composeButton),"Compose button exists");
    }


    public class MailForm extends MailPage{

        @FindBy(xpath = MAIL_SUBJECT)
        WebElement subjectInput;

        @FindBy(xpath = MAIL_TO)
        WebElement toInput;

        @FindBy(xpath = MAIL_BODY)
        WebElement bodyInput;

        @FindBy(xpath = SEND_BUTTON)
        WebElement sendButton;

        public MailForm(WebDriver beanDriver) {
            super(beanDriver);
        }

        public void setSubject(String subjectValue){
            typeText(subjectInput,subjectValue);
        }

        public void setRecipient(String recipient){
            typeText(toInput,recipient);
        }

        public void setBodyText(String bodyText){
            typeText(bodyInput,bodyText);
        }

        public void clickSend(){
            clickIfVisible(sendButton);
        }

        @Step("Send mail with Recipient : {0} Subject: {1} and Body text: {2} ")
        public void sendMail(String recipient,String subject, String body){
            setRecipient(recipient);
            setSubject(subject);
            setBodyText(body);
            clickSend();
        }


        @Override
        protected void load() {
            clickWithJS(composeButton);
        }

        @Override
        protected void isLoaded() {
            assertTrue(webElementIsVisible(sendButton),"Send button appears");
        }
    }
}
