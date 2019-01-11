#Author: nhan.nguyen@sutrixsolution.com
#Keywords Summary :this feature is run testcase just on excel, not using JIRA
@runtestcaseexcelAndroidIOs
Feature: Run testcase just on excel, not using JIRA
  As a user
  I want to run my testcase on excel but not using JIRA
  Ao that I can see the updated report on this excel

  @runtestcaseexcelAndroidIOs
  Scenario Outline: Run testcase just on excel
    Given I open android IOs excel file "<filename>" with sheetname as "<sheetname>"
    When I run testcase in step as android IOs

    Examples: 
      | filename       | sheetname     |
      | TestCase.xlsx | readTestCases |
