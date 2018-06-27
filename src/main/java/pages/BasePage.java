package pages;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;


import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;


public abstract class BasePage<T extends BasePage<T>> extends LoadableComponent<T> {

    protected static Logger logger = Logger.getLogger(BasePage.class);

    protected WebDriver driver;
    protected WebDriverWait wait;

    public BasePage(WebDriver beanDriver) {
        driver = beanDriver;
        driver.manage().window().maximize();
        PageFactory.initElements(driver, this);
        wait = new WebDriverWait(driver,10);
    }

    public void navigateUrl(String url) {
        logger.info("| Going to navigate " + url + " |");
        driver.get(url);

    }

    public  void quiteAndCloseDriver() {
        driver.quit();
    }

    protected void clearAndType(WebElement inputElement, String inputText) {
        inputElement.clear();
        inputElement.sendKeys(inputText);

    }

    protected void clearFieldNonJS(WebElement element) {
        element.sendKeys(Keys.CONTROL + "A" + Keys.DELETE);
    }

    protected void typeText(WebElement element, String text) {
        element.sendKeys(text);
    }

    protected void click(WebElement clickElement) {
        wait.until(ExpectedConditions.elementToBeClickable(clickElement));
        clickElement.click();
    }

    protected void clickWithAction(WebElement clickElement){
        new Actions(driver).moveToElement(clickElement).click().perform();
    }

    protected void clickIfVisible(WebElement element) {
        wait.until(ExpectedConditions.visibilityOf(element));
        element.click();
    }

    protected void clickWithJS(WebElement element){
        wait.until(ExpectedConditions.visibilityOf(element));
        executeJSCommand("arguments[0].click();", element);

    }

    protected boolean webElementIsEnabled(WebElement element) {
        return element.isEnabled();
    }

    protected boolean webElementIsVisible(WebElement element) {
        return element.isDisplayed();
    }


    protected void waitElementToBeClickable(String xpath) {
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
    }

    protected void waitElementToBeVisible(String xpath) {
        wait.until(ExpectedConditions.visibilityOf(getElementBy(By.xpath(xpath))));
    }

    protected void moveMouse(int horizont,int vertical){
        Actions acts=new Actions(driver);
        acts.moveByOffset(horizont, vertical).build().perform();
    }

    protected void navigatePageBack() {
        driver.navigate().back();
    }

    protected void navigatePageForward() {
        driver.navigate().forward();
    }

    protected void refreshPage() {
        driver.navigate().refresh();
    }

    protected void submit(WebElement element) {
        wait.until(ExpectedConditions.elementToBeClickable(element));
        element.submit();
    }

    protected WebElement getElementBy(By by){
        return driver.findElement(by);
    }

    protected List<WebElement> getElementsBy(By by){
        return driver.findElements(by);
    }



    protected void executeJSCommand(String command) {
        JavascriptExecutor javascript = (JavascriptExecutor) driver;
        javascript.executeScript(command);
    }

    protected void executeJSCommand(String command,WebElement element) {
        JavascriptExecutor javascript = (JavascriptExecutor) driver;
        javascript.executeScript(command,element);
    }


    protected void takeScreenShot() {
        File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try {
            String picName = "screen_" + RandomStringUtils.random(8, true, true);
            FileUtils.copyFile(screenshot, new File("src/target/classes/" + picName + ".jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void waitSeconds(int seconds){
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }



    @Override
    protected void load() {
    }

    @Override
    protected void isLoaded() {
    }

}
