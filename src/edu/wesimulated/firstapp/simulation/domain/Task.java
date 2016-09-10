package edu.wesimulated.firstapp.simulation.domain;

import java.time.LocalDate;
import java.util.Date;

import com.wesimulated.simulation.hla.DateLogicalTime;

import edu.wesimulated.firstapp.model.WorkType;
import edu.wesimulated.firstapp.simulation.hla.HlaTask;
import edu.wesimulated.firstapp.simulation.stochastic.NumericallyModeledEntity;
import edu.wesimulated.firstapp.simulation.stochastic.TaskStochasticVariableFactory;

public class Task implements NumericallyModeledEntity {

	private HlaTask hlaTask;
	// timeToFocus
	// timeRequiredToConfigureWorkbench

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

	public long findTimeToConfigureWorkbench() {
		return TaskStochasticVariableFactory.buildFactory().buildTimeToConfigureWorkbench().findRandomSample();
	}

	public long findTimeToFocus() {
		return TaskStochasticVariableFactory.buildFactory().buildTimeToFocus().findRandomSample();
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

	public Person getResponsiblePerson() {
		// TODO Auto-generated method stub
		return null;
	}

	public Double getLinesOfCodeToComplete() {
		// TODO Auto-generated method stub
		return null;
	}

	public Double getWorkDoneToComplete(WorkType key) {
		// TODO Auto-generated method stub
		return null;
	}

	public Person getConsultedPerson() {
		// TODO Auto-generated method stub
		return null;
	}

	public Person getAccountablePerson() {
		// TODO Auto-generated method stub
		return null;
	}
}
