package com.xcase.cucumber.testrunners;

import org.junit.runner.RunWith;
import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import cucumber.api.junit.*;

@RunWith(Cucumber.class)
@CucumberOptions(features="src/java/com/xcase/cucumber/features/CalculatorTest.feature",glue= {"com.xcase.cucumber.stepdefinitions"})
public class Runner {

}
