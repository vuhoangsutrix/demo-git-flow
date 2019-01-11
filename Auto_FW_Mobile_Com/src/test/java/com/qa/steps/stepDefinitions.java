/**
 * 
 */
package com.qa.steps;

import java.io.UncheckedIOException;

import com.qa.IOsAndroid.helperIOsAndroid;
import com.qa.IOsApp.helperIOsApps;
import com.qa.WindowsAndroid.helperWindowsAndroid;
import com.qa.WindowsApps.helperWindowsApps;
import com.servicecorp.core.utils.ReadProperties;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import support.utils.listener.Reporter;

/**
 * @author nhan.nguyen
 *
 */
public class stepDefinitions {
	// this variable is for Android work on Windows platform
	helperWindowsAndroid helperAW = new helperWindowsAndroid();
	// this variable is for Windows Apps work on Windows platform
	helperWindowsApps helperWA = new helperWindowsApps();
	// this variable is for IOS app work on IOS platform
	helperIOsApps helperIOsA = new helperIOsApps();
	// this variable is for Ios Android work on IOS platform
	helperIOsAndroid helperIOsAndroid = new helperIOsAndroid();

	public static Scenario mobScenario;

	@Before
	public void embedScreenshotStep(Scenario scenario) throws InterruptedException {
		
		this.mobScenario = scenario;
	}

	@After
	public void tearDown(Scenario scenario) throws Exception {
		String imagePath = "";
		//System.out.println("[Scenario Name: " + mobScenario.getName()+"status: " + mobScenario.getStatus()+"]");
		if (scenario.isFailed()) {
			System.out.println("[Scenario Name is " + mobScenario.getStatus().toString()+"]");
			if (ReadProperties.getConfig("Platform").equals("androidWin")) {
				System.out.println("Scenario Name with Android Windows");
				imagePath = helperAW.captureExtentReports("FAILED",scenario);
			} else if (ReadProperties.getConfig("Platform").equals("WindowsApp")) {
				System.out.println("Scenario Name with Windows Apps");
				imagePath = helperWA.captureExtentReports("FAILED");
			} else if (ReadProperties.getConfig("Platform").equals("IOSapp")) {
				System.out.println("Scenario Name with IOs app");
				imagePath = helperIOsA.captureExtentReports("FAILED");
			} else if (ReadProperties.getConfig("Platform").equals("androidIOs")) {
				System.out.println("Scenario Name with IOs Android");
				imagePath = helperIOsAndroid.captureExtentReports("FAILED");
			}

			try {
				Reporter.addScreenCaptureFromPath(imagePath);

			} catch (UncheckedIOException e) {
			}
			//mobScenario.getSourceTagNames();

		} else if (!scenario.isFailed() ) {
			System.out.println("[Scenario Name is " + mobScenario.getStatus().toString()+"]");
			if (ReadProperties.getConfig("Platform").equals("androidWin")&& helperWindowsAndroid.errorMessage.length()<0) {
				System.out.println("Scenario Name with Android Windows");
				imagePath = helperAW.captureExtentReports("PASSED",scenario);
			} else if (ReadProperties.getConfig("Platform").equals("WindowsApp") && helperWindowsApps.errorMessage.length()<0) {
				System.out.println("Scenario Name with Windows Apps");
				imagePath = helperWA.captureExtentReports("PASSED");
			} else if (ReadProperties.getConfig("Platform").equals("IOSapp")) {
				System.out.println("Scenario Name with IOs app");
				imagePath = helperIOsA.captureExtentReports("PASSED");
			} else if (ReadProperties.getConfig("Platform").equals("androidIOs")) {
				System.out.println("Scenario Name with IOs Android");
				imagePath = helperIOsAndroid.captureExtentReports("PASSED");
			}
			try {
				Reporter.addScreenCaptureFromPath(imagePath);
			} catch (UncheckedIOException e) {
			}
		}

	}

	/*
	 * these Steps definitions is for Windows Android
	 */
	
	@Given("^Open app_android$")
	public void open_app_android() throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		System.out.println("[i am open android app]");
		helperAW.appSetting();
	}

	@Then("^I get the file \"([^\"]*)\" and I verify links on sheet \"([^\"]*)\" and report to excel$")
	public void i_get_the_file_and_I_verify_links_on_sheet_and_report_to_excel(String filename, String sheetname) throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
	    helperAW.takeScreenshotFullpage(filename,sheetname);
	}
	
	@When("^I goto the page$")
	public void i_goto_the_page() throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		helperAW.testLogin();
	}
	
	@Then("^I click on sign up \"([^\"]*)\"$")
	public void i_click_on_sign_up(String element) throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
		helperAW.clickOn(element);
	}

	@When("^I input into \"([^\"]*)\" with \"([^\"]*)\"$")
	public void i_input_into_with(String element, String value) throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
		helperAW.inputInto(element,value);
	}

	

	@When("^I click on \"([^\"]*)\"$")
	public void i_click(String element) throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
		helperAW.clickOn(element);
	}
	@Then("^I wait for loading$")
	public void i_wait_for_loading() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
		helperAW.loading();
	}
	@Then("^I should see \"([^\"]*)\" with \"([^\"]*)\"$")
	public void i_should_see_with(String element, String value) throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
	    helperAW.shouldSee(element,value);
	}	
	// ----------------Run testcase in excel with Android on windows----------------
		@Given("^I open android excel file \"([^\"]*)\" with sheetname as \"([^\"]*)\"$")
		public void i_open_excel_file_with_sheetname_as_Android(String filename, String sheetname) throws Throwable {
		    // Write code here that turns the phrase above into concrete actions
			helperAW.openExcel(filename,sheetname);
		}

		@When("^I run testcase in step as android$")
		public void i_run_testcase_in_step_as_android() throws Throwable {
		    // Write code here that turns the phrase above into concrete actions
			helperAW.runTestcaseOnExcel();
		}

		// ----------------end testcase in excel with Android on windows----------------
		//-----------------create user case on JIRA with Windows App-------------
		@When("^I read contents on android sheet name: \"([^\"]*)\" of \"([^\"]*)\" file then create UserCases on Jira$")
		public void i_read_contents_on_android_sheet_name_of_file_then_create_UserCases_on_Jira(String sheetName, String excelName)
				throws Throwable {
			helperAW.createUsercaseAndroid(sheetName, excelName);
		}
		//-----------------End create user case on JIRA with Windows App---------
		//-----------------Run usercase on Excel and updated to both with Android Windows-------------
		@Given("^I run and check all usercases on android Excel and update into \"([^\"]*)\" file and Jira$")
		public void i_run_and_check_all_usercases_on_android_Excel_and_update_into_file_and_Jira(String fileName)
				throws Throwable {
			// Write code here that turns the phrase above into concrete actions
			helperAW.runCheckUsercaseOnExcelAndroid(fileName);
		}
		@Given("^I run and check usercases on android: \"([^\"]*)\" on sheet \"([^\"]*)\" on \"([^\"]*)\" Excel$")
		public void i_run_and_check_usercases_on_android_on_sheet_on_Excel(String jiraID, String sheetName, String fileName)
				throws Throwable {
			// Write code here that turns the phrase above into concrete actions
			helperAW.runCheckSomeUsercaseOnExcelAndroid(jiraID, sheetName, fileName);
		}
		//-----------------End Run usercase on Excel and updated to both with Android Windows---------
		//-----------------Run usercase on JIRA and updated to both-------------
		@Given("^I run and check some android usercases: \"([^\"]*)\" and \"([^\"]*)\" then output into: \"([^\"]*)\" on Jira$")
		public void i_run_and_check_some_android_usercases_and_then_output_into_on_Jira(String jiraID, String sheetName,
				String filename) throws Throwable {
			// Write code here that turns the phrase above into concrete actions
			helperAW.runCheckSomeCaseAndroidOnJira(jiraID, sheetName, filename);
		}

		@Given("^I run and check all usercase android tickets on Jira then ouput into \"([^\"]*)\"$")
		public void i_run_and_check_all_usercase_android_tickets_on_Jira_then_ouput_into(String filename) throws Throwable {
			// Write code here that turns the phrase above into concrete actions
			System.out.println("[i am here]");
			helperAW.runCheckAllCaseAndroidOnJira(filename);
		}
		//-----------------End Run usercase on JIRA and updated to both---------
		
//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	/*
	 * these Steps definitions is for Windows Apps
	 */
	// ----------------Test Add Function----------------
	@Given("^I open windows app$")
	public void i_open_windows_app() throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		helperWA.appSetting();
	}

	@When("^I perform function add$")
	public void i_perform_function_add() throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		helperWA.testAddFunction();
	}

	@Then("^I validate the outcomes$")
	public void i_validate_the_outcomes() throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		helperWA.addResultText();
	}
	// ----------------End Test Add Function----------------
	
	// ----------------Run testcase in excel----------------
	@Given("^I open excel file \"([^\"]*)\" with sheetname as \"([^\"]*)\"$")
	public void i_open_excel_file_with_sheetname_as(String filename, String sheetname) throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
		helperWA.openExcel(filename,sheetname);
	}

	@When("^I run testcase in step$")
	public void i_run_testcase_in_step() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
	    helperWA.runTestcaseOnExcel();
	}

	// ----------------end testcase in excel----------------
	//-----------------create user case on JIRA with Windows App-------------
	@When("^I read contents on sheet name: \"([^\"]*)\" of \"([^\"]*)\" file then create UserCases on Jira$")
	public void i_read_contents_on_sheet_name_of_file_then_create_UserCases_on_Jira(String sheetName, String excelName)
			throws Throwable {
		helperWA.createUsercase(sheetName, excelName);
	}
	//-----------------End create user case on JIRA with Windows App---------
	//-----------------Run usercase on Excel and updated to both with Windows App-------------
	@Given("^I run and check all usercases on Excel and update into \"([^\"]*)\" file and Jira$")
	public void i_run_and_check_all_usercases_on_Excel_and_update_into_file_and_Jira(String fileName)
			throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		helperWA.runCheckUsercaseOnExcel(fileName);
	}
	@Given("^I run and check usercases: \"([^\"]*)\" on sheet \"([^\"]*)\" on \"([^\"]*)\" Excel$")
	public void i_run_and_check_usercases_on_sheet_on_Excel(String jiraID, String sheetName, String fileName)
			throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		helperWA.runCheckSomeUsercaseOnExcel(jiraID, sheetName, fileName);
	}
	//-----------------End Run usercase on Excel and updated to both with Windows App---------
	//-----------------Run usercase on JIRA and updated to both-------------
	@Given("^I run and check some usercases: \"([^\"]*)\" and \"([^\"]*)\" then output into: \"([^\"]*)\" on Jira$")
	public void i_run_and_check_some_usercases_and_then_output_into_on_Jira(String jiraID, String sheetName,
			String filename) throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		helperWA.runCheckSomeCaseOnJira(jiraID, sheetName, filename);
	}

	@Given("^I run and check all usercase tickets on Jira then ouput into \"([^\"]*)\"$")
	public void i_run_and_check_all_usercase_tickets_on_Jira_then_ouput_into(String filename) throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		helperWA.runCheckAllCaseOnJira(filename);
	}
	//-----------------End Run usercase on JIRA and updated to both---------
	/*
	 * these Steps definitions is for IOs Apps
	 */
	
	@Given("^I open IOs App$")
	public void i_open_IOs_App() throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		System.out.println("[I am here!]");
		helperIOsA.appSetting();
	}

	@Then("^I perform for Alert Views$")
	public void i_perform_for_Alert_Views() throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		helperIOsA.testAlertViews();
	}
	@Then("^I perform for Steppers$")
	public void i_perform_for_Steppers() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
	    helperIOsA.testSteppers();
	}

	@Then("^I perform Picker View$")
	public void i_perform_Picker_View() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
	    helperIOsA.testPickerViews();
	}
	
	@Given("^I open IOs App excel file \"([^\"]*)\" with sheetname as \"([^\"]*)\"$")
	public void i_open_IOs_App_excel_file_with_sheetname_as(String filename, String sheetname) throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
	    helperIOsA.openExcel(filename,sheetname);
	}

	@When("^I run testcase in step as IOs App$")
	public void i_run_testcase_in_step_as_IOs_App() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
	    helperIOsA.runTestcaseOnExcel();
	}
	@When("^I read contents on IOs App sheet name: \"([^\"]*)\" of \"([^\"]*)\" file then create UserCases on Jira$")
	public void i_read_contents_on_IOs_App_sheet_name_of_file_then_create_UserCases_on_Jira(String sheetName, String excelName) throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
	    helperIOsA.createUsercaseIOsApp(sheetName, excelName);
	}
	@Given("^I run and check all usercases on IOs App Excel and update into \"([^\"]*)\" file and Jira$")
	public void i_run_and_check_all_usercases_on_IOs_App_Excel_and_update_into_file_and_Jira(String fileName) throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
		helperIOsA.runCheckUsercaseOnExcelIOsApp(fileName);
	}

	@Given("^I run and check usercases on IOsApp: \"([^\"]*)\" on sheet \"([^\"]*)\" on \"([^\"]*)\" Excel$")
	public void i_run_and_check_usercases_on_IOsApp_on_sheet_on_Excel(String jiraID, String sheetName, String fileName) throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
		helperIOsA.runCheckSomeUsercaseOnExcelIOsApp(jiraID, sheetName, fileName);
	}
	@Given("^I run and check all usercase IOs App tickets on Jira then ouput into \"([^\"]*)\"$")
	public void i_run_and_check_all_usercase_IOs_App_tickets_on_Jira_then_ouput_into(String filename) throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
		helperIOsA.runCheckAllCaseOnJiraIOsApp(filename);
	}

	@Given("^I run and check some IOs App usercases: \"([^\"]*)\" and \"([^\"]*)\" then output into: \"([^\"]*)\" on Jira$")
	public void i_run_and_check_some_IOs_App_usercases_and_then_output_into_on_Jira(String jiraID, String sheetName, String filename) throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
		helperIOsA.runCheckSomeCaseOnJiraIOSApp(jiraID, sheetName, filename);
	}
//===============================================================================================================
	/*
	 * these Steps definitions is for IOS Android
	 */
	
	@Given("^I open IOs android$")
	public void i_open_IOs_android() throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		helperIOsAndroid.appSetting();
	}
	
	@Given("^I open android IOs excel file \"([^\"]*)\" with sheetname as \"([^\"]*)\"$")
	public void i_open_android_IOs_excel_file_with_sheetname_as(String filename, String sheetname) throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
		helperIOsAndroid.openExcel(filename, sheetname);
	}

	@When("^I run testcase in step as android IOs$")
	public void i_run_testcase_in_step_as_android_IOs() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
		helperIOsAndroid.runTestcaseOnExcel();
	}
}
