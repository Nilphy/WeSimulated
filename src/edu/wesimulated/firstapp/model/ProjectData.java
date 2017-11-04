package edu.wesimulated.firstapp.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import edu.wesimulated.firstapp.persistence.XmlResponsibilityAssignment;
import edu.wesimulated.firstapp.persistence.XmlWbsNode;
import edu.wesimulated.firstapp.simulation.ProjectSimulatorBuilder.ProjectSimulatorType;
import edu.wesimulated.firstapp.simulation.SimulatorType;

@XmlRootElement(name = "projectData")
public class ProjectData implements SimulatedEntity {

	private List<PersonData> people;
	private List<TaskData> tasks;
	private List<RoleData> roles;
	private XmlWbsNode wbsRootNode;
	private List<XmlResponsibilityAssignment> xmlRam;

	@XmlElement(name = "person")
	public List<PersonData> getPeople() {
		if (people == null) {
			this.people = new ArrayList<>();
		}
		return people;
	}

	public void setPeople(List<PersonData> people) {
		this.people = people;
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

	public void setResponsibilityAssignments(List<XmlResponsibilityAssignment> xmlRam) {
		this.xmlRam = xmlRam;
	}

	@XmlElement(name = "ram")
	public List<XmlResponsibilityAssignment> getResponsibilityAssignments() {
		return this.xmlRam;
	}

	@Override
	public SimulatorType calculateSimulatorType() {
		return ProjectSimulatorType.MY_WORK;
	}
}
