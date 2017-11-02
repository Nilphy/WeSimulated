package edu.wesimulated.firstapp.simulation.domain.mywork.role;

import java.util.Date;

import com.wesimulated.simulationmotor.DateUtils;
import com.wesimulated.simulationmotor.des.BOperation;

import edu.wesimulated.firstapp.simulation.domain.worktype.WorkType;

public class RecoverFocus extends BOperation {

	private Date startTime;
	private RoleSimulator roleSimulator;

	public RecoverFocus(Date startTime, RoleSimulator simulator) {
		super(null);
		this.startTime = startTime;
		this.roleSimulator = simulator;
	}

	@Override
	public void doAction() {
		long duration = this.roleSimulator.getCurrentTask().findDurationOfRecoverFocus(this.roleSimulator);
		Date endOfRecover = DateUtils.addMilis(this.roleSimulator.getOperationBasedExecutor().getClock().getCurrentDate(), duration);
		Task taskToWorkIn = this.roleSimulator.getProject().findTaskToWorkForRole(this.roleSimulator.getPerson(), this.roleSimulator.getRole());
		if (taskToWorkIn != null) {
			WorkType workType = taskToWorkIn.findWorkTypeForRole(this.roleSimulator);
			this.roleSimulator.addCOperation(new WorkSlabStart(this.roleSimulator, workType, taskToWorkIn, endOfRecover));
		}
	}

	@Override
	public Date getStartTime() {
		return this.startTime;
	}
}
