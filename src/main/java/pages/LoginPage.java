package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;


import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import static pages.locators.LoginPage.*;
import static pages.locators.MailPage.INBOX;

public class LoginPage extends BasePage<LoginPage> {

    private static final String URL = System.getProperty("env");

    @FindBy(xpath = LOGIN_HEADING_TEXT)
    WebElement headingText;

    @FindBy(xpath = LOGIN_INPUT)
    WebElement loginInput;

    @FindBy(xpath = LOGIN_NEXT)
    WebElement loginButton;


    public LoginPage(WebDriver beanDriver) {
        super(beanDriver);
    }

    @Override
    protected void load() {
        refreshPage();
    }

    @Override
    protected void isLoaded() {
        assertEquals(headingText.getText(),"Sign in","Heading text exists and valid");
        assertTrue(webElementIsVisible(loginInput),"Login input is visible");
        assertTrue(webElementIsVisible(loginButton),"Next button is visible");
    }

    @Step("Navigating Login Page")
    public LoginPage navigateLogin() {
        navigateUrl(URL);
        return new LoginPage(driver).get();
    }

    @Step("Fill valid login: {0}")
    public void fillEmailLoginAndProceedNext(String loginValue) {
        setLoginValue(loginValue);
        clickNextButton();
    }

    @Step("Fill valid password: {0}")
    public MailPage fillPasswordAndSubmit(String passwordValue){
        setPasswordValue(passwordValue);
        submitPassword();

        return new MailPage(driver).get();
    }

    public void setLoginValue(String loginValue){
        typeText(loginInput,loginValue);
    }

    public void setPasswordValue(String passwordValue){
        typeText(driver.findElement(By.xpath(PASSWORD_INPUT)) , passwordValue);
    }

    public void clickNextButton(){
        clickIfVisible(loginButton);
    }

    public void submitPassword(){
        clickIfVisible(driver.findElement(By.xpath(PASSWORD_NEXT)));
        waitElementToBeVisible(INBOX);
    }
}
