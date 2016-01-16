package edu.wesimulated.firstapp.simulation;

import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;

import com.wesimulated.simulation.runparameters.CompletableTask;

public class ProjectSimulator implements CompletableTask {

	private static ProjectSimulator instance;
	
	private Collection<TaskSimulator> tasks;
	private Collection<PersonSimulator> people;
	private Date startDate;
	
	private ProjectSimulator() {
		this.startDate = new Date();
	}
	
	public static ProjectSimulator getInstance() {
		if (instance == null) {
			instance = new ProjectSimulator();
		}
		return instance;
	}

	@Override
	public boolean isCompleted() {
		for (TaskSimulator task : tasks) {
			if (!task.isCompleted()) {
				return false;
			}
		}
		return true;
	}

	public void assignTasks() {
		for (TaskSimulator taskSimulator : this.tasks) {
			for (PersonSimulator personSimulator : people) {
				personSimulator.addTask(taskSimulator);
			}
		}
	}

	public void addPerson(PersonSimulator personSimulator) {
		if (this.people == null) {
			this.people = new LinkedList<>();
		}
		this.people.add(personSimulator);
	}

	public void addTask(TaskSimulator taskSimulator) {
		if (this.tasks == null) {
			this.tasks = new LinkedList<>();
		}
		this.tasks.add(taskSimulator);
	}

	public Date getStartDate() {
		return this.startDate;
	}
}
