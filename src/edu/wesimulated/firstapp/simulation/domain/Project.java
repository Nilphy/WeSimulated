package edu.wesimulated.firstapp.simulation.domain;

import java.util.Collection;

import com.wesimulated.simulation.runparameters.CompletableTask;

import edu.wesimulated.firstapp.simulation.hla.HlaProject;

public class Project implements CompletableTask {

	private Collection<Task> tasks;
	private Collection<Person> people;
	private ProjectCalendar calendar;
	private ProjectWbs wbs;
	private ProjectGantt gantt;
	private ProjectRam ram;
	private HlaProject HlaProject;
	
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
		this.people.add(person);
	}
}
