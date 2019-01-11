#Author: nhan.nguyen@sutrixsolutio.com
#Keywords Summary : signUp to eCommercial android app
@signup
Feature: Sign Up to eCommercial android app

  @signupECom
  Scenario: Sign Up to eCommercial app
    Given open app_android
    Then I click on sign up "signUp"
    When I input into "email" with "nhannnguyen@sutrixsolution.com"
    And I input into "password" with "nhan1"
    And I input into "shopName" with "nhan1"
    And I click "createStore"
    Then I wait for loading
    And I input into "fName" with "Nhan"
    And I input into "lName" with "Nguyen"
    And I input into "address1" with "01 Bach Dang"
    And I input into "address2" with "Ward 2, Tan Binh Dist"
    And I input into "postalCode" with "70000"
    And I input into "phone" with "0908599865"
    Then I click "submit"
