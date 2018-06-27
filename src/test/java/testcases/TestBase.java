package testcases;

import com.google.common.io.Files;
import io.qameta.allure.Attachment;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import pages.LoginPage;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;


@ContextConfiguration(locations = {"classpath*:bean.xml"})
public class TestBase extends AbstractTestNGSpringContextTests implements ITestListener {

    public static Logger logger = Logger.getLogger(TestBase.class);

    @Autowired
    WebDriver initWebDriver;

    public LoginPage navigateToLoginPage(){
        return new LoginPage(initWebDriver).navigateLogin();
    }


    @BeforeMethod
    public void beforeTestMethod(Method method){
        logger.info("|Going to start '"+ method.getName().toUpperCase()+"' method |" );
    }

    @AfterMethod
    public void afterTestMethod(Method method){
        logger.info("|Finishing '"+ method.getName().toUpperCase()+"' method |" );
    }

    @Override
    public void onTestStart(ITestResult iTestResult) {
        logger.info("| Starting Test "+iTestResult.getMethod().getMethodName()+" |");
    }

    @Override
    public void onTestSuccess(ITestResult iTestResult) {
        logger.info("| Test Passed |");
    }

    @Override
    public void onTestFailure(ITestResult iTestResult) {
        logger.warn("| Test Failed |");
    }

    @Override
    public void onTestSkipped(ITestResult iTestResult) {
        logger.warn("| Test Skipped |");
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {
        logger.info("| Test partly failed }");
    }

    @Override
    public void onStart(ITestContext iTestContext) {
        logger.info(" Started :");

    }

    @Override
    public void onFinish(ITestContext iTestContext) {
        initWebDriver.quit();
        logger.info(" Finished :");

    }

    @Attachment(type = "image/png")
    public byte[] screenshot() throws IOException {

        File screenshot = ((TakesScreenshot) initWebDriver).getScreenshotAs(OutputType.FILE);
        try {
            String picName = "screen_" + RandomStringUtils.random(8, true, true);
            FileUtils.copyFile(screenshot, new File("src/target/classes/" + picName + ".jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  Files.toByteArray(screenshot);
    }
}
