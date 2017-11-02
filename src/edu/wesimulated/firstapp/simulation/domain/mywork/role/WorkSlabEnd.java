package edu.wesimulated.firstapp.simulation.domain.mywork.role;

import java.util.Date;

import com.wesimulated.simulationmotor.des.BOperation;
import com.wesimulated.simulationmotor.des.Operation;

import edu.wesimulated.firstapp.simulation.domain.worktype.WorkType;

public class WorkSlabEnd extends BOperation {

	private RoleSimulator roleSimulator;
	private Date startOfSlab;
	private Date endOfSlab;

	public WorkSlabEnd(RoleSimulator roleSimulator, Date startOfSlab, Date endOfSlab, Operation originalOperation) {
		super(originalOperation);
		this.startOfSlab = startOfSlab;
		this.endOfSlab = endOfSlab;
		this.roleSimulator = roleSimulator;
	}

	@Override
	public void doAction() {
		this.roleSimulator.getCurrentWorkType().applyEffects(startOfSlab, endOfSlab);
		Task nextTask = this.roleSimulator.getCurrentTask();
		if (this.roleSimulator.getCurrentTask().isCompleted(this.roleSimulator.getRole())) {
			nextTask = this.roleSimulator.getProject().findTaskToWorkOn(endOfSlab, this.roleSimulator.getPerson(), this.roleSimulator.getRole());
		}
		WorkType workType = nextTask.findWorkTypeForRole(this.roleSimulator);
		Date minDate = this.roleSimulator.getPerson().findNextAvailableDate();
		this.roleSimulator.addCOperation(new WorkSlabStart(this.roleSimulator, workType, nextTask, minDate));
		this.roleSimulator.getPerson().setAvailable(true);
	}

	@Override
	public Date getStartTime() {
		return this.endOfSlab;
	}
}
