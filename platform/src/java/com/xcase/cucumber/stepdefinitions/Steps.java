package com.xcase.cucumber.stepdefinitions;

import static org.junit.Assert.assertEquals;

import java.lang.invoke.MethodHandles;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;      
import org.openqa.selenium.WebDriver;       
import org.openqa.selenium.firefox.FirefoxDriver;

import com.xcase.cucumber.impl.simple.pages.CalculatorHomePage;
import com.xcase.cucumber.impl.simple.pages.MathCalculatorsPage;
import com.xcase.cucumber.impl.simple.pages.PercentageCalculatorPage;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;      
import cucumber.api.java.en.Then;       
import cucumber.api.java.en.When;       

public class Steps {
    /**
     * log4j logger.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    
    WebDriver driver;           
    
    @Given("^Open the Firefox browser and access the main Calculators page$")                 
    public void open_the_Firefox_browser_and_access_the_main_Calculators_page() throws Throwable {   
       System.setProperty("webdriver.gecko.driver", "D:\\xcase\\xcase\\platform\\lib\\geckodriver.exe");                    
       driver= new FirefoxDriver();                 
       driver.manage().window().maximize();         
       driver.get("http://www.calculator.net/");
       CalculatorHomePage calculatorHomePage = new CalculatorHomePage(driver);
       calculatorHomePage.navigateToMathCalculatorsPage();
       LOGGER.debug("navigated to MathCalculatorsPage");
    }
    
    @And("^Navigate to the Percentage calculator page$")
    public void navigate_to_the_Percentage_calculator_page() throws Throwable {
        MathCalculatorsPage mathCalculatorsPage = new MathCalculatorsPage(driver);
        mathCalculatorsPage.navigateToPercentageCalculatorPage();
        LOGGER.debug("navigated to PercentageCalculatorPage");
    }
    
    @When("I enter the first field value {string}")
    public void i_enter_the_first_field_value(String firstValue) throws Throwable {       
        PercentageCalculatorPage percentageCalculatorPage = new PercentageCalculatorPage(driver);
        percentageCalculatorPage.clearFirstField();
        percentageCalculatorPage.clearSecondField();
        percentageCalculatorPage.setFirstValue(firstValue);
    }
    
    @And("Enter the second field value {string}")
    public void enter_the_second_field_value(String secondValue) throws Throwable {       
        PercentageCalculatorPage percentageCalculatorPage = new PercentageCalculatorPage(driver);
        percentageCalculatorPage.setSecondValue(secondValue);
    }

    @And("^Click Calculate button$")
    public void click_calculate_button() {
        PercentageCalculatorPage percentageCalculatorPage = new PercentageCalculatorPage(driver);
        percentageCalculatorPage.clickCalculate();        
    }
    
    @Then("Verify the result {string}")
    public void verify_the_result(String result) throws Throwable {       
        PercentageCalculatorPage percentageCalculatorPage = new PercentageCalculatorPage(driver);
        String actualResult = percentageCalculatorPage.getResult();
        assertEquals("Result is not expected value", actualResult, result);
        driver.close();
    }
}
