#Author: nhan.nguyen@sutrixsolutions.com
#Keywords Summary :Run and updated usercase on JIRA and updated to JIRA and Excel
@runUpdatedJiraandroid
Feature: Check usercase on Jira
  As a user
  I want to verify usercase on Jira
  So that I should see the updated tickets on JIRA and Excel.

  @runUpdatedAllUsercasesOnJiraAndroid
  Scenario: Run and check all usercase android tickets then ouput into excel
    Given I run and check all usercase android tickets on Jira then ouput into "TestCase.xlsx"

  @runUpdatedUsercaseOnJiraAndroid
  Scenario Outline: Run and check some android usercases then output into excel
    Given I run and check some android usercases: "<ID>" and "<SheetName>" then output into: "TestCase.xlsx" on Jira

    Examples: 
      | ID      | SheetName     |
      | AUT-186 | readTestCases |
