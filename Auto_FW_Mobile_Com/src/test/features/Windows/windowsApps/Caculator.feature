#Author: nhan.nguyen@sutrixsolutions.com
#Keywords Summary : test functions for caclulator windows

@add
Feature: Test function for calculator windows
  I want to test function for calculator windows

  @add1
  Scenario: Test add function
    Given I open windows app
    When I perform function add
    Then I validate the outcomes