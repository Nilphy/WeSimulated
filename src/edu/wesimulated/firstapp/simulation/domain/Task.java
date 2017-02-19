package edu.wesimulated.firstapp.simulation.domain;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

import com.wesimulated.simulation.hla.DateLogicalTime;

import edu.wesimulated.firstapp.model.WorkType;
import edu.wesimulated.firstapp.simulation.RoleSimulator;
import edu.wesimulated.firstapp.simulation.domain.worktype.TypeOfWork;
import edu.wesimulated.firstapp.simulation.hla.HlaTask;
import edu.wesimulated.firstapp.simulation.stochastic.EntryValue;
import edu.wesimulated.firstapp.simulation.stochastic.NumericallyModeledEntity;
import edu.wesimulated.firstapp.simulation.stochastic.ParametricAlgorithm;
import edu.wesimulated.firstapp.simulation.stochastic.StochasticVar;

public class Task implements NumericallyModeledEntity {

	private HlaTask hlaTask;

	public Task() {
		// TODO Auto-generated constructor stub
	}

	public boolean isCompleted() {
		// TODO Auto-generated method stub
		return false;
	}

	public void setHlaTask(HlaTask hlaTask) {
		this.hlaTask = hlaTask;
	}

	public boolean isCompleted(Role role) {
		// TODO Auto-generated method stub
		return false;
	}

	public void increaseWorkDone(long duration, Role role, Date when) {
		// TODO Fix calculation of work done
		this.hlaTask.registerWorkToDo(new Work(duration), new DateLogicalTime(when));
	}

	public void setName(String name) {
		// TODO Auto-generated method stub

	}

	public void addAccountablePerson(Person newPerson) {
		// TODO Auto-generated method stub

	}

	public void addAcountablePerson(Person person) {
		// TODO Auto-generated method stub
	}

	public void addInformedPerson(Person personParam) {
		// TODO Auto-generated method stub
	}

	public void addResponsiblePerson(Person personParam) {
		// TODO Auto-generated method stub
	}

	public void addConsultedPerson(Person personParam) {
		// TODO Auto-generated method stub
	}

	public void setEndDate(LocalDate endDate) {
		// TODO Auto-generated method stub

	}

	public void setStartDate(LocalDate startDate) {
		// TODO Auto-generated method stub

	}

	public void addTaskDependency(TaskDependency taskDependency) {
		// TODO Auto-generated method stub

	}

	public void setUnitsOfWork(Integer unitsOfWork) {
		// TODO Auto-generated method stub

	}

	public Double getLinesOfCodeToComplete() {
		// TODO Auto-generated method stub
		return null;
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
}
