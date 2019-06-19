#Author: martinpg@outlook.com
#Keywords Summary :
#Feature: List of scenarios.
#Scenario: Business rule through list of steps with arguments.
#Given: Some precondition step
#When: Some key actions
#Then: To observe outcomes or validation
#And,But: To enumerate more Given,When,Then steps
#Scenario Outline: List of steps for data-driven as an Examples and <placeholder>
#Examples: Container for s table
#Background: List of steps run before each of the scenarios
#""" (Doc Strings)
#| (Data Tables)
#@ (Tags/Labels):To group Scenarios
#<> (placeholder)
#""
## (Comments)
#Sample Feature Definition Template

@tag
Feature: Check percentage calculator functionality
  This feature checks functionality provided by the Percentage calculator

  @tag1
  Scenario Outline: Test calculation of percentages
    Given Open the Firefox browser and access the main Calculators page
    And Navigate to the Percentage calculator page
    When I enter the first field value <firstvalue>
    And Enter the second field value <secondvalue>
    And Click Calculate button
    Then Verify the result <result> <compare>

    Examples: 
      | name  | firstvalue | secondvalue | result | compare |
      | name1 |     "5"    | "50"        | "2.5"  | "true"    |
      | name2 |     "7"    | "60"        | "4.2"  | "true"    |
      | name2 |     "7"    | "60"        | "5.2"  | "false"   |
