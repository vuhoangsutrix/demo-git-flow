package data.api.testing;

import gherkin.deps.com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import com.qa.common.Constants;




public class JiraTicketsList {

	public String expand;
	public int startAt;
	public int maxResults;
	public int total;
	public List<Issue> issues;

	public static JiraTicketsList getJiraTicketsList(String stream) {
		Gson gson = new Gson();
		return gson.fromJson(stream, JiraTicketsList.class);
	}

	public List<Issue> getUserCasesList() {
		List<Issue> list = new ArrayList<>();
		for (Issue item : issues) {
			if (item.getIssueType()) {
				list.add(item);
			}
		}
		return list;
	}

	public class Issuetype {
		public String self;
		public String id;
		public String description;
		public String iconUrl;
		public String name;
		public boolean subtask;
		public int avatarId;

		public String getSelf() {
			return self;
		}

		public void setSelf(String self) {
			this.self = self;
		}

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		public String getIconUrl() {
			return iconUrl;
		}

		public void setIconUrl(String iconUrl) {
			this.iconUrl = iconUrl;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public boolean isSubtask() {
			return subtask;
		}

		public void setSubtask(boolean subtask) {
			this.subtask = subtask;
		}

		public int getAvatarId() {
			return avatarId;
		}

		public void setAvatarId(int avatarId) {
			this.avatarId = avatarId;
		}
	}

	public class StatusCategory {
		public String self;
		public int id;
		public String key;
		public String colorName;
		public String name;

		public String getSelf() {
			return self;
		}

		public void setSelf(String self) {
			this.self = self;
		}

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public String getKey() {
			return key;
		}

		public void setKey(String key) {
			this.key = key;
		}

		public String getColorName() {
			return colorName;
		}

		public void setColorName(String colorName) {
			this.colorName = colorName;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
	}

	public class Status {
		public String self;
		public String description;
		public String iconUrl;
		public String name;
		public String id;
		public StatusCategory statusCategory;

		public String getSelf() {
			return self;
		}

		public void setSelf(String self) {
			this.self = self;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		public String getIconUrl() {
			return iconUrl;
		}

		public void setIconUrl(String iconUrl) {
			this.iconUrl = iconUrl;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public StatusCategory getStatusCategory() {
			return statusCategory;
		}

		public void setStatusCategory(StatusCategory statusCategory) {
			this.statusCategory = statusCategory;
		}
	}

	public class Fields {
		public Issuetype issuetype;
		public String description;
		public List<String> labels;
		public Status status;

		public Issuetype getIssuetype() {
			return issuetype;
		}

		public void setIssuetype(Issuetype issuetype) {
			this.issuetype = issuetype;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		public List<String> getLabels() {
			return labels;
		}

		public void setLabels(List<String> labels) {
			this.labels = labels;
		}

		public Status getStatus() {
			return status;
		}

		public void setStatus(Status status) {
			this.status = status;
		}
	}

	public class Issue {
		public String expand;
		public String id;
		public String self;
		public String key;
		public Fields fields;

		public String getExpand() {
			return expand;
		}

		public void setExpand(String expand) {
			this.expand = expand;
		}

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getSelf() {
			return self;
		}

		public void setSelf(String self) {
			this.self = self;
		}

		public String getKey() {
			return key;
		}

		public void setKey(String key) {
			this.key = key;
		}

		public Fields getFields() {
			return fields;
		}

		public void setFields(Fields fields) {
			this.fields = fields;
		}

		public boolean getIssueType() {
			boolean found = false;
			if (fields != null && fields.getIssuetype() != null && fields.getIssuetype().getName() != null) {
				if (fields.getIssuetype().getName().equalsIgnoreCase(Constants.ISSUE_TYPE)) {
					found = true;
				}
			}
			return found;
		}

		public String getDescription() {
			String des = "";
			if (fields != null && fields.getDescription() != null)
				des = fields.getDescription();
			return des;
		}
	}
}
