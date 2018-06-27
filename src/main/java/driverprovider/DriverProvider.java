package driverprovider;

import io.github.bonigarcia.wdm.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.test.context.ContextConfiguration;
import java.util.concurrent.TimeUnit;




@ContextConfiguration(locations = {"classpath*:bean.xml"})
@Configuration
public class DriverProvider {

    private WebDriver webDriver = null;
    @Value("${browser}")
    private String browserType;

    @Lazy
    @Bean
    protected WebDriver initWebDriver() {
        if (webDriver == null) {
            if(browserType == null) {
                browserType = "FIREFOX";
            }
            webDriver=createWebDriver(DriverManagerType.valueOf(browserType));
            webDriver.manage().timeouts().pageLoadTimeout(10000, TimeUnit.SECONDS);
            webDriver.manage().timeouts().setScriptTimeout(5000, TimeUnit.SECONDS);
            webDriver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        }
        return webDriver;
    }

    private WebDriver createWebDriver(DriverManagerType driverType) {
        WebDriverManager.getInstance(driverType).setup();
        WebDriver driver;
        switch (driverType){
            case CHROME: driver = new ChromeDriver();
            break;
            default: driver = new FirefoxDriver();
        }
        return driver;
    };

}
