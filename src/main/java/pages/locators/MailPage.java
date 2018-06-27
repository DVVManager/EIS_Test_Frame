package pages.locators;

public class MailPage {
    public static final String INBOX = "//*[contains(@href,'inbox') and contains(@title,'Inbox')]";
    public static final String COMPOSE_BUTTON = "//*[@role='button' and text()='COMPOSE']";
    public static final String MAIL_SUBJECT = "//input[@name='subjectbox' ]";
    public static final String MAIL_TO = "//textarea[@name='to' ]";
    public static final String MAIL_BODY = "//div[@aria-label='Message Body' ]";
    public static final String SEND_BUTTON = "//div[text()='Send']";
    public static  String MAIL_BODY_TEXT = "//div[text()='%s']";
    public static final String LAST_MAIL_CHECKBOX = "//*[@class='UI']//div[@role='checkbox' and position()=1]";
    public static final String DELETE_BUTTON = "//*[@role='button' and @aria-label='Delete']/div";
    public static final String NO_MAILS_LABEL = "//*[@role='tabpanel']//*[contains(text(),'Your Primary tab is empty.')]";
}
