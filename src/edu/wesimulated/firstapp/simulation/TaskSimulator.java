package edu.wesimulated.firstapp.simulation;

import com.wesimulated.simulationmotor.systemdynamics.CoreStructureAspect.Flow;
import com.wesimulated.simulationmotor.systemdynamics.CoreStructureAspect.Stock;
import com.wesimulated.simulationmotor.systemdynamics.SystemDynamicsSimulator;

import edu.wesimulated.firstapp.simulation.domain.Task;

public class TaskSimulator extends SystemDynamicsSimulator {

	private Task task;

	public TaskSimulator(Task task) {
		this.task = task;
	}

	public boolean isCompleted() {
		return this.task.isCompleted();
	}

	public void addStock(Stock stock) {
		this.getOperationBasedExecutor().addStock(stock);
	}

	public void addFlow(Flow flow) {
		this.getOperationBasedExecutor().addFlow(flow);
	}

	@Override
	public String toString() {
		return task.toString();
	}
}
