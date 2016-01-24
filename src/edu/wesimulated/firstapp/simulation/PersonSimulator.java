package edu.wesimulated.firstapp.simulation;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

import com.wesimulated.simulation.hla.DateLogicalTime;
import com.wesimulated.simulationmotor.DateUtils;
import com.wesimulated.simulationmotor.operationbased.BOperation;
import com.wesimulated.simulationmotor.operationbased.OperationBasedExecutor;

import edu.wesimulated.firstapp.model.Person;

public class PersonSimulator {

	private OperationBasedExecutor executor;
	private Collection<TaskSimulator> tasks;
	private Person person;
	private HLAPerson hlaPerson;

	public PersonSimulator(OperationBasedExecutor executor, Person person, HLAPerson hlaPerson) {
		this.setExecutor(executor);
		this.person = person;
		this.hlaPerson = hlaPerson;
	}

	public void startExecution() {
		this.executor.run();
	}

	public OperationBasedExecutor getExecutor() {
		return executor;
	}

	public void setExecutor(OperationBasedExecutor executor) {
		this.executor = executor;
	}

	public void addTask(TaskSimulator taskSimulator) {
		if (this.tasks == null) {
			this.tasks = new LinkedList<>();
		}
		this.tasks.add(taskSimulator);
	}

	public void doOneDayOfWork() {
		this.person.setAvailable(false);
		Float remainingMillisecondsToWorkToday = this.person.calculateEffectiveMillisecondsPerDay();
		Iterator<TaskSimulator> tasksIterator = this.tasks.iterator();
		Float previousRemainingMillisecondsToWorkToday = remainingMillisecondsToWorkToday;
		while (tasksIterator.hasNext() && remainingMillisecondsToWorkToday > 0) {
			TaskSimulator task = tasksIterator.next();
			if (!task.isCompleted()) {
				remainingMillisecondsToWorkToday = task.consumeTime(remainingMillisecondsToWorkToday);
				float millisSpentOnTask = previousRemainingMillisecondsToWorkToday - remainingMillisecondsToWorkToday;
				DateLogicalTime endMoment = DateUtils.addMilis(this.getExecutor().getClock().getCurrentDate(), millisSpentOnTask);
				this.hlaPerson.incrementWorkDone(
					UnitsOfWorkInterpreter.milisToUow(millisSpentOnTask), 
					endMoment
				);
				System.out.println("Work done! " + this.person + " has worked in " + task + " and still has " + remainingMillisecondsToWorkToday + " milliseconds to work today.");
				previousRemainingMillisecondsToWorkToday = remainingMillisecondsToWorkToday;
			}
		}
		this.person.setAvailable(true);
	}

	public void addBOperation(BOperation operation) {
		this.getExecutor().addBOperation(operation);
	}

	public boolean isRunning() {
		return !this.getExecutor().isSimulationEnd();
	}
}
