#Author: nhan.nguyen@sutrixsolution.com
#Keywords Summary :this feature is run testcase just on excel, not using JIRA
@runtestcaseexcel
Feature: Run testcase just on excel, not using JIRA
  As a user
  I want to run my testcase on excel but not using JIRA
  Ao that I can see the updated report on this excel

  @runtestcaseexcelAndroid
  Scenario Outline: Run testcase just on excel
    Given I open android excel file "<filename>" with sheetname as "<sheetname>"
    When I run testcase in step as android

    Examples: 
      | filename       | sheetname     |
      | TestCase.xlsx | readTestCases |
