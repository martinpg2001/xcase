package com.xcase.selenium.impl.simple.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class MathCalculatorsPage {
    WebDriver driver;
    
    @FindBy(linkText="Percentage Calculator")
    WebElement  percentageCalculatorLink;
    
    public MathCalculatorsPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
    
    public void clickPercentageCalculatorLink() {
        percentageCalculatorLink.click();
    }
    
    public void navigateToPercentageCalculatorPage() {
        clickPercentageCalculatorLink();
    }
}
