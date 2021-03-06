package edu.wesimulated.firstapp.simulation.domain.mywork.task;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.wesimulated.simulation.runparameters.TaskCompletedEndCondition;
import com.wesimulated.simulationmotor.systemdynamics.Stock;
import com.wesimulated.simulationmotor.systemdynamics.SystemDynamicsExecutor;
import com.wesimulated.simulationmotor.systemdynamics.SystemDynamicsSimulator;

import edu.wesimulated.firstapp.model.TaskNeedType;
import edu.wesimulated.firstapp.simulation.domain.Task;

public class TaskSimulator extends SystemDynamicsSimulator {

	private Task task;
	private Map<TaskNeedType, Stock> workDoneStocks;

	public TaskSimulator(Task task) {
		super(new SystemDynamicsExecutor(new TaskCompletedEndCondition(task)));
		this.task = task;
		this.workDoneStocks = new HashMap<>();
	}

	public void registerPlannedTaskWorkModule(WorkModule module) {
		this.register(module);
		this.registerWorkDoneStock(module.getTaskNeed(), module.getOutputStock());
	}

	private void registerWorkDoneStock(TaskNeedType taskNeed, Stock outputStock) {
		this.getWorkDoneStocks().put(taskNeed, outputStock);
	}

	private Map<TaskNeedType, Stock> getWorkDoneStocks() {
		return this.workDoneStocks;
	}

	public boolean isCompleted() {
		for (Entry<TaskNeedType, Stock> entry : this.getWorkDoneStocks().entrySet()) {
			if (this.task.getCostInHours(entry.getKey()).doubleValue() > entry.getValue().getActualValue()) {
				return false;
			}
		}
		/*
		 * TODO bugs found is acceptable Maybe if the task has a
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
