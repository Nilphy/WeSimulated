package edu.wesimulated.firstapp.simulation.domain;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.wesimulated.simulation.hla.DateLogicalTime;
import com.wesimulated.simulation.runparameters.CompletableTask;

import edu.wesimulated.firstapp.model.WorkType;
import edu.wesimulated.firstapp.simulation.RoleSimulator;
import edu.wesimulated.firstapp.simulation.domain.worktype.TypeOfWork;
import edu.wesimulated.firstapp.simulation.hla.HlaTask;
import edu.wesimulated.firstapp.simulation.stochastic.EntryValue;
import edu.wesimulated.firstapp.simulation.stochastic.NumericallyModeledEntity;
import edu.wesimulated.firstapp.simulation.stochastic.ParametricAlgorithm;
import edu.wesimulated.firstapp.simulation.stochastic.StochasticVar;

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
	private Map<WorkType, Number> costInHoursPerWorkType;
	private Map<WorkType, Number> workDoneInHoursPerWorkType;

	public Task() {
		this.workDoneInHoursPerWorkType = new HashMap<>();
	}

	@Override
	public boolean isCompleted() {
		for (Entry<WorkType, Number> entry : this.costInHoursPerWorkType.entrySet()) {
			Number workDoneForWorkType = this.workDoneInHoursPerWorkType.get(entry.getKey());
			if (workDoneForWorkType.longValue() < entry.getValue().longValue()) {
				return false;
			}
		}
		return true;
	}

	public boolean isCompleted(Role role) {
		return this.workDoneInHoursPerWorkType.get(role.getWorkType()).doubleValue() >= this.costInHoursPerWorkType.get(role.getWorkType()).doubleValue();
	}

	public void increaseWorkDone(long duration, Role role, Date when) {
		Number currentWorkDone = this.workDoneInHoursPerWorkType.get(role.getWorkType());
		this.workDoneInHoursPerWorkType.put(role.getWorkType(), new Long(duration + currentWorkDone.longValue()));
	}

	public void setName(String name) {
		// TODO Auto-generated method stub

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

	public void setCostInHours(Integer costInHours, WorkType workType) {
		this.costInHoursPerWorkType.put(workType, costInHours);
	}

	public Double getWorkDoneToComplete(WorkType key) {
		// TODO Auto-generated method stub
		return null;
	}

	public TypeOfWork findTypeOfTaskToWorkForRole(RoleSimulator roleSimulator) {
		ParametricAlgorithm typeOfWork = ParametricAlgorithm.buildParametricAlgorithmForVar(StochasticVar.TypeOfTaskToWorkForRole);
		typeOfWork.considerAll(roleSimulator.getAllNumericallyModeledEntities());
		return (TypeOfWork) typeOfWork.findSample().getClassifictation();
	}

	public long findDurationOfWorkSlab(RoleSimulator roleSimulator) {
		ParametricAlgorithm timeOfWorkSlab = ParametricAlgorithm.buildParametricAlgorithmForVar(StochasticVar.TimeOfWorkSlab);
		// TODO The person will have a list of all interruptions it has had
		// given the priority of this task and the ones that has interrupted it
		// The duration of this task could be calculated
		return timeOfWorkSlab.findSample().getPrediction().getValue().longValue();
	}

	@Override
	public Map<String, EntryValue> extractValues() {
		// TODO Auto-generated method stub
		return null;
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
