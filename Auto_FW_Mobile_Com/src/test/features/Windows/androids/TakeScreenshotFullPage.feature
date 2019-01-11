#Author: nhan.nguyen@sutrixsolutions.com
#Keywords Summary : Get all URLs that client when to take screenshot for fullpage
#and report to file excel
@TakeScreenshotURLs
Feature: Take Screenshot for fullpage
  As I a user
  I want to get all URLs on excel file and run it
  So that I can take screenshot for fullpage.

  @TakeScreenshotOnExcel
  Scenario Outline: Take screenshot for fullpage on excel file
    Given open app_android
    Then I get the file "<filename>" and I verify links on sheet "<sheetname>" and report to excel

    Examples: 
      | filename  | sheetname      |
      | URLs.xlsx | TakeScreenshot |
