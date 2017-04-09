package edu.wesimulated.firstapp.simulation.domain;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.wesimulated.simulation.hla.DateLogicalTime;
import com.wesimulated.simulation.runparameters.CompletableTask;

import edu.wesimulated.firstapp.model.TaskNeed;
import edu.wesimulated.firstapp.simulation.RoleSimulator;
import edu.wesimulated.firstapp.simulation.domain.worktype.WorkType;
import edu.wesimulated.firstapp.simulation.hla.HlaTask;
import edu.wesimulated.firstapp.simulation.stochastic.EntryValue;
import edu.wesimulated.firstapp.simulation.stochastic.NumericallyModeledEntity;
import edu.wesimulated.firstapp.simulation.stochastic.ParametricAlgorithm;
import edu.wesimulated.firstapp.simulation.stochastic.StochasticVar;
import edu.wesimulated.firstapp.simulation.stochastic.StochasticVariableName;

public class Task implements NumericallyModeledEntity, CompletableTask {

	private HlaTask hlaTask;
	private Collection<Person> accountablePeople;
	private Collection<Person> informedPeople;
	private Collection<Person> responsiblePeople;
	private Collection<Person> consultedPeople;
	private Profile profile;
	private LocalDate endDate;
	private LocalDate startDate;
	private Collection<TaskDependency> taskDependencies;
	private Map<TaskNeed, Number> costInHoursPerTaskNeed;
	private Map<TaskNeed, Number> workDoneInHoursPerTaskNeed;

	public Task() {
		this.workDoneInHoursPerTaskNeed = new HashMap<>();
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
			this.hlaTask.registerWorkToDo(new Work(duration), new DateLogicalTime(when));
			// FIXME register pending changes to send to hla
		}
	}

	public void extendDuration(double scale) {
		for (Entry<TaskNeed, Number> entry : this.costInHoursPerTaskNeed.entrySet()) {
			Double actualCostInHours = entry.getValue().doubleValue();
			Double escalatedCostInHours = actualCostInHours + actualCostInHours * scale;
			entry.setValue(escalatedCostInHours);
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

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
		// FIXME: register change in hla task
	}

	public void setStartDate(LocalDate startDate) {
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
		ParametricAlgorithm stochasticWorkType = ParametricAlgorithm.buildParametricAlgorithmForVar(StochasticVar.WorkTypeForRole);
		stochasticWorkType.considerAll(roleSimulator.getAllNumericallyModeledEntities());
		return (WorkType) stochasticWorkType.findSample().getClassifictation();
	}

	public long findDurationOfWorkSlab(RoleSimulator roleSimulator) {
		ParametricAlgorithm timeOfWorkSlab = ParametricAlgorithm.buildParametricAlgorithmForVar(StochasticVar.TimeOfWorkSlab);
		// TODO The person will have a list of all interruptions it has had
		// given the priority of this task and the ones that has interrupted it
		// The duration of this task could be calculated
		return timeOfWorkSlab.findSample().getPrediction().getValue().longValue();
	}

	@Override
	public Map<StochasticVariableName, EntryValue> extractValues() {
		Map<StochasticVariableName, EntryValue> returnValue = new HashMap<>();
		returnValue.putAll(profile.extractValues());
		/*
		 * TODO: Sobre que caracter�sticas poner sobre las personas, supongo que
		 * podria poner una nota promedio sobre accountable people, informed
		 * people, responsible people y consulted people y despu�s poner por
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

	public Collection<NumericallyModeledEntity> getAllRelatedNumericallyModeledEntities() {
		// TODO Auto-generated method stub
		return null;
	}

	public Profile getProfile() {
		return this.profile;
	}

	public void setHlaTask(HlaTask hlaTask) {
		this.hlaTask = hlaTask;
	}
}
