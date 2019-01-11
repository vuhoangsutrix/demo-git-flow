import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import support.utils.BaseTest;


/**
 * Here runner total for all scenario, user run with replace or add tags of
 * scenario
 * 
 * @author
 * 
 * To create User cases on excel to JIRA use:
 *    CreateUsercaseOnJIRA
 * 
 * To run automation user cases on JIRA
 * 1. run some of the User cases please use:
 *    CheckSomeUsercasesOnJira
 * 
 * 2. run all User cases please use:
 *    CheckTicketOnJira
 * 
 * To run automation user cases on Excel
 * 1. run some User cases please use:
 *    CheckSomeUsercasesOnExcel
 * 
 * 2. run all User cases please use:
 *    CheckAllUsercasesOnExcel
 *    
 *    getMaster
 *    getActualAndCompareMaster
 *    
 *    MetaTag
 *    TestCopy
 *    CheckURLs
 *    CheckImages
 *    CheckURLsOnMigrationAndCapture
 *    		"support.utils.listener.ExtentCucumberFormatter:"
 *    CheckAuthenURLOnExcel 
 *    openApp
 *    runtestcaseexcel1
 *    CreateUsercaseOnJIRAWindowsApp
 *    runUpdatedAllUsercasesOnExcel
 *    runUpdatedAllUsercasesOnJira
 *    runUpdatedUsercaseOnJira
 *    signupECom
 *    loginECom
 *    runtestcaseexcelAndroid
 *    runUpdatedAllUsercasesOnExcelAndroid
 *    runUpdatedAllUsercasesOnJiraAndroid
 *    runtestcaseexcelIOsApp
 *    CreateUsercaseOnJIRAIOsApp
 *    runtestcaseexcelAndroidIOs
 */
@RunWith(Cucumber.class)
@CucumberOptions(plugin = { "pretty", "html:target/cucumber","json:target/cucumber.json",
		"support.utils.listener.ExtentCucumberFormatter:"}
							, features = {"src/test/features"}
							, glue = {"hooks","com/qa/steps"}
							,tags = {"@runUpdatedUsercaseOnExcel"}
)
public class TestRunner extends BaseTest{
	
	
}
