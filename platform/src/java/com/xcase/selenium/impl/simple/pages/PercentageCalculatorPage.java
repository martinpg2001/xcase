package com.xcase.selenium.impl.simple.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class PercentageCalculatorPage {
    WebDriver driver;
    
    By firstField = By.id("cpar1");
    
    By secondField = By.id("cpar2");
    
    By resultField = By.xpath(".//*[@id = 'content']/p[2]/font/b");
    
    By calculateButton = By.xpath(".//*[@id = 'content']/table/tbody/tr[2]/td/input[2]");
    
    public PercentageCalculatorPage(WebDriver driver) {
        this.driver = driver;
    }
    
    public void setFirstValue(String firstValue) {
        driver.findElement(firstField).sendKeys(firstValue);
    }
   
    public void setSecondValue(String secondValue) {
        driver.findElement(secondField).sendKeys(secondValue);
    }

    public void clickCalculate() {
        driver.findElement(calculateButton).click();
    }
   
    public void calculatePercentage(String firstValue, String secondValue) {
        this.setFirstValue(firstValue);
        this.setSecondValue(secondValue);
        this.clickCalculate();        
    }
   
    public String getResult() {
        String result = driver.findElement(resultField).getText();
        return result;
    }

    public void clearFirstField() {
        driver.findElement(firstField).clear();
    }

    public void clearSecondField() {
        driver.findElement(secondField).clear();
    }

    public String getResult(String firstParameter, String secondParameter) {
        clearFirstField();
        clearSecondField();
        calculatePercentage(firstParameter, secondParameter);
        return getResult();
    }
}
