package edu.wesimulated.firstapp.simulation.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.wesimulated.simulation.runparameters.Completable;

import edu.wesimulated.firstapp.model.ProjectData;
import edu.wesimulated.firstapp.model.SimulationEntity;
import edu.wesimulated.firstapp.persistence.XmlResponsibilityAssignment;
import edu.wesimulated.firstapp.persistence.XmlWbsNode;
import edu.wesimulated.firstapp.simulation.domain.Assignment.AssignmentType;
import edu.wesimulated.firstapp.simulation.hla.HlaProject;
import edu.wesimulated.firstapp.simulation.stochastic.EntryValue;
import edu.wesimulated.firstapp.simulation.stochastic.EntryValue.Type;
import edu.wesimulated.firstapp.simulation.stochastic.NumericallyModeledEntity;
import edu.wesimulated.firstapp.view.ThingsWithoutAUi;

public class Project implements Completable, NumericallyModeledEntity, Populatable {

	private ProjectContract contract;
	private ProjectWbs wbs;
	private ProjectRam ram;
	private HlaProject hlaProject;
	private List<Person> people;
	private List<Team> teams;
	private List<Task> tasks;
	private List<Role> roles;
	private Date startDate;
	private Date endDate;
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
		values.putAll(this.getManagementFramework().extractValues());
		values.put(ProjectCharacteristic.AMOUNT_PEOPLE, new EntryValue(Type.Long, this.people.size()));
		values.put(ProjectCharacteristic.AMOUNT_TEAMS, new EntryValue(Type.Long, this.teams.size()));
		values.put(ProjectCharacteristic.AMOUNT_TASKS, new EntryValue(Type.Long, this.tasks.size()));
		values.put(ProjectCharacteristic.AMOUNT_ROLES, new EntryValue(Type.Long, this.roles.size()));
		// FIXME ¿? consider more characteristics about the entities involved
		// (entities, tasks, etc)?
		// FIXME ¿? consider information about the start date?
		return values;
	}

	public Person pickRandomPerson() {
		List<Person> people = this.getPeople();
		int index = (int) Math.round(Math.random() * people.size());
		return people.get(index);
	}

	public List<Person> getPeople() {
		return this.people;
	}

	protected ManagementFramework getManagementFramework() {
		return this.managementFramework;
	}

	public void setManagementFramework(ManagementFramework managementFramework) {
		this.managementFramework = managementFramework;
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
		this.populateRam(projectData.getResponsibilityAssignments(), factory);
		this.setContract(ThingsWithoutAUi.buildContract(projectData.getStartDate(), projectData.getEndDate()));
		this.addTeam(ThingsWithoutAUi.getTeamInstance());
		this.setManagementFramework(ThingsWithoutAUi.buildManagementFramework());
	}

	private void populateRam(List<XmlResponsibilityAssignment> responsibilityAssignments, SimulatorFactory factory) {
		responsibilityAssignments.forEach((xmlRAM) -> {
			Task task = (Task) factory.getPopulatablesPool().getPopulatable(IdentifiableType.TASK, xmlRAM.getTaskId().toString());
			Role role = (Role) factory.getPopulatablesPool().getPopulatable(IdentifiableType.ROLE, xmlRAM.getRoleName());
			if (xmlRAM.getResponsible()) {
				this.addAssignments(task, role, task.getResponsiblePeople(), AssignmentType.RESPONSIBLE);
			} else if (xmlRAM.getAccountable()) {
				this.addAssignments(task, role, task.getAccountablePeople(), AssignmentType.ACCOUNTABLE);
			} else if (xmlRAM.getConsulted()) {
				this.addAssignments(task, role, task.getConsultedPeople(), AssignmentType.CONSULTED);
			} else {
				this.addAssignments(task, role, task.getInformedPeople(), AssignmentType.INFORMED);
			}
		});
	}

	public void addAssignments(Task task, Role role, Collection<Person> possiblePeople, AssignmentType assignmentType) {
		possiblePeople.forEach((person) -> {
			if (person.hasRole(role)) {
				Assignment newAssignment = new Assignment(task, person, role, assignmentType);
				task.addAssignment(newAssignment);
				role.addAssignment(newAssignment);
				this.getRam().addAssignment(newAssignment);
			}
		});
	}

	private ProjectRam getRam() {
		return this.ram;
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

	public void setContract(ProjectContract contract) {
		this.contract = contract;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public void setTeams(List<Team> teams) {
		this.teams = teams;
	}

	public List<Team> getTeams() {
		if (this.teams == null) {
			this.teams = new ArrayList<>();
		}
		return this.teams;
	}

	public void addTeam(Team team) {
		this.getTeams().add(team);
	}
}
