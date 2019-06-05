package com.xcase.selenium.impl.simple.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CalculatorHomePage {
    WebDriver driver;

    By mathCalulatorsLink = By.linkText("Math Calculators");

    public CalculatorHomePage(WebDriver driver) {
        this.driver = driver;
    }

    public void clickMathCalculatorsLink() {
        driver.findElement(mathCalulatorsLink).click();
    }
    
    public void navigateToMathCalculatorsPage() {
        clickMathCalculatorsLink();
    }
}
