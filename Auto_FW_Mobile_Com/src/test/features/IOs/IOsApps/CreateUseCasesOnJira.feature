#Author: nhan.nguyen@sutrixsolutions.com
#Keywords Summary : Create UserCases on JIRA
@CreateUsercasesIOsApp
Feature: Create UserCases on JIRA
  I want to create user cases on JIRA by using JIRA REST API

  @CreateUsercaseOnJIRAIOsApp
  Scenario: Create UserCases on JIRA
    When I read contents on IOs App sheet name: "readTestCases" of "TestCase.xlsx" file then create UserCases on Jira
