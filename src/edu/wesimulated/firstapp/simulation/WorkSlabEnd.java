package edu.wesimulated.firstapp.simulation;

import java.util.Date;

import com.wesimulated.simulationmotor.des.BOperation;

import edu.wesimulated.firstapp.simulation.domain.avature.role.AvatureDeveloperTask;
import edu.wesimulated.firstapp.simulation.domain.worktype.WorkType;

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
		this.roleSimulator.getCurrentWorkType().applyEffects(startOfSlab, endOfSlab);
		AvatureDeveloperTask nextTask = (AvatureDeveloperTask) this.roleSimulator.getCurrentTask();
		if (this.roleSimulator.getCurrentTask().isCompleted(this.roleSimulator.getRole())) {
			nextTask = (AvatureDeveloperTask) this.roleSimulator.getProject().findTaskToWorkOn(endOfSlab, this.roleSimulator.getPerson(), this.roleSimulator.getRole());
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
