package edu.wesimulated.firstapp.simulation;

import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;

import com.wesimulated.simulation.runparameters.CompletableTask;

import edu.wesimulated.firstapp.simulation.domain.Person;
import edu.wesimulated.firstapp.simulation.domain.Task;

public class ProjectSimulator extends Simulator implements CompletableTask {

	private static ProjectSimulator instance;
	
	private Collection<Task> tasks;
	private Collection<Person> people;
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
		for (Task task : tasks) {
			if (!task.isCompleted()) {
				return false;
			}
		}
		return true;
	}

	public void addPerson(Person person) {
		if (this.people == null) {
			this.people = new LinkedList<>();
		}
		this.people.add(person);
	}

	public void addTask(Task taskSimulator) {
		if (this.tasks == null) {
			this.tasks = new LinkedList<>();
		}
		this.tasks.add(taskSimulator);
	}

	public Date getStartDate() {
		return this.startDate;
	}
}
