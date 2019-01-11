#Author: nhan.nguyen@sutrixsolutions.com
#Keywords Summary :Run and updated usercase on JIRA and updated to JIRA and Excel
@runUpdatedJiraIOsApp
Feature: Check usercase on Jira
  As a user
  I want to verify usercase on Jira
  So that I should see the updated tickets on JIRA and Excel.

  @runUpdatedAllUsercasesOnJiraIOsApp
  Scenario: Run and check all usercase IOs App tickets then ouput into excel
    Given I run and check all usercase IOs App tickets on Jira then ouput into "TestCase.xlsx"

  @runUpdatedUsercaseOnJiraIOsApp
  Scenario Outline: Run and check some IOs App usercases then output into excel
    Given I run and check some IOs App usercases: "<ID>" and "<SheetName>" then output into: "TestCase.xlsx" on Jira

    Examples: 
      | ID      | SheetName     |
      | AUT-187 | readTestCases |
