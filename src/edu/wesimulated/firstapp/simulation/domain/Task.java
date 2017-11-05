package edu.wesimulated.firstapp.simulation.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javafx.collections.ObservableList;

import com.wesimulated.simulation.hla.DateLogicalTime;
import com.wesimulated.simulation.runparameters.Completable;

import edu.wesimulated.firstapp.model.PersonData;
import edu.wesimulated.firstapp.model.SimulationEntity;
import edu.wesimulated.firstapp.model.TaskData;
import edu.wesimulated.firstapp.model.TaskDependencyData;
import edu.wesimulated.firstapp.model.TaskNeed;
import edu.wesimulated.firstapp.simulation.domain.mywork.role.RoleSimulator;
import edu.wesimulated.firstapp.simulation.domain.worktype.WorkType;
import edu.wesimulated.firstapp.simulation.hla.HlaTask;
import edu.wesimulated.firstapp.simulation.stochastic.EntryValue;
import edu.wesimulated.firstapp.simulation.stochastic.NumericallyModeledEntity;
import edu.wesimulated.firstapp.simulation.stochastic.ParametricAlgorithm;
import edu.wesimulated.firstapp.simulation.stochastic.StochasticVar;

public class Task implements NumericallyModeledEntity, Completable, Populatable {

	private HlaTask hlaTask;
	private Collection<Person> accountablePeople;
	private Collection<Person> informedPeople;
	private Collection<Person> responsiblePeople;
	private Collection<Person> consultedPeople;
	private Profile profile;
	/**
	 * Not all tasks need to have an end date, if a date has an end date it will
	 * be considered finished even if it is not completed // FIXME do this
	 */
	private Date endDate;
	private Date startDate;
	private Collection<TaskDependency> taskDependencies;
	private Map<TaskNeed, Number> costInHoursPerTaskNeed;
	private Map<TaskNeed, Number> workDoneInHoursPerTaskNeed;
	private String id;

	public static Task orderTasksAndGetFirst(List<Task> tasks, Comparator<Task> comparator) {
		if (tasks.size() > 0) {
			Collections.sort(tasks, comparator);
			return tasks.get(0);
		}
		return null;
	}

	private static void incorporatePersonToTask(PersonAssignerToTask personAssigner, ObservableList<PersonData> peopleData, Task task, SimulatorFactory factory) {
		for (PersonData person : peopleData) {
			personAssigner.assign((Person) factory.registerSimulationEntity(person), task);
		}
	}

	public Task() {
		this.workDoneInHoursPerTaskNeed = new HashMap<>();
	}

	public void registerAllAtributes(Date when) {
		this.hlaTask.registerAll(new DateLogicalTime(when));
	}

	@Override
	public boolean isCompleted() {
		for (Entry<TaskNeed, Number> entry : this.costInHoursPerTaskNeed.entrySet()) {
			Number workDoneForWorkType = this.workDoneInHoursPerTaskNeed.get(entry.getKey());
			if (workDoneForWorkType.longValue() < entry.getValue().longValue()) {
				return false;
			}
		}
		return true;
	}

	public boolean isCompleted(Role role) {
		for (TaskNeed taskNeed : role.getTaskNeedsThatCanBeMet()) {
			if (this.workDoneInHoursPerTaskNeed.get(taskNeed).doubleValue() < this.costInHoursPerTaskNeed.get(taskNeed).doubleValue()) {
				return false;
			}
		}
		return true;
	}

	protected Map<TaskNeed, Number> getWorkDoneInHoursPerTaskNeed() {
		return this.workDoneInHoursPerTaskNeed;
	}

	public void setName(String name, Date when) {
		this.profile.set(TaskCharacteristic.NAME, new EntryValue(EntryValue.Type.String, name));
		if (when == null) {
			this.hlaTask.registerName(name, new DateLogicalTime(when));
			// FIXME register pending changes to send to hla
		}
	}

	public void addAccountablePerson(Person newPerson) {
		this.getAccountablePeople().add(newPerson);
	}

	public Collection<Person> getAccountablePeople() {
		return this.accountablePeople;
	}

	public void addInformedPerson(Person newPerson) {
		this.getInformedPeople().add(newPerson);
	}

	public Collection<Person> getInformedPeople() {
		return this.informedPeople;
	}

	public void addResponsiblePerson(Person newPerson) {
		this.getResponsiblePeople().add(newPerson);
	}

	public Collection<Person> getResponsiblePeople() {
		return this.responsiblePeople;
	}

	public void addConsultedPerson(Person newPerson) {
		this.getConsultedPeople().add(newPerson);
	}

	public Collection<Person> getConsultedPeople() {
		return this.consultedPeople;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public void addTaskDependency(TaskDependency taskDependency) {
		this.getTaskDependencies().add(taskDependency);
	}

	private Collection<TaskDependency> getTaskDependencies() {
		return this.taskDependencies;
	}

	public void setCostInHours(Integer costInHours, TaskNeed taskNeed) {
		this.costInHoursPerTaskNeed.put(taskNeed, costInHours);
	}

	public Number getCostInHours(TaskNeed taskNeed) {
		return this.costInHoursPerTaskNeed.get(taskNeed);
	}

	public WorkType findWorkTypeForRole(RoleSimulator roleSimulator) {
		ParametricAlgorithm stochasticWorkType = ParametricAlgorithm.buildParametricAlgorithmForVar(StochasticVar.NEXT_WORK_TYPE_FOR_ROLE);
		stochasticWorkType.considerAll(roleSimulator.getAllNumericallyModeledEntities());
		return (WorkType) stochasticWorkType.findSample().getClassifictation();
	}

	@Override
	public Map<Characteristic, EntryValue> extractValues() {
		Map<Characteristic, EntryValue> returnValue = new HashMap<>();
		returnValue.putAll(profile.extractValues());
		/*
		 * TODO: Sobre que características poner sobre las personas, supongo que
		 * podria poner una nota promedio sobre accountable people, informed
		 * people, responsible people y consulted people y después poner por
		 * separado la cantidad
		 */
		// private Collection<Person> accountablePeople;
		// private Collection<Person> informedPeople;
		// private Collection<Person> responsiblePeople;
		// private Collection<Person> consultedPeople;
		// private LocalDate endDate;
		// private LocalDate startDate;
		// private Collection<TaskDependency> taskDependencies;
		// private Map<WorkType, Number> costInHoursPerWorkType;
		// private Map<WorkType, Number> workDoneInHoursPerWorkType;

		return returnValue;
	}

	public Collection<Person> getAllPeopleInvolved() {
		Collection<Person> allPeople = new ArrayList<>();
		allPeople.addAll(this.getAccountablePeople());
		allPeople.addAll(this.getConsultedPeople());
		allPeople.addAll(this.getInformedPeople());
		allPeople.addAll(this.getResponsiblePeople());
		return allPeople;
	}

	public boolean isPersonAssigned(Person person) {
		return this.getAllPeopleInvolved().contains(person);
	}

	public Profile getProfile() {
		return this.profile;
	}

	public void setHlaTask(HlaTask hlaTask) {
		this.hlaTask = hlaTask;
	}

	protected Map<TaskNeed, Number> getCostInHoursPerTaskNeed() {
		return this.costInHoursPerTaskNeed;
	}

	public Date getStartDate() {
		return this.startDate;
	}

	public Date getEndDate() {
		return this.endDate;
	}

	public boolean hasWorkForRole(Role role) {
		// TODO Vincular task needs con roles
		return false;
	}

	public boolean isReady() {
		return !this.hasUnmetDependencies() && !this.isCompleted();
	}

	private boolean hasUnmetDependencies() {
		boolean hasOneUnMet = false;
		for (TaskDependency taskDependency : this.taskDependencies) {
			if (!taskDependency.isSatisfied()) {
				hasOneUnMet = true;
			}
		}
		return hasOneUnMet;
	}

	public boolean hasNoOneAssigned() {
		return (this.getResponsiblePeople().size() + this.getAccountablePeople().size() + this.getInformedPeople().size() + this.getConsultedPeople().size()) == 0;
	}

	public long getTotalWorkDone() {
		return this.workDoneInHoursPerTaskNeed.values().stream().mapToLong((workDone) -> workDone.longValue()).sum();
	}

	@Override
	public String getIdentifier() {
		return this.id;
	}

	@Override
	public IdentifiableType getType() {
		return IdentifiableType.TASK;
	}

	@Override
	public void populateFrom(SimulationEntity simulationEntity, SimulatorFactory factory) {
		TaskData taskData = (TaskData) simulationEntity;
		Date when = null; // FIXME: find out when
		this.setName(taskData.getName(), when);
		Task.incorporatePersonToTask((Person person, Task task) -> task.addResponsiblePerson(person), taskData.getResponsiblePeople(), this, factory);
		Task.incorporatePersonToTask((Person person, Task task) -> task.addAccountablePerson(person), taskData.getAccountablePeople(), this, factory);
		Task.incorporatePersonToTask((Person person, Task task) -> task.addConsultedPerson(person), taskData.getConsultedPeople(), this, factory);
		Task.incorporatePersonToTask((Person person, Task task) -> task.addInformedPerson(person), taskData.getInformedPeople(), this, factory);
		this.setStartDate(taskData.getStartDate());
		for (TaskDependencyData taskDependency : taskData.getTaskDependencies()) {
			Task dependentTask = (Task) factory.registerSimulationEntity(taskDependency.getTask());
			this.addTaskDependency(new TaskDependency(dependentTask, taskDependency.getPrecedence()));
		}
		this.setCostInHours(taskData.getUnitsOfWork(), TaskNeed.Development);
	}

	@FunctionalInterface
	private interface PersonAssignerToTask {
		public void assign(Person person, Task task);
	}
}
