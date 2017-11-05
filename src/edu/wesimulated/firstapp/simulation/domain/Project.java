package edu.wesimulated.firstapp.simulation.domain;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.wesimulated.simulation.runparameters.Completable;

import edu.wesimulated.firstapp.model.ProjectData;
import edu.wesimulated.firstapp.model.SimulationEntity;
import edu.wesimulated.firstapp.persistence.XmlWbsNode;
import edu.wesimulated.firstapp.simulation.domain.mywork.project.MaintenanceTask;
import edu.wesimulated.firstapp.simulation.domain.mywork.project.Meeting;
import edu.wesimulated.firstapp.simulation.domain.mywork.project.Risk;
import edu.wesimulated.firstapp.simulation.hla.HlaProject;
import edu.wesimulated.firstapp.simulation.stochastic.EntryValue;
import edu.wesimulated.firstapp.simulation.stochastic.EntryValue.Type;
import edu.wesimulated.firstapp.simulation.stochastic.NumericallyModeledEntity;

public class Project implements Completable, NumericallyModeledEntity, Populatable {

	private ProjectContract contract;
	private ProjectWbs wbs;
	private ProjectRam ram;
	private HlaProject hlaProject;
	private List<Person> people;
	private List<Team> teams;
	private List<Task> tasks;
	private List<Role> roles;
	private List<Risk> risks;
	private List<MaintenanceTask> maintenanceTasks;
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
		// FIXME ¿? consider more characteristics about the entities involved
		// (entities, tasks, etc)?
		// FIXME ¿? consider information about the start date?
		return values;
	}

	public Collection<Risk> getRisks() {
		return this.risks;
	}

	public Collection<MaintenanceTask> getMaintenanceTasks() {
		return this.maintenanceTasks;
	}

	public edu.wesimulated.firstapp.simulation.domain.Person pickRandomPerson() {
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

	protected List<Task> getTasks() {
		return this.tasks;
	}

	public void addPerson(Person person) {
		if (this.people == null) {
			this.people = new LinkedList<>();
		}
		this.people.add(person);
	}

	public void addTask(Task task) {
		if (this.tasks == null) {
			this.tasks = new LinkedList<>();
		}
		this.tasks.add(task);
	}

	public void setHlaProject(HlaProject hlaProject) {
		this.hlaProject = hlaProject;
	}

	public void addRole(Role role) {
		if (this.roles == null) {
			this.roles = new LinkedList<>();
		}
		this.roles.add(role);
	}

	@Override
	public void populateFrom(SimulationEntity simulationEntity, SimulatorFactory factory) {
		ProjectData projectData = (ProjectData) simulationEntity;
		projectData.getPeople().forEach((personData) -> {
			Person person = (Person) factory.registerSimulationEntity(personData);
			this.addPerson(person);
		});
		projectData.getTasks().stream().forEach((taskData) -> {
			Task task = (Task) factory.registerSimulationEntity(taskData);
			this.addTask(task);
		});
		projectData.getRoles().stream().forEach((roleData) -> {
			Role role = (Role) factory.registerSimulationEntity(roleData);
			this.addRole(role);
		});
		this.populateWbs(projectData.getWbsRootNode(), factory);
		/*
	private List<XmlResponsibilityAssignment> xmlRam;
		 */

		/*
		 * ate ProjectContract contract;
	private ProjectRam ram;
	private HlaProject hlaProject;
	private List<Team> teams;
	private List<Risk> risks;
	private List<MaintenanceTask> maintenanceTasks;
	private Date startDate;
	private ManagementFramework managementFramework;
		 */
		
	}

	private void populateWbs(XmlWbsNode wbsRootNode, SimulatorFactory factory) {
		this.wbs = new ProjectWbs();
		this.wbs.populateFrom(wbsRootNode, factory);
	}

	@Override
	public String getIdentifier() {
		return this.wbs.getFirstNodeName();
	}

	@Override
	public IdentifiableType getType() {
		return IdentifiableType.PROJECT;
	}
}
