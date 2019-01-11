#Author: nhan.nguyen@sutrixsolutions.com
#Keywords Summary : Create UserCases on JIRA
@CreateUsercasesWindowsAndroid
Feature: Create UserCases on JIRA
  I want to create user cases on JIRA by using JIRA REST API

  @CreateUsercaseOnJIRAWindowsAndroid
  Scenario: Create UserCases on JIRA
    When I read contents on android sheet name: "readTestCases" of "TestCase.xlsx" file then create UserCases on Jira
