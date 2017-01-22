package edu.wesimulated.firstapp.simulation;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.wesimulated.simulationmotor.systemdynamics.Stock;
import com.wesimulated.simulationmotor.systemdynamics.SystemDynamicsSimulator;

import edu.wesimulated.firstapp.model.WorkType;
import edu.wesimulated.firstapp.simulation.domain.Task;

public class TaskSimulator extends SystemDynamicsSimulator {

	private Task task;
	private Map<WorkType, Stock> workDoneStocks;

	public TaskSimulator(Task task) {
		this.task = task;
		this.workDoneStocks = new HashMap<>();
	}

	public void registerPlannedTaskWorkModule(WorkModule module) {
		this.register(module);
		this.registerWorkDoneStock(module.getWorkType(), module.getOutputStock());
	}

	private void registerWorkDoneStock(WorkType taskWorkType, Stock outputStock) {
		this.getWorkDoneStocks().put(taskWorkType, outputStock);
	}

	private Map<WorkType, Stock> getWorkDoneStocks() {
		return this.workDoneStocks;
	}

	public boolean isCompleted() {
		for (Entry<WorkType, Stock> entry : this.getWorkDoneStocks().entrySet()) {
			if (this.task.getWorkDoneToComplete(entry.getKey()) > entry.getValue().getActualValue()) {
				return false;
			}
		}
		/*
		 * TODO and bugs found is acceptable Maybe if the task has a
		 * getWorkDoneToComplete for the rework tasks its stocks could be
		 * controlled as the workDone stocks
		 */
		return true;
	}

	@Override
	public String toString() {
		return task.toString();
	}

}
