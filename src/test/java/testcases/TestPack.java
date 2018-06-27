package testcases;


import io.qameta.allure.*;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.MailPage;

import static org.testng.Assert.assertTrue;


@Listeners({ TestBase.class })
public class TestPack extends TestBase {

    LoginPage loginPage;
    MailPage mailPage;
    MailPage.MailForm mailForm;
    @Value("${login}")
    String login;
    @Value("${password}")
    String password;

    @Story("Login Story")
    @Feature("Login to account")
    @Description("Perform login to e-mail gmail.com and check that we are inside of test mail")
    @Severity(SeverityLevel.NORMAL)
    @Test(priority = 0, description = "Test Login to account")
    public void testLogin(){
        loginPage = navigateToLoginPage();
        loginPage.fillEmailLoginAndProceedNext(login);
        mailPage = loginPage.fillPasswordAndSubmit(password);

    }

    @Story("Send Email Story")
    @Feature("Send Email")
    @Description("Write a letter to yourself and verify successful sending mail")
    @Severity(SeverityLevel.NORMAL)
    @Test(priority = 1, description = "Test Send Email")
    public void testSendEmail(){
        String body = "Body_"+ RandomStringUtils.random(6, true, true);
        String subject = "Subject_"+ RandomStringUtils.random(6, true, true);
        String email = new StringBuilder(login).append("@gmail.com").toString();

        mailForm = mailPage.raiseMailCompose();
        mailForm.sendMail(email,subject,body);
        mailPage.openInbox();
        int messagesReceived = Integer.valueOf(mailPage.getAmountOfReceivedMails());
        assertTrue(messagesReceived>0,"Message was delivered to Inbox");
        mailPage.openMailByBodyPart(body);
        assertTrue(mailPage.checkOpenedEmailBody(body),"Valid email was opened");
    }


    @Story("Remove Email Story")
    @Feature("Remove Email")
    @Description("Remove from the list of incoming mail and check the successful removal")
    @Severity(SeverityLevel.NORMAL)
    @Test(priority = 2, description = "Test Email Removal")
    public void testEmailRemoval(){
        mailPage.openInbox();
        mailPage.removeLastReceivedMail();
        assertTrue(mailPage.isInboxEmpty(),"Message was removed - Inbox is empty");
    }

}
