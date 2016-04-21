package edu.wesimulated.firstapp.model;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "projectData")
public class ProjectData {

	private List<PersonData> persons;
	private List<TaskData> tasks;
	
	@XmlElement(name = "person")
	public List<PersonData> getPersons() {
		return persons;
	}
	
	public void setPersons(List<PersonData> persons) {
		this.persons = persons;
	}
	
	@XmlElement(name = "task")
	public List<TaskData> getTasks() {
		return tasks;
	}
	
	public void setTasks(List<TaskData> tasks) {
		this.tasks = tasks;
	}
}
