/**
 * 
 */
package com.qa.IOsAndroid;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Assert;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;

import com.qa.common.Constants;
import com.servicecorp.core.server.Server;
import com.servicecorp.core.utils.DriverUtils;
import com.servicecorp.core.utils.OperationUtils;
import com.servicecorp.core.utils.ReadProperties;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;

/**
 * @author nhan.nguyen
 *
 */
public class helperIOsAndroid {
	public static AndroidDriver<MobileElement>  driver;
	//private Server server = null;

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
				dest = System.getProperty("user.dir") + "/reporting/fail/" + fileName;
				System.out.println("path:" + dest);
				File destination = new File(dest);
				FileUtils.copyFile(source, destination);
				dest = Constants.ROOT_EXTENT_FAIL + fileName;
			} else {
				dest = System.getProperty("user.dir") + "/reporting/pass/" + fileName;
				System.out.println("path:" + dest);
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
	 * This is the main method which makes use of find element for windows app
	 * method.
	 * 
	 * @author Nhan Nguyen
	 * 
	 * @version 1.0
	 * 
	 * @since 24-12-2018
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
		if (null != ReadProperties.getOR(objectName + "-idIOsAnd")) {
			return driver.findElementById(ReadProperties.getOR(objectName + "-idIOsAnd"));
		} else if (null != ReadProperties.getOR(objectName + "-classNameIOsAnd")) {
			return driver.findElementByClassName(ReadProperties.getOR(objectName + "-classNameIOsAnd"));
		} else if (null != ReadProperties.getOR(objectName + "-xpathIOsAnd")) {
			return driver.findElementByXPath(ReadProperties.getOR(objectName + "-nameWA-xpathIOsAnd"));
		} else {
			try {
				if (driver.findElementByAccessibilityId(ReadProperties.getOR(objectName + "-accIDIOsAnd")).isDisplayed()) {
					return driver.findElementByAccessibilityId(ReadProperties.getOR(objectName + "-accIDIOsAnd"));
				} else {
					return null;
				}
			} catch (Exception e) {
				return null;
			}
		}
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
		System.out.println("[Appium port for portIOsAndroid: " + ReadProperties.getConfig("portIOsAndroid") + "]");
		//if(Objects.isNull(server)){
			Server server = OperationUtils.getServer(OperationUtils.ServerType.ANDROID, ReadProperties.getConfig("portIOsAndroid"));
			// Start Appium server
			server.startServer();
		//}
		// Setup for IOS Driver
		String platformVersion = ReadProperties.getConfig("platformVersionIOsAndroid");
		System.out.println("[platform Version: " + ReadProperties.getConfig("platformVersion") + "]");
		
		String deviceName = ReadProperties.getConfig("deviceNameIOsAndroid");
		System.out.println("[device Name: " + ReadProperties.getConfig("deviceNameIOsAndroid") + "]");
		String app = Constants.ROOT_PATH+ReadProperties.getConfig("appIOsAndroid");
		File appAndroid = new File(app);
		System.out.println("[App Name: " + ReadProperties.getConfig("appIOsAndroid") + "]");
		String appActivity = ReadProperties.getConfig("appActivityIOsAndroid");
		System.out.println("[App Activity: " + ReadProperties.getConfig("appActivityIOsAndroid") + "]");
		String appPackage = ReadProperties.getConfig("appPackageIOsAndroid");
		System.out.println("[App Package: " + ReadProperties.getConfig("appPackageIOsAndroid") + "]");
		driver = DriverUtils.capabilities(server, appAndroid,deviceName,platformVersion,appActivity,appPackage);

	}
	/*
	 * This is the main method which makes use of Open Excel for windows app
	 * method.
	 * 
	 * @author Nhan Nguyen
	 * 
	 * @version 1.0
	 * 
	 * @since 24-12-2018
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
	 * This is the main method which makes use of run each step on Excel for windows app
	 * method.
	 * 
	 * @author Nhan Nguyen
	 * 
	 * @version 1.0
	 * 
	 * @since 24-12-2018
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
	 * @since 24-12-2018
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
	 * This is the main method which makes use of run steps get from Excel for windows app
	 * method.
	 * 
	 * @author Nhan Nguyen
	 * 
	 * @version 1.0
	 * 
	 * @since 24-12-2018
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
		case "Open android IOs App":
			try {
				System.out.println("[Run this app Android on Mac]");
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
	
	public void clickOn(String element) throws Exception {
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
	 * This is the main method which makes use of clear Number And Dot from Excel for windows app
	 * method.
	 * 
	 * @author Nhan Nguyen
	 * 
	 * @version 1.0
	 * 
	 * @since 24-12-2018
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
	 * This is the main method which makes use of set value to Excel for windows app
	 * method.
	 * 
	 * @author Nhan Nguyen
	 * 
	 * @version 1.0
	 * 
	 * @since 24-12-2018
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
}