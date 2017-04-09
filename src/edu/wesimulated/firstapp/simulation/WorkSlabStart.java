package edu.wesimulated.firstapp.simulation;

import java.util.Date;

import com.wesimulated.simulationmotor.DateUtils;
import com.wesimulated.simulationmotor.des.COperation;
import com.wesimulated.simulationmotor.des.Prioritized;

import edu.wesimulated.firstapp.simulation.domain.Task;
import edu.wesimulated.firstapp.simulation.domain.worktype.WorkType;

public class WorkSlabStart extends COperation implements Prioritized {

	private RoleSimulator roleSimulator;
	private WorkType workType;
	private Task task;
	private Date minDate;

	public WorkSlabStart(RoleSimulator simulator, WorkType workType, Task task, Date minDate) {
		this.roleSimulator = simulator;
		this.workType = workType;
		this.task = task;
		this.minDate = minDate;
	}

	@Override
	public boolean testIfRequirementsAreMet() {
		return this.roleSimulator.getPerson().isAvailable(this) && this.roleSimulator.getOperationBasedExecutor().getClock().dateHasPassed(this.minDate);
	}

	@Override
	public void doAction() {
		this.roleSimulator.getPerson().setAvailable(false);
		this.roleSimulator.getPerson().setCurrentTask(this.task);
		this.roleSimulator.setCurrentTask(this.task);
		this.roleSimulator.setCurrentTaskStart(this.roleSimulator.getOperationBasedExecutor().getClock().getCurrentDate());
		this.roleSimulator.setCurrentWorkType(this.workType);
		long duration = this.task.findDurationOfWorkSlab(this.roleSimulator);
		Date endOfSlab = DateUtils.addMilis(this.roleSimulator.getOperationBasedExecutor().getClock().getCurrentDate(), duration);
		this.roleSimulator.addBOperation(new WorkSlabEnd(this.roleSimulator, this.roleSimulator.getOperationBasedExecutor().getClock().getCurrentDate(), endOfSlab));
	}

	@Override
	public Date getDateOfOccurrence() {
		return this.minDate;
	}

	@Override
	public Float calculatePriority() {
		// TODO Auto-generated method stub
		return 0f;
	}
}
