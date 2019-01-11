/**
 * 
 */
package com.qa.IOsApp;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFFormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONObject;
import org.junit.Assert;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;

import com.qa.common.Constants;
import com.servicecorp.core.server.Server;
import com.servicecorp.core.utils.DriverUtils;
import com.servicecorp.core.utils.OperationUtils;
import com.servicecorp.core.utils.ReadProperties;

import gherkin.deps.net.iharder.Base64;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import net.arnx.jsonic.JSON;
import com.servicecorp.core.apis.ParamsPostMethod;
import com.servicecorp.core.apis.APIsJIRA;

/**
 * @author nhan.nguyen
 *
 */
public class helperIOsApps {
	public static IOSDriver<IOSElement> driver;

	public static FileInputStream fis;
	public static FileOutputStream fos;
	static XSSFWorkbook workbook;
	static XSSFSheet sheet;
	static XSSFCell cell;
	static XSSFRow row;

	public static String messageAlert = "";
	public static String errorMessage = "";
	private static WebElement CalculatorResult = null;
	//public static boolean bError = false;
	//Jira
	public String userJira = ReadProperties.getConfig("jiraUserName");
	public String pwJira = ReadProperties.getConfig("jiraPassword");

	public static SimpleDateFormat df = new SimpleDateFormat("HH:mm dd_MMM_YYYY");
	public static Date da = new Date();
	public static String timeToCheck = df.format(da);
	

	
	public String captureExtentReports(String status) {
		String dest = "";
		String dateFormat = new SimpleDateFormat("yyyyMMdd_hhmmss").format(Calendar.getInstance().getTime());
		String fileName = "TS_" + status + "_" + dateFormat + ".jpg";
		try {
			File source = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			if (status == "FAILED") {
				dest = Constants.ROOT_FAIL + fileName;
				
				File destination = new File(dest);
				FileUtils.copyFile(source, destination);
				dest =  Constants.ROOT_EXTENT_FAIL+ fileName;
			} else {
				dest = Constants.ROOT_PASS + fileName;
				
				File destination = new File(dest);
				FileUtils.copyFile(source, destination);
				dest = Constants.ROOT_EXTENT_PASS + fileName;
			}
		} catch (IOException e) {
			System.out.println("Error captureExtentReports : " + e.getMessage());
		}
		return dest;
	}

	/*
	 * This is the main method which makes use of Open application method.
	 * 
	 * @author Nhan Nguyen
	 * 
	 * @version 1.0
	 * 
	 * @since 14-12-2018
	 * 
	 * @param args Unused.
	 * 
	 * @return Nothing.
	 * 
	 * @exception MalformedURLException On input error.
	 * 
	 * @see MalformedURLException
	 */
	public void appSetting() throws MalformedURLException {
		// Setup port for Appium server
		System.out.println("[Appium port: "+ReadProperties.getConfig("portIOs")+"]");
		Server server= OperationUtils.getServer(OperationUtils.ServerType.APP, ReadProperties.getConfig("portIOs"));
		//Start Appium server
		server.startServer();
		//Setup for IOS Driver
		String platformVersion=ReadProperties.getConfig("platformVersionIOs");
		System.out.println("[platform Version: "+ReadProperties.getConfig("platformVersionIOs")+"]");
		String UDID=ReadProperties.getConfig("UDID");
		System.out.println("[UDID: "+ReadProperties.getConfig("UDID")+"]");
		String deviceName=ReadProperties.getConfig("deviceNameIOs");
		System.out.println("[device Name: "+ReadProperties.getConfig("deviceNameIOs")+"]");
		String app=Constants.ROOT_PATH + ReadProperties.getConfig("appIOs");
		System.out.println("[App Name: "+app+"]");
		try{
		driver = DriverUtils.capabilities(server,platformVersion,UDID,deviceName,app);
		}catch (Exception e) {
	        e.printStackTrace();
	    }
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
	}
	
	public void testAlertViews(){
		driver.findElementByAccessibilityId("Alert Views").click();
		driver.findElementByXPath("//*[@value='Text Entry']").click();
		driver.findElementByClassName("XCUIElementTypeTextField").sendKeys("Hello!");
		driver.findElementByName("OK").click();
		driver.navigate().back();
	}
	
	public void testSteppers(){
		driver.findElementByAccessibilityId("Steppers").click();
		driver.findElementByAccessibilityId("Increment").click();
		driver.findElementByAccessibilityId("Increment").click();
        System.out.println(driver.findElementsByClassName("XCUIElementTypeStaticText").get(1).getText());
        
        System.out.println(driver.findElementsByClassName("XCUIElementTypeStaticText").get(2).getText());
        driver.findElementByAccessibilityId("Decrement").click();
        
        System.out.println(driver.findElementsByClassName("XCUIElementTypeStaticText").get(1).getText());
        driver.navigate().back();
	}
	public void testPickerViews(){
		driver.findElementByAccessibilityId("Picker View").click();
		driver.findElementByName("Red color component value").sendKeys("70");
		driver.findElementByName("Green color component value").sendKeys("220");
		driver.findElementsByClassName("XCUIElementTypePickerWheel").get(2).sendKeys("130");
	}
	/*
	 * This is the main method which makes use of Open Excel for windows app
	 * method.
	 * 
	 * @author Nhan Nguyen
	 * 
	 * @version 1.0
	 * 
	 * @since 04-01-2019
	 * 
	 * @param args filename, sheetname.
	 * 
	 * @return none.
	 * 
	 * @exception IOException On input error.
	 * 
	 * @see IOException
	 */
	public void openExcel(String filename, String sheetname) throws IOException {
		// TODO Auto-generated method stub
		String strFileName = Constants.ROOT_PATH_EXCELS + filename;
		fis = new FileInputStream(strFileName);
		String strFileOut = Constants.ROOT_PATH_EXCELSOUT + filename;
		fos = new FileOutputStream(strFileOut);
		workbook = new XSSFWorkbook(fis);
		sheet = workbook.getSheet(sheetname);
		System.out.println("[Excel file already open: \n" + strFileName + "\n with  sheetname: \n" + sheetname + "]");
	}
	/*
	 * This is the main method which makes use of run each step on Excel for
	 * windows app method.
	 * 
	 * @author Nhan Nguyen
	 * 
	 * @version 1.0
	 * 
	 * @since 04-01-2019
	 * 
	 * @param args none.
	 * 
	 * @return none.
	 * 
	 * @exception IOException On input error.
	 * 
	 * @see IOException
	 */
	public void runTestcaseOnExcel() throws IOException {
		// TODO Auto-generated method stub
		int totalRow = sheet.getLastRowNum() + 1;
		for (int i = 1; i < totalRow; i++) {
			messageAlert = "";
			errorMessage = "";
			String getTestcase = getExcelLine(i, 9, sheet);
			if (getTestcase.equals("") || getTestcase == null) {
				continue;
			} else {
				String[] step = getTestcase.split("\n");
				for (int j = 0; j < step.length; j++) {
					if (step[j].trim().equals("")) {
						continue;
					}
					System.out.println("----Define steps for running----");
					System.out.println("[step[" + j + "]: " + step[j] + "]");
					System.out.println("----Running each step:----");
					String runStep = step[j].toString().trim();
					runStep = clearNumberAndDot(runStep);
					try {
						runStep(runStep, true);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					//update excel
					if (!messageAlert.equals("")) {
						setValueInExcel(i, 20, messageAlert, "");
						setValueInExcel(i, 20, Constants.LABEL_PENDING_ISSUES, Constants.LABEL_YELLOW);
						break;
					}
					if( j == step.length - 1) {
						if (!errorMessage.equals("")) {
							setValueInExcel(i, 20, errorMessage, "");
							setValueInExcel(i, 20, Constants.LABEL_FAIL, Constants.LABEL_FAIL.toUpperCase());
							break;
						}
						if (errorMessage.equals("") && j == step.length - 1) {
							setValueInExcel(i, 20, errorMessage, "");
							setValueInExcel(i, 20, Constants.LABEL_PASS, Constants.LABEL_PASS.toUpperCase()+"ED");
							break;
						}
					}
				}
			}
			
			workbook.write(fos);
			fos.close();
		}
	}
	/*
	 * This is the main method which makes use of get each line from Excel for windows app
	 * method.
	 * 
	 * @author Nhan Nguyen
	 * 
	 * @version 1.0
	 * 
	 * @since 02-01-2019
	 * 
	 * @param args row,cell,sheet.
	 * 
	 * @return String.
	 * 
	 * @exception none.
	 * 
	 * @see none
	 */
	public static String getExcelLine(int row, int cell, XSSFSheet sheet) {
		XSSFRow rowExcel = sheet.getRow(row);
		XSSFCell cellExcel = rowExcel.getCell(cell, Row.RETURN_BLANK_AS_NULL);
		String name = "";
		if (cellExcel != null) {
			try {
				name = rowExcel.getCell(cell, Row.RETURN_BLANK_AS_NULL).getStringCellValue();
			} catch (Exception e) {
				name = String.valueOf(rowExcel.getCell(cell, Row.RETURN_BLANK_AS_NULL).getNumericCellValue());
			}
		}
		return name;
	}
	/*
	 * This is the main method which makes use of clear Number And Dot from Excel for windows app
	 * method.
	 * 
	 * @author Nhan Nguyen
	 * 
	 * @version 1.0
	 * 
	 * @since 02-01-2019
	 * 
	 * @param args step.
	 * 
	 * @return String.
	 * 
	 * @exception none.
	 * 
	 * @see none
	 */
	public String clearNumberAndDot(String step) {
		step = step.trim();
		String newStep = step;
		for (char c : step.toCharArray()) {
			if (Character.isDigit(c)) {
				newStep = newStep.substring(1);
			} else {
				break;
			}
		}
		return newStep.replace(". ", "");
	}
	/*
	 * This is the main method which makes use of run steps get from Excel for windows app
	 * method.
	 * 
	 * @author Nhan Nguyen
	 * 
	 * @version 1.0
	 * 
	 * @since 02-01-2019
	 * 
	 * @param args step,excel.
	 * 
	 * @return none.
	 * 
	 * @exception Exception On input error.
	 * 
	 * @see Exception
	 */
	private void runStep(String step, boolean excel) throws Exception {
		// TODO Auto-generated method stub
		Random rad = new Random();
		String element, result;
		String[] arrayStep = step.trim().split("\"");
		for (int i = 0; i < arrayStep.length; i++) {
			System.out.println("[arrayStep(" + i + "): " + arrayStep[i] + "]");
		}
		String keyword = arrayStep[0];
		System.out.println("[Keyword:" + keyword + "]");
		switch (keyword.trim()) {
		case "Open IOs App":
			try {
				appSetting();
			} catch (AssertionError e) {
				messageAlert = e.toString();
			}

			break;
		case "Click on":
			element = arrayStep[1];
			System.out.println("[get element: " + element + "]");
			try {
				clickOn(element);
			} catch (AssertionError e) {
				messageAlert = e.toString();
			}

			break;
		case "Input into":
			element = arrayStep[1];
			result = arrayStep[3];
			System.out.println("[get element: " + element + "]");
			try {
				inputInto(element, result);
			} catch (AssertionError e) {
				messageAlert = e.toString();
			}
			
			break;
		case "Should see":
			element = arrayStep[1];
			result = arrayStep[3];
			System.out.println("[get element: " + element + "]");
			System.out.println("[get result: " + result + "]");
			shouldSee(element, result);
			
			
			break;
		}
	}
	/*
	 * This is the main method which makes use of set value to Excel for IOs app
	 * method.
	 * 
	 * @author Nhan Nguyen
	 * 
	 * @version 1.0
	 * 
	 * @since 02-01-2019
	 * 
	 * @param args rowNum,colNum,value,color.
	 * 
	 * @return none.
	 * 
	 * @exception none.
	 * 
	 * @see none
	 */
	public void setValueInExcel(int rowNum, int colNum, String value, String color) {
		row = sheet.getRow(rowNum);
		if (row == null) {
			row = sheet.createRow(rowNum);
		}
		cell = row.getCell(colNum, org.apache.poi.ss.usermodel.Row.RETURN_BLANK_AS_NULL);
		if (cell == null) {
			cell = row.createCell(colNum);
			cell.setCellValue(value);
		} else {
			cell.setCellValue(value);
		}
		CellStyle style = workbook.createCellStyle();
		Font font = workbook.createFont();
		style.setBorderBottom(CellStyle.BORDER_THIN);
		style.setBottomBorderColor(HSSFColor.BLACK.index);
		style.setBorderLeft(CellStyle.BORDER_THIN);
		style.setLeftBorderColor(HSSFColor.BLACK.index);
		style.setBorderRight(CellStyle.BORDER_THIN);
		style.setRightBorderColor(HSSFColor.BLACK.index);
		style.setBorderTop(CellStyle.BORDER_THIN);
		style.setTopBorderColor(HSSFColor.BLACK.index);
		style.setAlignment(HorizontalAlignment.LEFT);
		style.setVerticalAlignment(org.apache.poi.ss.usermodel.VerticalAlignment.CENTER);

		switch (color) {
		case "PASSED":
			font.setColor(HSSFColor.WHITE.index);
			style.setFillForegroundColor(HSSFColor.GREEN.index);
			style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			style.setFont(font);
			cell.setCellStyle(style);
			break;
		case "FAIL":
			font.setColor(HSSFColor.WHITE.index);
			style.setFillForegroundColor(HSSFColor.RED.index);
			style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			style.setFont(font);
			cell.setCellStyle(style);
			break;
		case "YELLOW":
			font.setColor(HSSFColor.BLACK.index);
			style.setFillForegroundColor(HSSFColor.YELLOW.index);
			style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			style.setFont(font);
			cell.setCellStyle(style);
			break;
		case "ORANGE":
			font.setColor(HSSFColor.BLACK.index);
			style.setFillForegroundColor(HSSFColor.ORANGE.index);
			style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			style.setFont(font);
			cell.setCellStyle(style);
			break;
		default:
			font.setColor(HSSFColor.BLACK.index);
			style.setFont(font);
			cell.setCellStyle(style);
			break;
		}
	}
	/*
	 * This is the main method which makes use of find element for windows app
	 * method.
	 * 
	 * @author Nhan Nguyen
	 * 
	 * @version 1.0
	 * 
	 * @since 02-01-2019
	 * 
	 * @param args objectName.
	 * 
	 * @return WebElement.
	 * 
	 * @exception Exception On input error.
	 * 
	 * @see Exception
	 */

	private WebElement getElement(String objectName) throws Exception {
		if (null != ReadProperties.getOR(objectName + "-accIDIOsA")) {
			System.out.println("[object name: "+ReadProperties.getOR(objectName + "-accIDIOsA")+"]");
			return driver.findElementByAccessibilityId(ReadProperties.getOR(objectName + "-accIDIOsA"));
		} else if (null != ReadProperties.getOR(objectName + "-clssNameIOsA")) {
			System.out.println("[object name: "+ReadProperties.getOR(objectName + "-clssNameIOsA")+"]");
			return driver.findElementByClassName(ReadProperties.getOR(objectName + "-clssNameIOsA"));
		} else if (null != ReadProperties.getOR(objectName + "-nameIOsA")) {
			System.out.println("[object name: "+ReadProperties.getOR(objectName + "-nameIOsA")+"]");
			return driver.findElementByName(ReadProperties.getOR(objectName + "-nameIOsA"));
		} else if (null != ReadProperties.getOR(objectName + "-idIOsA")) {
			System.out.println("[object name: "+ReadProperties.getOR(objectName + "-idIOsA")+"]");
			return driver.findElementById(ReadProperties.getOR(objectName + "-idIOsA"));
		} else if (null != ReadProperties.getOR(objectName + "-xpathIOsA")) {
			System.out.println("[object name: "+ReadProperties.getOR(objectName + "-xpathIOsA")+"]");
			return driver.findElementByXPath(ReadProperties.getOR(objectName + "-xpathIOsA"));
		} else {
			try {
				if (driver.findElementByXPath(ReadProperties.getOR(objectName + "-xpathIOsA")).isDisplayed()) {
					return driver.findElementByXPath(ReadProperties.getOR(objectName + "-xpathIOsA"));
				} else {
					return null;
				}
			} catch (Exception e) {
				return null;
			}
		}
	}

	public void clickOn(String element) throws Exception {
		System.out.println("[I am Try to lick on it!]");
		getElement(element).click();
	}

	private void inputInto(String element, String result) throws Exception {
		getElement(element).clear();
		getElement(element).sendKeys(result);
		
	}
	public void shouldSee(String element, String Result) throws Exception {
		WebElement eleResult = getElement(element);
		String actual =eleResult.getText().replace("Display is", "").trim();
		System.out.println("[get text from Element: "+actual+"]");
		System.out.println("[Result: "+Result+"]");
		try {
			Assert.assertTrue("The result <"+Result+"> not equals as <"+actual +">!",actual.equals(Result.trim()));
		} catch (AssertionError e) {
			//System.out.println("status mobScenario: "+stepDefinitions.mobScenario.getStatus());
			errorMessage = e.toString();
			//bError= true;
			
		}
	}
	/*
	 * This is the main method which makes use create user cases from Excel to
	 * JIRA from Excel for IOs app method.
	 * 
	 * @author Nhan Nguyen
	 * 
	 * @version 1.0
	 * 
	 * @since 02-01-2019
	 * 
	 * @param args sheetName,excelName.
	 * 
	 * @return none.
	 * 
	 * @exception KeyManagementException,NoSuchAlgorithmException,IOException,
	 * InterruptedException.
	 * 
	 * @see none
	 */
	public void createUsercaseIOsApp(String sheetName, String excelName) throws KeyManagementException, NoSuchAlgorithmException, IOException, InterruptedException {
		// TODO Auto-generated method stub
		appSetting();
		String strExcelTestCasePath = Constants.ROOT_PATH_EXCELS + excelName ;

		fis = new FileInputStream(new File(strExcelTestCasePath));
		workbook = new XSSFWorkbook(fis);
		sheet = workbook.getSheet(sheetName);
		int rowCount = sheet.getLastRowNum() + 1;
		for (int i = 1; i < rowCount; i++) {
			String jiraticket = "";
			if (sheet.getRow(i).getCell(1) != null && !sheet.getRow(i).getCell(1).getStringCellValue().equals("")) {
				jiraticket = sheet.getRow(i).getCell(1).getStringCellValue();
			}

			String assignee = getValueFromExcel(i, 18);
			String expect = getValueFromExcel(i, 8);
			String designInfo = getValueFromExcel(i, 10);
			String priority = getValueFromExcel(i, 6);
			String label = getValueFromExcel(i, 5);
			String summary = sheet.getRow(i).getCell(7).getStringCellValue();
			String run = sheet.getRow(i).getCell(13).getStringCellValue();
			String team = "";
			String teamId = "";

			if (summary != "") {
				if (run.trim().toLowerCase().equals(Constants.LABEL_YES)) {
					team = Constants.TEAM_AUTO;
					teamId = Constants.TEAM_AUTO_ID;
				} else if (run.trim().toLowerCase().equals(Constants.LABEL_NO)) {
					team = Constants.TEAM_MANUAL;
					teamId = Constants.TEAM_MANUAL_ID;
				}

				//PostTicketOnJira jira = new PostTicketOnJira();
				ParamsPostMethod params = new ParamsPostMethod();
				String description = StringEscapeUtils
						.escapeJson("Steps:" + changeTestStep(getValueFromExcel(i, 9)).replaceAll("#", "\r\n #")
								+ "\r\n\r\n" + expect + "\n" + designInfo);

				if (!jiraticket.equals("")) {
					boolean update = true;
					runStepForTestcase(run, i, sheet, jiraticket, update, strExcelTestCasePath, expect, designInfo);
				} else {
					boolean update = false;
					System.out.println("[Summary: "+summary+ "]\n"+"[description: "+ description+ "]\n"+ "[assignee: "+assignee+ "]\n"+"[priority: "+ priority+ "]\n"+"[label: "+ label+ "]\n"+ "[team: "+team+ "]\n"+"[teamId: "+ teamId+ "]\n");
					String keyResonse = APIsJIRA.postAPI(
							ReadProperties.getConfig("jiraURL") + Constants.JIRA_Path,
							params.paramsCreateUserCase(summary, description, assignee, priority, label, team, teamId),userJira,pwJira);
					System.out.println("keyResonse: " + keyResonse);

					// Get ticket jira
					for (String item : keyResonse.substring(8, keyResonse.length() - 1).split(",")) {
						if (item.contains("key")) {
							jiraticket = item.split(":")[1].replaceAll("\"", "");
						}
					}

					// run step
					runStepForTestcase(run, i, sheet, jiraticket, update, strExcelTestCasePath, expect, designInfo);
				}
			}
		}
	}
	/*
	 * This is the main method which makes use to get value on Excel from Excel for IOs app
	 * method.
	 * 
	 * @author Nhan Nguyen
	 * 
	 * @version 1.0
	 * 
	 * @since 02-01-2019
	 * 
	 * @param args row,column.
	 * 
	 * @return String.
	 * 
	 * @exception none.
	 * 
	 * @see none
	 */
	public String getValueFromExcel(int row, int column) {
		String value = "";
		XSSFCell cell = sheet.getRow(row).getCell(column);
		DataFormatter objDefaultFormat = new DataFormatter();
		FormulaEvaluator objFormulaEvaluator = new XSSFFormulaEvaluator((XSSFWorkbook) workbook);
		value = objDefaultFormat.formatCellValue(cell, objFormulaEvaluator);
		return value;
	}
	/*
	 * This is the main method which makes use to change test step for IOs app
	 * method.
	 * 
	 * @author Nhan Nguyen
	 * 
	 * @version 1.0
	 * 
	 * @since 02-01-2019
	 * 
	 * @param args step.
	 * 
	 * @return String.
	 * 
	 * @exception none.
	 * 
	 * @see none
	 */
	public String changeTestStep(String strStep) {
		// strStep = strStep.replaceAll("", replacement)
		String newStrStep = "";
		for (String step : strStep.split("\n")) {
			newStrStep += "# " + clearNumberAndDot(step) + " ";
		}
		return newStrStep;
	}
	/*
	 * This is the main method which makes use to run step on Excel for IOs app
	 * method.
	 * 
	 * @author Nhan Nguyen
	 * 
	 * @version 1.0
	 * 
	 * @since 02-01-2019
	 * 
	 * @param args run, i,sheet, jiraticket,update, exceltestcasepath, expect, designedInfo.
	 * 
	 * @return none.
	 * 
	 * @exception KeyManagementException,NoSuchAlgorithmException,IOException,InterruptedException.
	 * 
	 * @see none
	 */
	public void runStepForTestcase(String run, int i, XSSFSheet sheet, String jiraticket, boolean update,
			String strExcelTestCasePath, String expect, String designInfo)
			throws KeyManagementException, NoSuchAlgorithmException, IOException, InterruptedException {
		row = sheet.getRow(i);
		Cell cellTicketID = row.getCell(1);
		Cell cellCreateDay = row.getCell(14);
		Cell cellStartDay = row.getCell(15);
		Cell cellStatus = row.getCell(16);
		Cell cellReporter = row.getCell(17);

		if (run.toLowerCase().trim().equals("no") && update) {
			System.out.println("changeTestStep(getValueFromExcel(i, 9): " + changeTestStep(getValueFromExcel(i, 9)));
			System.out.println("getValueFromExcel(i, 9): " + getValueFromExcel(i, 9));

			APIsJIRA.postAPI(
					ReadProperties.getConfig("jiraURL") + Constants.JIRA_Path + jiraticket + "/comment",
					Constants.paramsCommentJira("update description"),userJira,pwJira);
			APIsJIRA.putAPI(ReadProperties.getConfig("jiraURL") + Constants.JIRA_Path + jiraticket,
					Constants.paramsAddDescriptionJira("Steps:" + changeTestStep(getValueFromExcel(i, 9)) + "\r\n\r\n" + expect
							+ "\n" + designInfo),userJira,pwJira);
		}

		if (run.toLowerCase().trim().equals("yes")) {
			messageAlert = "";
			String[] lStep = getValueFromExcel(i, 9).split("\n");
			for (int j = 0; j < lStep.length; j++) {
				String step = lStep[j].toString().trim();
				step = clearNumberAndDot(step);
				try {
					runStep(step, true);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			// update excel
			Cell cellEvidence = row.getCell(11);
			Cell cellComment = row.getCell(20);
			String comment = "";

			if (cellEvidence != null) {
				cellEvidence.removeCellComment();
			} else {
				cellEvidence = row.createCell(11);
			}

			if (cellCreateDay != null) {
				cellCreateDay.removeCellComment();
			} else {
				cellCreateDay = row.createCell(14);
			}

			if (cellStartDay != null) {
				cellStartDay.removeCellComment();
			} else {
				cellStartDay = row.createCell(15);
			}

			if (cellStatus != null) {
				cellStatus.removeCellComment();
			} else {
				cellStatus = row.createCell(16);
			}

			if (cellReporter != null) {
				cellReporter.removeCellComment();
			} else {
				cellReporter = row.createCell(17);
			}

			if (cellComment != null) {
				cellComment.removeCellComment();
			} else {
				cellComment = row.createCell(20);
			}

			if (!messageAlert.equals("")) {
				cellEvidence.setCellValue(
						(jiraticket + " " + timeToCheck).replaceAll(":", "").replaceAll(" ", "_") + ".png");
				cellComment.setCellValue("Not OK");

				// update jira
				comment = "Hi,\\r\\n\\n" + "This usercase is NOT Ok at " + timeToCheck + "\\r\\n" + "Actual result: "
						+ messageAlert + "\\r\\n" + "Please see attachment("
						+ (jiraticket + " " + timeToCheck).replaceAll(":", "").replaceAll(" ", "_") + ".png"
						+ ") for more details\\r\\n" + "It is checked by Automation team.\\r\\n\\n" + "Thanks";
				APIsJIRA.postAPI(
						ReadProperties.getConfig("jiraURL") + Constants.JIRA_Path + jiraticket + "/comment",
						Constants.paramsCommentJira(comment),userJira,pwJira);
				APIsJIRA.putAPI(ReadProperties.getConfig("jiraURL") + Constants.JIRA_Path + jiraticket,
						Constants.paramsAddDescriptionJira("Steps:" + changeTestStep(getValueFromExcel(i, 9)) + "\r\n\r\n"
								+ expect + "\n" + designInfo),userJira,pwJira);
				APIsJIRA.putAPI(ReadProperties.getConfig("jiraURL") + Constants.JIRA_Path + jiraticket,
						Constants.paramsAddLabelJira(Constants.LABEL_FAIL),userJira,pwJira);
				APIsJIRA.putAPI(ReadProperties.getConfig("jiraURL") + Constants.JIRA_Path + jiraticket,
						Constants.paramsRemoveLabelJira(Constants.LABEL_PASS),userJira,pwJira);
				takeScreenShort((jiraticket + " " + timeToCheck).replaceAll(":", ""));
				uploadAttachment(userJira, pwJira, jiraticket, Constants.ROOT_SCREENSHOT+(jiraticket + " " + timeToCheck).replaceAll(":", "").replaceAll(" ", "_") + ".png");
			}

			if (messageAlert.equals("")) {
				// update excel
				cellComment.setCellValue("OK");
				cellEvidence.setCellValue("");
				// update jira
				comment = "Hi,\\r\\n\\n" + "This usercase is Ok at " + df.format(da) + "\\r\\n"
						+ "It is checked by Automation team\\r\\n\\n" + "Thanks";
				APIsJIRA.postAPI(
						ReadProperties.getConfig("jiraURL") + Constants.JIRA_Path + jiraticket + "/comment",
						Constants.paramsCommentJira(comment),userJira,pwJira);
				APIsJIRA.putAPI(ReadProperties.getConfig("jiraURL") + Constants.JIRA_Path + jiraticket,
						Constants.paramsAddDescriptionJira("Steps:" + changeTestStep(getValueFromExcel(i, 9)) + "\r\n\r\n"
								+ expect + "\n" + designInfo),userJira,pwJira);
				APIsJIRA.putAPI(ReadProperties.getConfig("jiraURL") + Constants.JIRA_Path + jiraticket,
						Constants.paramsAddLabelJira(Constants.LABEL_PASS),userJira,pwJira);
				APIsJIRA.putAPI(ReadProperties.getConfig("jiraURL") + Constants.JIRA_Path + jiraticket,
						Constants.paramsRemoveLabelJira(Constants.LABEL_FAIL),userJira,pwJira);
			}
		}

		if (!update) {
			if (cellTicketID != null) {
				cellTicketID.removeCellComment();
			} else {
				cellTicketID = row.createCell(1);
			}
			cellTicketID.setCellValue(jiraticket);
			cellCreateDay.setCellValue(timeToCheck);
			cellStartDay.setCellValue(timeToCheck);
		}

		cellStatus.setCellValue("To Do");
		cellReporter.setCellValue(userJira);

		fis.close();

		// Update excel
		FileOutputStream streamOut = new FileOutputStream(new File(strExcelTestCasePath));
		workbook.write(streamOut);
		streamOut.close();
	}
	
	/*
	 * This is the main method which makes use to take screenshoot for IOs app
	 * method.
	 * 
	 * @author Nhan Nguyen
	 * 
	 * @version 1.0
	 * 
	 * @since 02-01-2019
	 * 
	 * @param args screenshotName,myScenario.
	 * 
	 * @return none.
	 * 
	 * @exception IOException,InterruptedException.
	 * 
	 * @see none
	 */
	public void takeScreenShort(String screenshotName) throws InterruptedException, IOException {
		
		Thread.sleep(2000);
		File screen = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		BufferedImage img = ImageIO.read(screen);
		FileOutputStream out = null;
		try {
			out = new FileOutputStream(Constants.ROOT_SCREENSHOT+screenshotName);
		} catch (java.io.FileNotFoundException io) {
			io.printStackTrace();
		}
		
		//ImageIO.write(img, "png", out);
		FileUtils.copyFile(screen, new File(Constants.ROOT_SCREENSHOT+screenshotName.replaceAll(" ", "_") + ".png"));
	}
	/*
	 * This is the main method which makes use to upload attachment on JIRA for IOs app
	 * method.
	 * 
	 * @author Nhan Nguyen
	 * 
	 * @version 1.0
	 * 
	 * @since 02-01-2019
	 * 
	 * @param args username,password,jiraID,file.
	 * 
	 * @return none.
	 * 
	 * @exception ClientProtocolException, IOException.
	 * 
	 * @see none
	 */
	public static void uploadAttachment(String username, String password, String jiraID, String file)
			throws ClientProtocolException, IOException {
		File f = new File(file);
		CloseableHttpClient httpClient = HttpClients.createDefault();
		System.out.println("attachments: " + ReadProperties.getConfig("jiraURL") + Constants.JIRA_Path + jiraID
				+ "/attachments");
		HttpPost post = new HttpPost(
				ReadProperties.getConfig("jiraURL") + Constants.JIRA_Path + jiraID + "/attachments");
		if (!username.equals("") && !password.equals("")) {
			String author = username + ":" + password;
			String encoding = Base64.encodeBytes(author.getBytes());
			post.setHeader("Authorization", "Basic " + encoding);
		}
		post.setHeader("X-Atlassian-Token", "nocheck");
		HttpEntity reqEntity = MultipartEntityBuilder.create().setMode(HttpMultipartMode.BROWSER_COMPATIBLE)
				.addBinaryBody("file", new FileInputStream(f), ContentType.APPLICATION_OCTET_STREAM, f.getName())
				.build();
		post.setEntity(reqEntity);
		post.setHeader(reqEntity.getContentType());
		CloseableHttpResponse response = httpClient.execute(post);
	}
	/*
	 * This is the main method which makes use to run and updated after run on excel and to JIRA for IOs app
	 * method.
	 * 
	 * @author Nhan Nguyen
	 * 
	 * @version 1.0
	 * 
	 * @since 02-01-2019
	 * 
	 * @param args fileName.
	 * 
	 * @return none.
	 * 
	 * @exception Exception.
	 * 
	 * @see none
	 */
	public void runCheckUsercaseOnExcelIOsApp(String fileName) throws Exception {
		String strExcelTestCasePath = Constants.ROOT_PATH_EXCELS+fileName;

		fis = new FileInputStream(new File(strExcelTestCasePath));
		workbook = new XSSFWorkbook(fis);

		for (int i = 0; i <= workbook.getActiveSheetIndex(); i++) {
			sheet = workbook.getSheetAt(i);
			for (int j = 1; j <= sheet.getLastRowNum(); j++) {
				String jiraticket = "";
				if (sheet.getRow(j).getCell(1) != null && !sheet.getRow(j).getCell(1).getStringCellValue().equals("")) {
					jiraticket = sheet.getRow(j).getCell(1).getStringCellValue();
				}

				runExcel(j, jiraticket, strExcelTestCasePath);
			}
		}
	}
	/*
	 * This is the main method which makes use to run each user case and updated after run on excel and to JIRA for IOs app
	 * method.
	 * 
	 * @author Nhan Nguyen
	 * 
	 * @version 1.0
	 * 
	 * @since 02-01-2019
	 * 
	 * @param args jiraID,sheetName.
	 * 
	 * @return none.
	 * 
	 * @exception Exception.
	 * 
	 * @see none
	 */
	public void runCheckSomeUsercaseOnExcelIOsApp(String jiraID, String sheetName, String fileName) throws Exception {
		String strExcelTestCasePath = Constants.ROOT_PATH_EXCELS+fileName;

		fis = new FileInputStream(new File(strExcelTestCasePath));
		workbook = new XSSFWorkbook(fis);
		sheet = workbook.getSheet(sheetName);

		for (int j = 1; j <= sheet.getLastRowNum(); j++) {
			if (sheet.getRow(j).getCell(1) != null && !sheet.getRow(j).getCell(1).getStringCellValue().equals("")) {
				if (sheet.getRow(j).getCell(1).getStringCellValue().trim().toLowerCase()
						.equals(jiraID.trim().toLowerCase())) {
					runExcel(j, jiraID, strExcelTestCasePath);
				}
			}
		}
	}
	/*
	 * This is the main method which makes use to run on excel and updated to JIRA for IOs app
	 * method.
	 * 
	 * @author Nhan Nguyen
	 * 
	 * @version 1.0
	 * 
	 * @since 02-01-2019
	 * 
	 * @param args noRow,jiraID,strExcelTestCasePath.
	 * 
	 * @return none.
	 * 
	 * @exception Exception.
	 * 
	 * @see none
	 */
	public void runExcel(int noRow, String jiraID, String strExcelTestCasePath) throws Exception {
		String expect = getValueFromExcel(noRow, 8);
		String summary = sheet.getRow(noRow).getCell(7).getStringCellValue();
		String designInfo = getValueFromExcel(noRow, 10);
		String run = sheet.getRow(noRow).getCell(13).getStringCellValue();
		if (summary != "") {
			if (!jiraID.equals("")) {
				boolean update = true;
				runStepForTestcase(run, noRow, sheet, jiraID, update, strExcelTestCasePath, expect, designInfo);
			}
		}
	}
	/*
	 * This is the main method which makes use to check all case on JIRA for IOs app
	 * method.
	 * 
	 * @author Nhan Nguyen
	 * 
	 * @version 1.0
	 * 
	 * @since 02-01-2019
	 * 
	 * @param args fileName.
	 * 
	 * @return none.
	 * 
	 * @exception Exception.
	 * 
	 * @see none
	 */
	public void runCheckAllCaseOnJiraIOsApp(String fileName) throws Exception {
		// TODO Auto-generated method stub
		String strExcelTestCasePath =  Constants.ROOT_PATH_EXCELS+fileName;

		fis = new FileInputStream(new File(strExcelTestCasePath));
		workbook = new XSSFWorkbook(fis);

		for (int i = 0; i <= workbook.getActiveSheetIndex(); i++) {
			sheet = workbook.getSheetAt(i);
			for (int j = 1; j <= sheet.getLastRowNum(); j++) {
				String jiraID = sheet.getRow(j).getCell(1).getStringCellValue();
				if (!jiraID.equals("") && jiraID != null) {
					messageAlert = "";
					errorMessage = "";
					runAndUpdateExcel(sheet, jiraID, strExcelTestCasePath);
				}
			}
		}
	}
	/*
	 * This is the main method which makes use to some case on JIRA for IOs app
	 * method.
	 * 
	 * @author Nhan Nguyen
	 * 
	 * @version 1.0
	 * 
	 * @since 02-01-2019
	 * 
	 * @param args noRow,jiraID,strExcelTestCasePath.
	 * 
	 * @return none.
	 * 
	 * @exception Exception.
	 * 
	 * @see none
	 */
	public void runCheckSomeCaseOnJiraIOSApp(String jiraID, String sheetName, String fileName) throws Exception {
		// TODO Auto-generated method stub
		String strExcelTestCasePath = Constants.ROOT_PATH_EXCELS+fileName;

		fis = new FileInputStream(new File(strExcelTestCasePath));
		workbook = new XSSFWorkbook(fis);
		sheet = workbook.getSheet(sheetName);

		runAndUpdateExcel(sheet, jiraID, strExcelTestCasePath);
	}
	/*
	 * This is the main method which makes use to run test case on excel for IOs app
	 * method.
	 * 
	 * @author Nhan Nguyen
	 * 
	 * @version 1.0
	 * 
	 * @since 02-01-2019
	 * 
	 * @param args noRow,jiraID,strExcelTestCasePath.
	 * 
	 * @return none.
	 * 
	 * @exception Exception.
	 * 
	 * @see none
	 */
	public void runAndUpdateExcel(XSSFSheet sheet, String jiraID, String strExcelTestCasePath) throws Exception {
		String apiOfJira = APIsJIRA.getAPI(ReadProperties.getConfig("jiraURL") + Constants.JIRA_Path + jiraID,userJira,pwJira);
		JSONObject object = new JSONObject(apiOfJira);
		String team = object.getJSONObject("fields").get(Constants.CUSTOMFIELD).toString().replace("[", "").replace("]",
				"");
		JSONObject jsonTeam = new JSONObject(team);
		String value = jsonTeam.getString("value");
		if (value.trim().toLowerCase().equals(Constants.TEAM_AUTO.toLowerCase())) {
			int noRow = 0;
			boolean update = false;
			boolean valueReturn = false;

			for (int i = 1; i <= sheet.getLastRowNum(); i++) {
				if (sheet.getRow(i).getCell(1).getStringCellValue().equals(jiraID)) {
					update = true;
					noRow = i;
				}
			}

			// Get body ticket from JIRA ID
			String bodyTicket = APIsJIRA
					.getAPI(ReadProperties.getConfig("jiraURL") + Constants.JIRA_Path + jiraID,userJira,pwJira);
			messageAlert = "";
			errorMessage = "";
			String comment = "";
			String description = "";
			if (object.getJSONObject("fields") != null
					&& object.getJSONObject("fields").getString("description") != null) {
				description = object.getJSONObject("fields").getString("description");
			}
			SimpleDateFormat df = new SimpleDateFormat("HH:mm dd_MMM_YYYY");
			Date da = new Date();

			boolean foundStep = false;
			// get the description for each ticket
			String[] u = description.split("\r\n");
			for (int j = 0; j < u.length; j++) {
				if (u[j].startsWith(" #") || u[j].startsWith("#")) {
					foundStep = true;
					String[] step = u[j].split("#");
					
					runStep(step[1].trim(), false);
					String timeToCheck = df.format(da);
					if (!errorMessage.equals("")) {
						comment = "Hi,\\r\\n\\n" + "This usercase is NOT Ok at " + timeToCheck + "\\r\\n"
								+ "Actual result: " + errorMessage + "\\r\\n" + "Please see attachment("
								+ (jiraID + " " + timeToCheck).replaceAll(":", "").replaceAll(" ", "_") + ".png"
								+ ") for more details\\r\\n" + "It is checked by Automation team.\\r\\n\\n" + "Thanks";
						APIsJIRA.postAPI(ReadProperties.getConfig("jiraURL") + Constants.JIRA_Path + jiraID
								+ "/comment", Constants.paramsCommentJira(comment),userJira,pwJira);
						APIsJIRA.putAPI(ReadProperties.getConfig("jiraURL") + Constants.JIRA_Path + jiraID,
								Constants.paramsRemoveLabelJira(Constants.LABEL_PASS),userJira,pwJira);
						APIsJIRA.putAPI(ReadProperties.getConfig("jiraURL") + Constants.JIRA_Path + jiraID,
								Constants.paramsAddLabelJira(Constants.LABEL_FAIL),userJira,pwJira);
						takeScreenShort((jiraID + " " + timeToCheck).replaceAll(":", ""));
						uploadAttachment(userJira, pwJira, jiraID, Constants.ROOT_SCREENSHOT+
								(jiraID + " " + timeToCheck).replaceAll(":", "").replaceAll(" ", "_") + ".png");
					}
				}
				if (errorMessage.equals("") && j == u.length - 1) {
					valueReturn = true;
					comment = "Hi,\\r\\n\\n" + "This usercase is Ok at " + df.format(da) + "\\r\\n"
							+ "It is checked by Automation team\\r\\n\\n" + "Thanks";
					APIsJIRA.postAPI(
							ReadProperties.getConfig("jiraURL") + Constants.JIRA_Path + jiraID + "/comment",
							Constants.paramsCommentJira(comment),userJira,pwJira);
					APIsJIRA.putAPI(ReadProperties.getConfig("jiraURL") + Constants.JIRA_Path + jiraID,
							Constants.paramsRemoveLabelJira(Constants.LABEL_FAIL),userJira,pwJira);
					APIsJIRA.putAPI(ReadProperties.getConfig("jiraURL") + Constants.JIRA_Path + jiraID,
							Constants.paramsAddLabelJira(Constants.LABEL_PASS),userJira,pwJira);
				}
			}

			if (noRow != 0) {
				XSSFRow row = sheet.getRow(noRow);

				Cell cellComment = row.getCell(20);
				if (cellComment != null) {
					cellComment.removeCellComment();
				} else {
					cellComment = row.createCell(20);
				}

				if (valueReturn) {
					setValueInExcel(noRow,20,"OK","PASSED");
					
				} else {
					setValueInExcel(noRow,20,"Not OK","FAIL");
					
				}

				fis.close();

				// Update excel
				FileOutputStream streamOut = new FileOutputStream(new File(strExcelTestCasePath));
				workbook.write(streamOut);
				streamOut.close();
			}
		}
	}
}
