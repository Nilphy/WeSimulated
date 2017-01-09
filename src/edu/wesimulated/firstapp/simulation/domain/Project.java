package edu.wesimulated.firstapp.simulation.domain;

import java.util.Collection;
import java.util.Date;
import java.util.Map;

import com.wesimulated.simulation.runparameters.CompletableTask;

import edu.wesimulated.firstapp.simulation.hla.HlaProject;
import edu.wesimulated.firstapp.simulation.stochastic.EntryValue;
import edu.wesimulated.firstapp.simulation.stochastic.NumericallyModeledEntity;

public class Project implements CompletableTask, NumericallyModeledEntity {

	private ProjectContract contract;
	private ProjectWbs wbs;
	private ProjectRam ram;
	private HlaProject hlaProject;
	private Collection<Person> people;
	private Collection<Task> tasks;

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

	public void addRole(Role role) {
		// TODO Auto-generated method stub
	}

	public Date findStartDateToWorkForRole(Role role, Person person) {
		// TODO Auto-generated method stub
		return null;
	}

	public Date findWorkEndOfRole(Role role) {
		// TODO Auto-generated method stub
		return null;
	}

	public Task findTaskToWorkOn(Date day, Person person, Role role) {
		// TODO Auto-generated method stub
		return null;
	}

	public Task findTaskToWorkForRole(Role role) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, EntryValue> extractValues() {
		// TODO Auto-generated method stub
		return null;
	}
}
