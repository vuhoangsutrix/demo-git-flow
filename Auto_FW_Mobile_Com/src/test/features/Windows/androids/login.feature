#Author: nhan.nguyen@sutrixsolutio.com
#Keywords Summary : login to eCommercial android app

@login
Feature: login to eCommercial android app

  @loginECom
  Scenario: Login to eCommercial app
    Given Open app_android
    Then I click on "login"
    When I input into "loginEmail" with "nhan.nguyen@sutrixsolution.com"
    And I input into "loginPw" with "nhan1234"
    Then I click on "loginBtn"
    Then I wait for loading
    And I should see "alertToolbar" with "A few final details"