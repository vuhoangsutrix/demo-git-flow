#Author: nhan.nguyen@sutrixsolutions.com
#Keywords Summary :Run and updated usercase on Excel and updated to JIRA
@runUpdatedExcelAndroid
Feature: Check usercase on Excel
  As a user
  I want to verify usercase on Excel
  So that I could see the updated on excel and JIRA.

  @runUpdatedAllUsercasesOnExcelAndroid
  Scenario: Check all usercases then update into excel file and Jira
    Given I run and check all usercases on android Excel and update into "TestCase.xlsx" file and Jira

  @runUpdatedUsercaseOnExcelAndroid
  Scenario Outline: Check some usercases then report into excel and Jira
    Given I run and check usercases on android: "<ID>" on sheet "<Sheet>" on "<Name>" Excel

    Examples: 
      | ID      | Sheet         | Name          |
      | AUT-186 | readTestCases | TestCase.xlsx |
