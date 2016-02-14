package edu.wesimulated.firstapp.simulation;

import java.util.Collection;
import java.util.LinkedList;

import com.wesimulated.simulationmotor.des.BOperation;
import com.wesimulated.simulationmotor.des.OperationBasedExecutor;

import edu.wesimulated.firstapp.simulation.domain.Person;
import edu.wesimulated.firstapp.simulation.domain.Task;

public class PersonRolSimulator extends Simulator {

	private Collection<Task> tasks;
	private Person person;

	public PersonRolSimulator(OperationBasedExecutor executor, Person person) {
		this.setExecutor(executor);
		this.person = person;
	}

	public void assignTask(Task task) {
		if (this.tasks == null) {
			this.tasks = new LinkedList<>();
		}
		this.tasks.add(task);
	}

	public void addBOperation(BOperation operation) {
		this.getExecutor().addBOperation(operation);
	}
}
