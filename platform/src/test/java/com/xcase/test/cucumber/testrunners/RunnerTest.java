package com.xcase.test.cucumber.testrunners;

import org.junit.runner.RunWith;
import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import cucumber.api.junit.*;

@RunWith(Cucumber.class)
@CucumberOptions(features="src/test/java/com/xcase/test/cucumber/features/CalculatorTest.feature",glue= {"com.xcase.test.cucumber.stepdefinitions"})
public class RunnerTest {

}
