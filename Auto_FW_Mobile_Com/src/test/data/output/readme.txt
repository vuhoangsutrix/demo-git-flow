String comment = null;
Date da = new Date();
String timeToCheck = df.format(da);

			// update excel
			row = sheetCurrent.getRow(key);
			Cell cellActualResult = row.getCell(9);
			Cell cellStatus = row.getCell(10);
			Cell cellExecuteBy = row.getCell(11);
			Cell cellExecuteDate = row.getCell(12);
			if (!messageAlert.equals("")) {
				comment = "Hi,\\r\\n\\n" + "This usercase is NOT Ok at " + timeToCheck + "\\r\\n" + "Actual result: "
						+ messageAlert + "\\r\\n" + "Please see attachment("
						+ (jiraID + " " + timeToCheck).replaceAll(":", "").replaceAll(" ", "_") + ".png"
						+ ") for more details\\r\\n" + "It is checked by Automation team.\\r\\n\\n" + "Thanks";
				// update excel
				if (cellActualResult != null) {
					cellActualResult.removeCellComment();
				} else {
					cellActualResult = row.createCell(9);
				}
				cellActualResult.setCellValue(messageAlert);

				if (cellStatus != null) {
					cellStatus.removeCellComment();
				} else {
					cellStatus = row.createCell(10);
				}
				cellStatus.setCellValue("Fail");

				if (cellExecuteBy != null) {
					cellExecuteBy.removeCellComment();
				} else {
					cellExecuteBy = row.createCell(11);
				}
				cellExecuteBy.setCellValue(ReadProperties.getConfigSelenium("jiraUserName"));

				if (cellExecuteDate != null) {
					cellExecuteDate.removeCellComment();
				} else {
					cellExecuteDate = row.createCell(12);
				}
				cellExecuteDate.setCellValue(timeToCheck);

				if (strSteps == null || strSteps.equals("")) {
					strSteps = "Not have Step";
				}
				System.out.println("strSteps:" + strSteps);

				// update jira
				try {
					ReadAPI.postAPI(
							ReadProperties.getConfigSelenium("jiraURL") + "/rest/api/2/issue/" + jiraID + "/comment",
							paramsCommentJira(comment), ReadProperties.getConfigSelenium("jiraUserName"),
							ReadProperties.getConfigSelenium("jiraPassword"));
					ReadAPI.putAPI(ReadProperties.getConfigSelenium("jiraURL") + "/rest/api/2/issue/" + jiraID,
							paramsAddDescriptionJira(changeTestStep(strSteps)),
							ReadProperties.getConfigSelenium("jiraUserName"),
							ReadProperties.getConfigSelenium("jiraPassword"));

					ReadAPI.putAPI(ReadProperties.getConfigSelenium("jiraURL") + "/rest/api/2/issue/" + jiraID,
							paramsRemoveLabelJira(Constant.LABEL_PASS),
							ReadProperties.getConfigSelenium("jiraUserName"),
							ReadProperties.getConfigSelenium("jiraPassword"));
					ReadAPI.putAPI(ReadProperties.getConfigSelenium("jiraURL") + "/rest/api/2/issue/" + jiraID,
							paramsAddLabelJira(Constant.LABEL_FAIL), ReadProperties.getConfigSelenium("jiraUserName"),
							ReadProperties.getConfigSelenium("jiraPassword"));
					// chup man hinh
					takeScreenShort((jiraID + " " + timeToCheck).replaceAll(":", ""), myScenario);
					uploadAttachment("", "", jiraID, data.getOutputScreenshot(
							(jiraID + " " + timeToCheck).replaceAll(":", "").replaceAll(" ", "_") + ".png"));
				} catch (KeyManagementException | NoSuchAlgorithmException | InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			if (messageAlert.equals("")) {
				comment = "Hi,\\r\\n\\n" + "This usercase is Ok at " + df.format(da) + "\\r\\n"
						+ "It is checked by Automation team\\r\\n\\n" + "Thanks";
				if (cellActualResult != null) {
					cellActualResult.removeCellComment();
				} else {
					cellActualResult = row.createCell(9);
				}
				cellActualResult.setCellValue(messageAlert);

				if (cellStatus != null) {
					cellStatus.removeCellComment();
				} else {
					cellStatus = row.createCell(10);
				}
				cellStatus.setCellValue("Pass");

				if (cellExecuteBy != null) {
					cellExecuteBy.removeCellComment();
				} else {
					cellExecuteBy = row.createCell(11);
				}
				cellExecuteBy.setCellValue(ReadProperties.getConfigSelenium("jiraUserName"));

				if (cellExecuteDate != null) {
					cellExecuteDate.removeCellComment();
				} else {
					cellExecuteDate = row.createCell(12);
				}
				cellExecuteDate.setCellValue(timeToCheck);

				if (strSteps == null || strSteps.equals("")) {
					strSteps = "Not have Step";
				}
				System.out.println("strSteps:" + strSteps);

				// update jira
				try {
					ReadAPI.postAPI(
							ReadProperties.getConfigSelenium("jiraURL") + "/rest/api/2/issue/" + jiraID + "/comment",
							paramsCommentJira(comment), ReadProperties.getConfigSelenium("jiraUserName"),
							ReadProperties.getConfigSelenium("jiraPassword"));
					ReadAPI.putAPI(ReadProperties.getConfigSelenium("jiraURL") + "/rest/api/2/issue/" + jiraID,
							paramsAddDescriptionJira(changeTestStep(strSteps)),
							ReadProperties.getConfigSelenium("jiraUserName"),
							ReadProperties.getConfigSelenium("jiraPassword"));
					ReadAPI.putAPI(ReadProperties.getConfigSelenium("jiraURL") + "/rest/api/2/issue/" + jiraID,
							paramsRemoveLabelJira(Constant.LABEL_FAIL),
							ReadProperties.getConfigSelenium("jiraUserName"),
							ReadProperties.getConfigSelenium("jiraPassword"));
					ReadAPI.putAPI(ReadProperties.getConfigSelenium("jiraURL") + "/rest/api/2/issue/" + jiraID,
							paramsAddLabelJira(Constant.LABEL_PASS), ReadProperties.getConfigSelenium("jiraUserName"),
							ReadProperties.getConfigSelenium("jiraPassword"));
				} catch (KeyManagementException | NoSuchAlgorithmException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			fis.close();

			// Update excel
			FileOutputStream streamOut = new FileOutputStream(new File(strExcelTestCasePath));
			workbook.write(streamOut);
			streamOut.close();
		}