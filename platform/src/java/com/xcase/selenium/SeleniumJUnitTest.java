package com.xcase.selenium;

import static org.junit.Assert.*;

import com.xcase.selenium.constant.SeleniumConstant;
import com.xcase.selenium.impl.simple.core.SeleniumConfigurationManager;
import com.xcase.selenium.impl.simple.pages.CalculatorHomePage;
import com.xcase.selenium.impl.simple.pages.MathCalculatorsPage;
import com.xcase.selenium.impl.simple.pages.PercentageCalculatorPage;

import java.lang.invoke.MethodHandles;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class SeleniumJUnitTest {
    /**
     * log4j logger.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    
    protected static PercentageCalculatorPage percentageCalculatorPage = null;
    
    protected static WebDriver driver = null;
    
    @BeforeClass
    public static void navigateToCalculator() {
        driver = null;
        String webDriver = SeleniumConfigurationManager.getConfigurationManager().getLocalConfig().getProperty(SeleniumConstant.LOCAL_WEBDRIVER);
        LOGGER.debug("webDriver is " + webDriver);
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
        
        //Launch Web site
        driver.navigate().to("http://www.calculator.net/");
        
        //Maximize the browser
        driver.manage().window().maximize();
        
        //Navigate to percentage calculator
        CalculatorHomePage calculatorHomePage = new CalculatorHomePage(driver);
        calculatorHomePage.navigateToMathCalculatorsPage();
        LOGGER.debug("navigated to MathCalculatorsPage");
        MathCalculatorsPage mathCalculatorsPage = new MathCalculatorsPage(driver);
        mathCalculatorsPage.navigateToPercentageCalculatorPage();
        LOGGER.debug("navigated to PercentageCalculatorPage");
        percentageCalculatorPage = new PercentageCalculatorPage(driver);
    }
    
    @Test
    public void test1() {
        percentageCalculatorPage.clearFirstField();
        percentageCalculatorPage.clearSecondField();
        percentageCalculatorPage.calculatePercentage("33", "16");
        String result = percentageCalculatorPage.getResult();
        assertEquals("Result is not expected value", result, "5.28");
    }
    
    @Test
    public void test2() {
        percentageCalculatorPage.clearFirstField();
        percentageCalculatorPage.clearSecondField();
        percentageCalculatorPage.calculatePercentage("30", "16");
        String result = percentageCalculatorPage.getResult();
        assertEquals("Result is not expected value", result, "4.8");
    }
    
    @Test
    public void test3() {
        percentageCalculatorPage.clearFirstField();
        percentageCalculatorPage.clearSecondField();
        percentageCalculatorPage.calculatePercentage("-30", "16");
        String result = percentageCalculatorPage.getResult();
        assertEquals("Result is not expected value", result, "-4.8");
    }
    
    @AfterClass
    public static void closeBrowser() {
        driver.close();
    }
}
