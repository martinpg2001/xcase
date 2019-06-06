package com.xcase.appium;

import com.xcase.appium.constant.AppiumConstant;
import com.xcase.appium.impl.simple.core.AppiumConfigurationManager;
import com.xcase.appium.impl.simple.pages.CalculatorHomePage;
import com.xcase.appium.impl.simple.pages.MathCalculatorsPage;
import com.xcase.appium.impl.simple.pages.PercentageCalculatorPage;
import io.appium.java_client.android.AndroidDriver;

import java.io.File;
import java.lang.invoke.MethodHandles;
import java.net.URI;
import java.net.URL;
import java.util.concurrent.TimeUnit;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

public class AppiumApplication {

    /**
     * log4j logger.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            File classpathRoot = new File(".");
            String appDirectory = AppiumConfigurationManager.getConfigurationManager().getLocalConfig()
                    .getProperty(AppiumConstant.LOCAL_APP_DIRECTORY);
            LOGGER.debug("appDirectory is " + appDirectory);
            File appDir = new File(classpathRoot, appDirectory);
            String appFilename = AppiumConfigurationManager.getConfigurationManager().getLocalConfig()
                    .getProperty(AppiumConstant.LOCAL_APP);
            LOGGER.debug("appFilename is " + appFilename);            
            File app = new File(appDir.getCanonicalPath(), appFilename);
            DesiredCapabilities capabilities = new DesiredCapabilities();
            String deviceName = AppiumConfigurationManager.getConfigurationManager().getLocalConfig()
                    .getProperty(AppiumConstant.LOCAL_DEVICE_NAME);
            LOGGER.debug("deviceName is " + deviceName);             
            capabilities.setCapability("deviceName", deviceName);
            capabilities.setCapability("app", app.getAbsolutePath());
            String appPackage = AppiumConfigurationManager.getConfigurationManager().getLocalConfig()
                    .getProperty(AppiumConstant.LOCAL_APP_PACKAGE);
            LOGGER.debug("appPackage is " + appPackage);             
            capabilities.setCapability("appPackage", appPackage);
            String appActivity = AppiumConfigurationManager.getConfigurationManager().getLocalConfig()
                    .getProperty(AppiumConstant.LOCAL_APP_ACTIVITY);
            LOGGER.debug("appActivity is " + appActivity);             
            capabilities.setCapability("appActivity", appActivity);
            AndroidDriver<WebElement> driver = null;
            String webDriver = AppiumConfigurationManager.getConfigurationManager().getLocalConfig()
                    .getProperty(AppiumConstant.LOCAL_WEBDRIVER);
            LOGGER.debug("webDriver is " + webDriver);
            switch (webDriver) {
            case "AndroidDriver":
                driver = new AndroidDriver<WebElement>(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
                break;
            case "ChromeDriver":

                break;
            case "FirefoxDriver":

                break;
            case "MSEdgeDriver":

                break;
            default:
                LOGGER.warn("unrecognized webDriver " + webDriver);
            }

            // Puts an Implicit wait, Will wait for 10 seconds before throwing exception
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

            // Find text on page
            driver.findElementById("text_view_id");
            LOGGER.debug("found element");
            String result = driver.findElementById("text_view_id").getText();

            // Log the result
            LOGGER.debug("The result is " + result);

            // Close the Browser.
            driver.close();
        } catch (Exception e) {
            LOGGER.warn("exception running application: " + e.getMessage());
        }
    }
}
