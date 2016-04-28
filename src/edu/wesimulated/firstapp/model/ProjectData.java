package edu.wesimulated.firstapp.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import edu.wesimulated.firstapp.persistence.XmlWbsNode;

@XmlRootElement(name = "projectData")
public class ProjectData {

	private List<PersonData> persons;
	private List<TaskData> tasks;
	private List<RoleData> roles;
	private XmlWbsNode wbsRootNode;

	@XmlElement(name = "person")
	public List<PersonData> getPersons() {
		if (persons == null) {
			this.persons = new ArrayList<>();
		}
		return persons;
	}

	public void setPersons(List<PersonData> persons) {
		this.persons = persons;
	}

	@XmlElement(name = "task")
	public List<TaskData> getTasks() {
		if (tasks == null) {
			this.tasks = new ArrayList<>();
		}
		return tasks;
	}

	public void setTasks(List<TaskData> tasks) {
		this.tasks = tasks;
	}

	@XmlElement(name = "rol")
	public List<RoleData> getRoles() {
		if (roles == null) {
			this.roles = new ArrayList<>();
		}
		return roles;
	}

	public void setRoles(List<RoleData> roles) {
		this.roles = roles;
	}

	@XmlElement(name = "wbs")
	public XmlWbsNode getWbsRootNode() {
		if (this.wbsRootNode == null) {
			this.wbsRootNode = new XmlWbsNode();
		}
		return wbsRootNode;
	}

	public void setWbsRootNode(XmlWbsNode wbsRootNode) {
		this.wbsRootNode = wbsRootNode;
	}

	public void registerMaxId() {
		for (TaskData task : this.getTasks()) {
			if (task.getId() > TaskData.MAX_ID) {
				TaskData.MAX_ID = task.getId();
			}
		}
	}
}
