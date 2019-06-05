package com.xcase.selenium;

import com.xcase.selenium.constant.SeleniumConstant;
import com.xcase.selenium.impl.simple.core.SeleniumConfigurationManager;
import com.xcase.selenium.impl.simple.pages.CalculatorHomePage;
import com.xcase.selenium.impl.simple.pages.MathCalculatorsPage;
import com.xcase.selenium.impl.simple.pages.PercentageCalculatorPage;

import java.lang.invoke.MethodHandles;
import java.util.concurrent.TimeUnit;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class SeleniumApplication {

    /**
     * log4j logger.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        WebDriver driver = null;
        String webDriver = SeleniumConfigurationManager.getConfigurationManager().getLocalConfig().getProperty(SeleniumConstant.LOCAL_WEBDRIVER);
        switch(webDriver) {
            case "ChromeDriver":
                driver = new ChromeDriver();
                break;
            case "FirefoxDriver":
                driver = new FirefoxDriver();
                break;
            case "MSEdgeDriver":
                driver = new EdgeDriver();
                break;
            default:
                LOGGER.warn("unrecognized webDriver " + webDriver);
        }
        
        //Puts an Implicit wait, Will wait for 10 seconds before throwing exception
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        
        //Launch website
        driver.navigate().to("http://www.calculator.net/");
        
        //Maximize the browser
        driver.manage().window().maximize();
        
        //Use POM to execute test
        CalculatorHomePage calculatorHomePage = new CalculatorHomePage(driver);
        calculatorHomePage.navigateToMathCalculatorsPage();
        LOGGER.debug("navigated to MathCalculatorsPage");
        MathCalculatorsPage mathCalculatorsPage = new MathCalculatorsPage(driver);
        mathCalculatorsPage.navigateToPercentageCalculatorPage();
        LOGGER.debug("navigated to PercentageCalculatorPage");
        PercentageCalculatorPage percentageCalculatorPage = new PercentageCalculatorPage(driver);
        percentageCalculatorPage.calculatePercentage("33", "16");
        String result = percentageCalculatorPage.getResult();
        
        // Log the result
        LOGGER.debug("The result is " + result);
        
        //Close the Browser.
        driver.close();
    }
}
