package edu.wesimulated.firstapp.simulation.domain;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.wesimulated.simulation.runparameters.CompletableTask;

import edu.wesimulated.firstapp.simulation.domain.avature.project.MaintenanceTask;
import edu.wesimulated.firstapp.simulation.domain.avature.project.Meeting;
import edu.wesimulated.firstapp.simulation.domain.avature.project.Risk;
import edu.wesimulated.firstapp.simulation.hla.HlaProject;
import edu.wesimulated.firstapp.simulation.stochastic.EntryValue;
import edu.wesimulated.firstapp.simulation.stochastic.EntryValue.Type;
import edu.wesimulated.firstapp.simulation.stochastic.NumericallyModeledEntity;

public class Project implements CompletableTask, NumericallyModeledEntity {

	private static Task orderTasksAndGetFirst(List<Task> tasks, Comparator<Task> comparator) {
		if (tasks.size() > 0) {
			Collections.sort(tasks, comparator);
			return tasks.get(0);
		}
		return null;
	}

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

	public Date findStartDateToWork(Person person) throws MisconfiguredProject {
		List<Task> tasksAssignedToPerson = this.findTasksAssignedToPerson(person);
		if (tasksAssignedToPerson.size() > 0) {
			Task firstTaskAssignedToPerson = orderTasksAndGetFirst(tasksAssignedToPerson, (Task first, Task second) -> {
				return first.getStartDate().compareTo(second.getStartDate());
			});
			return firstTaskAssignedToPerson.getStartDate();
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
		throw new MisconfiguredProject("PersonWithoutAssignations");
		this.tasks.add(taskSimulator);
	}

	public void setHlaProject(HlaProject hlaProject) {
		this.hlaProject = hlaProject;
	}

	public void addRole(Role role) {
		// TODO Auto-generated method stub
	}

	private List<Task> findTasksAssignedToPerson(Person person) {
		return this.getTasks().stream().filter((task) -> {
			if (task.isPersonAssigned(person)) {
				return true;
			};
			return false;
		}).collect(Collectors.toList());
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
	public Map<Characteristic, EntryValue> extractValues() {
		Map<Characteristic, EntryValue> values = new HashMap<>();
		values.putAll(this.contract.extractValues());
		values.putAll(this.wbs.extractValues());
		values.putAll(this.managementFramework.extractValues());
		values.put(ProjectCharacteristic.AMOUNT_PEOPLE, new EntryValue(Type.Long, this.people.size()));
		values.put(ProjectCharacteristic.AMOUNT_TEAMS, new EntryValue(Type.Long, this.teams.size()));
		values.put(ProjectCharacteristic.AMOUNT_TASKS, new EntryValue(Type.Long, this.tasks.size()));
		values.put(ProjectCharacteristic.AMOUNT_ROLES, new EntryValue(Type.Long, this.roles.size()));
		// FIXME ¿? consider more characteristics about the entities involved (entities, tasks, etc)?
		// FIXME ¿? consider information about the start date?
		return values;
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

	private List<Task> getTasks() {
		return this.tasks;
	}

}
