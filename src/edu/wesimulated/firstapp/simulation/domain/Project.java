package edu.wesimulated.firstapp.simulation.domain;

import java.util.Collection;

import com.wesimulated.simulation.runparameters.CompletableTask;

import edu.wesimulated.firstapp.simulation.hla.HlaProject;
import edu.wesimulated.firstapp.simulation.stochastic.NumericallyModeledEntity;

public class Project implements CompletableTask, NumericallyModeledEntity {

	private Collection<Task> tasks;
	private Collection<Person> people;
	private ProjectContract contract;
	private ProjectCalendar calendar;
	private ProjectWbs wbs;
	private ProjectGantt gantt;
	private ProjectRam ram;
	private HlaProject hlaProject;
	
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

	public void addTask(Task task) {
		this.tasks.add(task);
	}

	public void setHlaProject(HlaProject hlaProject) {
		this.hlaProject = hlaProject;
	}
}
