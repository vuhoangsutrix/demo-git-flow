#Author: nhan.nguyen@sutrixsolutions.com
#Keywords Summary :Run and updated usercase on JIRA and updated to JIRA and Excel
@runUpdatedJira
Feature: Check usercase on Jira
  As a user
  I want to verify usercase on Jira
  So that I should see the updated tickets on JIRA and Excel.

  @runUpdatedAllUsercasesOnJira
  Scenario: Run and check all usercase tickets then ouput into excel
    Given I run and check all usercase tickets on Jira then ouput into "TestCase.xlsx"

  @runUpdatedUsercaseOnJira
  Scenario Outline: Run and check some usercases then output into excel
    Given I run and check some usercases: "<ID>" and "<SheetName>" then output into: "TestCase.xlsx" on Jira

    Examples: 
      | ID      | SheetName     |
      | AUT-185 | readTestCases |
