package edu.wesimulated.firstapp.simulation.domain;

import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.wesimulated.simulation.runparameters.CompletableTask;

import edu.wesimulated.firstapp.simulation.domain.avature.project.MaintenanceTask;
import edu.wesimulated.firstapp.simulation.domain.avature.project.Meeting;
import edu.wesimulated.firstapp.simulation.domain.avature.project.Risk;
import edu.wesimulated.firstapp.simulation.hla.HlaProject;
import edu.wesimulated.firstapp.simulation.stochastic.EntryValue;
import edu.wesimulated.firstapp.simulation.stochastic.NumericallyModeledEntity;
import edu.wesimulated.firstapp.simulation.stochastic.StochasticVariableName;

public class Project implements CompletableTask, NumericallyModeledEntity {

	private ProjectContract contract;
	private ProjectWbs wbs;
	private ProjectRam ram;
	private HlaProject hlaProject;
	private List<Person> people;
	private List<Team> teams;
	private List<Task> tasks;
	private List<Role> roles;
	private Date startDate;
	private ManagementFramework managementFramework;

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
	public Map<StochasticVariableName, EntryValue> extractValues() {
		// TODO Auto-generated method stub
		return null;
	}

	public Collection<Risk> getRisks() {
		// TODO Auto-generated method stub
		return null;
	}

	public Collection<MaintenanceTask> getMaintenanceTasks() {
		// TODO Auto-generated method stub
		return null;
	}

	public Person pickRandomPerson() {
		List<Person> people = this.getPeople();
		int index = (int) Math.round(Math.random() * people.size());
		return people.get(index);
	}

	public List<Person> getPeople() {
		return this.people;
	}

	public Collection<Meeting> findRegularMeetings() {
		return this.managementFramework.getMeetings();
	}
}
