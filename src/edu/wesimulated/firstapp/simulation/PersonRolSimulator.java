package edu.wesimulated.firstapp.simulation;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

import com.wesimulated.simulation.hla.DateLogicalTime;
import com.wesimulated.simulationmotor.DateUtils;
import com.wesimulated.simulationmotor.des.BOperation;
import com.wesimulated.simulationmotor.des.OperationBasedExecutor;

import edu.wesimulated.firstapp.model.PersonData;
import edu.wesimulated.firstapp.simulation.hla.HlaPerson;

public class PersonRolSimulator {

	private OperationBasedExecutor executor;
	private Collection<TaskSimulator> tasks;
	private PersonData person;
	private HlaPerson hlaPerson;

	public PersonRolSimulator(OperationBasedExecutor executor, PersonData person, HlaPerson hlaPerson) {
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

		this.tasks.add(task);
	}

	public void addBOperation(BOperation operation) {
		this.getExecutor().addBOperation(operation);
	}

	public boolean isRunning() {
		return !this.getExecutor().isSimulationEnd();
	}
}
