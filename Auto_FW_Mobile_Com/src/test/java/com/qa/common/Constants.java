package com.qa.common;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.arnx.jsonic.JSON;

public class Constants {
	public static final String ROOT_PATH = System.getProperty("user.dir");
	public static final String ROOT_PATH_APP= System.getProperty("user.dir")+ File.separator + "src" + File.separator + "test"
			+ File.separator+"Apps";
	public static final String ROOT_PATH_TEST = ROOT_PATH + File.separator + "src" + File.separator + "test"
			+ File.separator;
	public static final File PROJECT_DIR = new File(ROOT_PATH);
	public static final String ROOT_PATH_EXCELS= System.getProperty("user.dir")+ File.separator + "src" + File.separator + "test"
			+ File.separator+"excels" + File.separator;
	public static final String REPORT_DIR=System.getProperty("user.dir") + File.separator + "reporting" + File.separator;
	public static final String ROOT_PATH_EXCELSOUT= System.getProperty("user.dir")+ File.separator + "reporting" + File.separator + "Excel" + File.separator;
	public static final String ROOT_SCREENSHOT = System.getProperty("user.dir")+ File.separator + "reporting" + File.separator +  "ScreenShot" + File.separator;
	public static final String ROOT_FAIL = System.getProperty("user.dir")+ File.separator + "reporting" + File.separator + "fail"+ File.separator;
	public static final String ROOT_PASS = System.getProperty("user.dir")+ File.separator + "reporting" + File.separator + "pass"+ File.separator;
	public static final String ROOT_EXTENT_FAIL="./fail/";
	public static final String ROOT_EXTENT_PASS="./pass/";
	//Jira
	public static final String JIRA_Path="/rest/api/2/issue/";
	public static String paramsCommentJira(String comment) {
		return "{\"body\":\"" + comment + "\"}".toString();
	}

	public static String paramsRemoveLabelJira(String label) {
		return "{\"update\":{ \"labels\":[{\"remove\":\"" + label + "\"}]}}".toString();
	}

	public static String paramsAddLabelJira(String label) {
		return "{\"update\":{ \"labels\":[{\"add\":\"" + label + "\"}]}}".toString();
	}
	/*
	 * This is the main method which makes use add Desciption onJIRA Excel for windows app
	 * method.
	 * 
	 * @author Nhan Nguyen
	 * 
	 * @version 1.0
	 * 
	 * @since 26-12-2018
	 * 
	 * @param args description.
	 * 
	 * @return none.
	 * 
	 * @exception none.
	 * 
	 * @see none
	 */
	public static String paramsAddDescriptionJira(String description) {
		// Get dấu nháy vào json
		String newVal = description.replaceAll("#", "\r\n #");

		Map<String, Map> map = new HashMap<>();
		Map<String, List<Map>> desc = new HashMap<>();
		Map<String, String> map3 = new HashMap<>();

		List<Map> maps = new ArrayList<>();
		map3.put("set", newVal);
		maps.add(map3);
		desc.put("description", maps);
		map.put("update", desc);

		System.out.println(JSON.encode(map));
		return JSON.encode(map);
	}
    public static final String ISSUE_TYPE = "User case";
    public static final String LABEL_PASS = "Pass";
    public static final String LABEL_FAIL = "Fail";
    public static final String TEAM_AUTO = "Automation";
    public static final String TEAM_AUTO_ID = "10200";
    public static final String TEAM_MANUAL = "Default";
    public static final String TEAM_MANUAL_ID = "10111";
    public static final String CUSTOMFIELD="customfield_10102";
    public static final String LABEL_YES = "yes";
    public static final String LABEL_NO = "no";
    public static final String LABEL_PENDING_ISSUES = "Pending";
    public static final String LABEL_YELLOW = "YELLOW";
    public static final String LABEL_RED = "RED";
    public static final String LABEL_GREEN = "GREEN";
    
}
