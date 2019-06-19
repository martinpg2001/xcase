package com.xcase.test.selenium.impl.simple.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CalculatorHomePage {
    WebDriver driver;

    By mathCalculatorsLink = By.linkText("Math Calculators");

    public CalculatorHomePage(WebDriver driver) {
        this.driver = driver;
    }

    public void clickMathCalculatorsLink() {
        driver.findElement(mathCalculatorsLink).click();
    }
    
    public void navigateToMathCalculatorsPage() {
        clickMathCalculatorsLink();
    }
}
