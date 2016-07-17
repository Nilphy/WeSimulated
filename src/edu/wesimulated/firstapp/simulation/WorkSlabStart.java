package edu.wesimulated.firstapp.simulation;

import java.util.Date;

import com.wesimulated.simulationmotor.DateUtils;
import com.wesimulated.simulationmotor.des.COperation;
import com.wesimulated.simulationmotor.des.TaskWithPriority;

import edu.wesimulated.firstapp.simulation.domain.TypeOfWork;
import edu.wesimulated.firstapp.simulation.stochastic.TaskStochasticVariableFactory;
import edu.wesimulated.firstapp.simulation.stochastic.var.RandomVar;

public class WorkSlabStart implements COperation, TaskWithPriority {

	private RoleSimulator simulator;

	public WorkSlabStart(RoleSimulator simulator, TypeOfWork typeOfWork) {
		this.simulator = simulator;
	}

	@Override
	public boolean testIfRequirementsAreMet() {
		return this.simulator.getPerson().isAvailable(this);
	}

	@Override
	public void doAction() {
		this.simulator.getPerson().setAvailable(false);
		long duration = calculateDurationOfWorkSlab();
		Date endOfSlab = DateUtils.addMilis(this.simulator.getExecutor().getClock().getCurrentDate(), duration);
		this.simulator.addBOperation(new WorkSlabEnd(endOfSlab, duration));
	}

	private long calculateDurationOfWorkSlab() {
		RandomVar timeOfWorkSlab = TaskStochasticVariableFactory.buildFactory().buildTimeOfWorkSlab();
		// TODO The person will have a list of all interruptions it has had
		// given the priority of this task and the ones that has interrupted it
		// The duration of this task could be calculated
		timeOfWorkSlab.consider(this.simulator.getPerson());
		timeOfWorkSlab.consider(this.simulator.getRole());
		timeOfWorkSlab.consider(this.simulator.getCurrentTask());
		timeOfWorkSlab.consider(this.simulator.getProject());
		return timeOfWorkSlab.findRandomSample();
	}
}