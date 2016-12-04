package edu.wesimulated.firstapp.simulation;

import java.util.Date;

import com.wesimulated.simulationmotor.DateUtils;
import com.wesimulated.simulationmotor.des.COperation;
import com.wesimulated.simulationmotor.des.TaskWithPriority;

import edu.wesimulated.firstapp.simulation.domain.Task;
import edu.wesimulated.firstapp.simulation.domain.worktype.TypeOfWork;
import edu.wesimulated.firstapp.simulation.stochastic.TaskStochasticVariableFactory;
import edu.wesimulated.firstapp.simulation.stochastic.var.RandomVar;

public class WorkSlabStart implements COperation, TaskWithPriority {

	private RoleSimulator roleSimulator;
	private TypeOfWork typeOfWork;
	private Task task;
	private Date minDate;

	public WorkSlabStart(RoleSimulator simulator, TypeOfWork typeOfWork, Task task, Date minDate) {
		this.roleSimulator = simulator;
		this.typeOfWork = typeOfWork;
		this.task = task;
		this.minDate = minDate;
	}

	@Override
	public boolean testIfRequirementsAreMet() {
		return this.roleSimulator.getPerson().isAvailable(this) && this.roleSimulator.getOperationBasedExecutor().getClock().dateHasPassed(this.minDate);
	}

	@Override
	public void doAction() {
		long duration = calculateDurationOfWorkSlab();
		this.roleSimulator.getPerson().setAvailable(false);
	}

	private long calculateDurationOfWorkSlab() {
		RandomVar timeOfWorkSlab = TaskStochasticVariableFactory.buildFactory().buildTimeOfWorkSlab();
		// TODO The person will have a list of all interruptions it has had
		// given the priority of this task and the ones that has interrupted it
		// The duration of this task could be calculated
		timeOfWorkSlab.consider(this.simulator.getPerson());
		timeOfWorkSlab.consider(this.simulator.getRole());
		timeOfWorkSlab.consider(this.simulator.getCurrentTask());
		timeOfWorkSlab.consider(this.simulator.getCurrentTypeOfWork());
		timeOfWorkSlab.consider(this.simulator.getProject());
		return timeOfWorkSlab.findRandomSample();
		this.roleSimulator.setCurrentTask(this.task);
		this.roleSimulator.setCurrentTypeOfWork(this.typeOfWork);
		Date endOfSlab = DateUtils.addMilis(this.roleSimulator.getOperationBasedExecutor().getClock().getCurrentDate(), duration);
		this.roleSimulator.addBOperation(new WorkSlabEnd(this.roleSimulator, this.roleSimulator.getOperationBasedExecutor().getClock().getCurrentDate(), endOfSlab));
	}
}
