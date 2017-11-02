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

import com.wesimulated.simulation.hla.DateLogicalTime;
import com.wesimulated.simulation.runparameters.Completable;

import edu.wesimulated.firstapp.model.TaskNeed;
import edu.wesimulated.firstapp.simulation.domain.mywork.role.RoleSimulator;
import edu.wesimulated.firstapp.simulation.domain.worktype.WorkType;
import edu.wesimulated.firstapp.simulation.hla.HlaTask;
import edu.wesimulated.firstapp.simulation.stochastic.EntryValue;
import edu.wesimulated.firstapp.simulation.stochastic.NumericallyModeledEntity;
import edu.wesimulated.firstapp.simulation.stochastic.ParametricAlgorithm;
import edu.wesimulated.firstapp.simulation.stochastic.StochasticVar;

public class Task implements NumericallyModeledEntity, Completable {

	public static Task orderTasksAndGetFirst(List<Task> tasks, Comparator<Task> comparator) {
		if (tasks.size() > 0) {
			Collections.sort(tasks, comparator);
			return tasks.get(0);
		}
		return null;
	}

	private HlaTask hlaTask;
	private Collection<Person> accountablePeople;
	private Collection<Person> informedPeople;
	private Collection<Person> responsiblePeople;
	private Collection<Person> consultedPeople;
	private Profile profile;
	/**
	 * Last possible end
	 */
	private Date endDate;
	/**
	 * First possible start
	 */
	private Date startDate;
	private Collection<TaskDependency> taskDependencies;
	private Map<TaskNeed, Number> costInHoursPerTaskNeed;
	private Map<TaskNeed, Number> workDoneInHoursPerTaskNeed;

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

	public void increaseWorkDone(long duration, TaskNeed taskNeed, Date when) {
		Number currentWorkDone = this.workDoneInHoursPerTaskNeed.get(taskNeed);
		this.workDoneInHoursPerTaskNeed.put(taskNeed, new Long(duration + currentWorkDone.longValue()));
		if (when == null) {
			// this.hlaTask.registerWorkToDo(new Work(duration), new
			// DateLogicalTime(when));
			// FIXME register pending changes to send to hla
		}
	}

	public void setName(String name, Date when) {
		this.profile.set(TaskCharacteristic.Name, new EntryValue(EntryValue.Type.String, name));
		if (when == null) {
			this.hlaTask.registerName(name, new DateLogicalTime(when));
			// FIXME register pending changes to send to hla
		}
	}

	public void addAccountablePerson(Person newPerson) {
		this.getAccountablePeople().add(newPerson);
		// FIXME: register change in hla task
	}

	public Collection<Person> getAccountablePeople() {
		return this.accountablePeople;
	}

	public void addInformedPerson(Person newPerson) {
		this.getInformedPeople().add(newPerson);
		// FIXME: register change in hla task
	}

	public Collection<Person> getInformedPeople() {
		return this.informedPeople;
	}

	public void addResponsiblePerson(Person newPerson) {
		this.getResponsiblePeople().add(newPerson);
		// FIXME: register change in hla task
	}

	public Collection<Person> getResponsiblePeople() {
		return this.responsiblePeople;
	}

	public void addConsultedPerson(Person newPerson) {
		this.getConsultedPeople().add(newPerson);
		// FIXME: register change in hla task
	}

	public Collection<Person> getConsultedPeople() {
		return this.consultedPeople;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
		// FIXME: register change in hla task
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
		// FIXME: register change in hla task
	}

	public void addTaskDependency(TaskDependency taskDependency) {
		this.getTaskDependencies().add(taskDependency);
		// FIXME: register change in hla task
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
}
