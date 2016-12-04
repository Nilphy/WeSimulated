package edu.wesimulated.firstapp.simulation;

import java.util.Date;

import com.wesimulated.simulationmotor.des.BOperation;

import edu.wesimulated.firstapp.simulation.domain.Task;
import edu.wesimulated.firstapp.simulation.domain.worktype.TypeOfWork;

public class WorkSlabEnd implements BOperation {

	private RoleSimulator roleSimulator;
	private Date startOfSlab;
	private Date endOfSlab;

	public WorkSlabEnd(RoleSimulator roleSimulator, Date startOfSlab, Date endOfSlab) {
		this.startOfSlab = startOfSlab;
		this.endOfSlab = endOfSlab;
		this.roleSimulator = roleSimulator;
	}

	@Override
	public void doAction() {
		this.roleSimulator.getCurrentTypeOfWork().applyEffects(startOfSlab, endOfSlab);
		Task nextTask = this.roleSimulator.getCurrentTask();
		if (this.roleSimulator.getCurrentTask().isCompleted(this.roleSimulator.getRole())) {
			nextTask = this.roleSimulator.getProject().findTaskToWorkOn(endOfSlab, this.roleSimulator.getPerson(), this.roleSimulator.getRole());
		}
		TypeOfWork typeOfWork = nextTask.findTypeOfTaskToWorkForRole(this.roleSimulator);
		// TODO calc min date considering end of laboral day
		Date minDate = null;
		this.roleSimulator.addCOperation(new WorkSlabStart(this.roleSimulator, typeOfWork, nextTask, minDate));
		this.roleSimulator.getPerson().setAvailable(true);
	}

	@Override
	public Date getStartTime() {
		return this.endOfSlab;
	}
}
