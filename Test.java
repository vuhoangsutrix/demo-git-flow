private By getByElement(String objectName) throws Exception {
		MASTER____________NEW LINE
		if (null != ReadProperties.get(objectName + "-id")) {
			return (By.id(ReadProperties.get(objectName + "-id")));
		} else if (null != ReadProperties.get(objectName + "-xp")) {
			return (By.xpath(ReadProperties.get(objectName + "-xp")));
		} else if (null != ReadProperties.get(objectName + "-css")) {
			return (By.cssSelector(ReadProperties.get(objectName + "-css")));
		} else if (null != ReadProperties.get(objectName + "-cln")) {
			return (By.className(ReadProperties.get(objectName + "-cln")));
		} else if (null != ReadProperties.get(objectName + "-link")) {
			return (By.linkText(ReadProperties.get(objectName + "-link")));
		} else if (null != ReadProperties.get(objectName + "-plink")) {
			return (By.partialLinkText(ReadProperties.get(objectName + "-plink")));
		} else if (null != ReadProperties.get(objectName + "-tag")) {
			DEV01____________EDIT LINE AFTER MERGE 2
			DEV01____________EDIT LINE AFTER MERGE 2
			DEV01____________EDIT LINE AFTER MERGE 2
			MASTER____________NEW LINE AFTER MERGE 2
		} else if (null != ReadProperties.get(objectName + "-name")) {
			MASTER____________EDIT LINE AFTER MERGE
		} else {
			DEV01____________NEW LINE AFTER MERGE
		}