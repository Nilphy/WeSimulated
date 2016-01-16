package edu.wesimulated.firstapp.simulation;

import hla.rti1516e.exceptions.FederateNotExecutionMember;
import hla.rti1516e.exceptions.InTimeAdvancingState;
import hla.rti1516e.exceptions.InvalidLogicalTime;
import hla.rti1516e.exceptions.LogicalTimeAlreadyPassed;
import hla.rti1516e.exceptions.NotConnected;
import hla.rti1516e.exceptions.RTIinternalError;
import hla.rti1516e.exceptions.RequestForTimeConstrainedPending;
import hla.rti1516e.exceptions.RequestForTimeRegulationPending;
import hla.rti1516e.exceptions.RestoreInProgress;
import hla.rti1516e.exceptions.SaveInProgress;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

import com.wesimulated.simulation.BaseExcecutor;
import com.wesimulated.simulationmotor.operationbased.BOperation;

import edu.wesimulated.firstapp.model.Person;

public class PersonSimulator {

	private BaseExcecutor executor;
	private Collection<TaskSimulator> tasks;
	private Person person;

	public PersonSimulator(BaseExcecutor executor, Person person) {
		this.setExecutor(executor);
		this.person = person;
	}

	public void startExecution() throws LogicalTimeAlreadyPassed, InvalidLogicalTime, InTimeAdvancingState, RequestForTimeRegulationPending, RequestForTimeConstrainedPending, SaveInProgress,
			RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError, InterruptedException {
		this.executor.run();
	}

	public BaseExcecutor getExecutor() {
		return executor;
	}

	public void setExecutor(BaseExcecutor executor) {
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
		while (tasksIterator.hasNext() && remainingMillisecondsToWorkToday > 0) {
			TaskSimulator task = tasksIterator.next();
			if (!task.isCompleted()) {
				remainingMillisecondsToWorkToday = task.consumeTime(remainingMillisecondsToWorkToday);
				System.out.println("Work done! " + this.person + " has worked in " + task + " and still has " + remainingMillisecondsToWorkToday + " milliseconds to work today.");
				// TODO update work done of task for HLA
			}
		}
		this.person.setAvailable(true);
	}

	public void addBOperation(BOperation operation) {
		
	}

	public boolean isRunning() {
		return !this.getExecutor().isSimulationEnd();
	}
}
