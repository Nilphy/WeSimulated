package edu.wesimulated.firstapp.simulation.domain.mywork.role;

import java.util.Date;

import com.wesimulated.simulationmotor.DateUtils;
import com.wesimulated.simulationmotor.des.COperation;
import com.wesimulated.simulationmotor.des.Prioritized;

import edu.wesimulated.firstapp.simulation.domain.worktype.WorkType;

public class WorkSlabStart extends COperation {

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
		return this.roleSimulator.getPerson().isAvailable() && this.roleSimulator.getExecutor().getClock().dateHasPassed(this.minDate);
	}

	@Override
	public void doAction() {
		this.roleSimulator.setCurrentTask(this.task);
		this.roleSimulator.setCurrentTaskStart(this.roleSimulator.getCurrentDate());
		this.roleSimulator.setCurrentWorkType(this.workType);
		long duration = this.task.findDurationOfWorkSlab(this.roleSimulator);
		Date endOfSlab = DateUtils.addMilis(this.roleSimulator.getCurrentDate(), duration);
		this.roleSimulator.addBOperation(new WorkSlabEnd(this.roleSimulator, this.roleSimulator.getCurrentDate(), endOfSlab, this));
	}

	@Override
	public Date getDateOfOccurrence(Date actualDate) {
		return this.minDate;
	}
}
