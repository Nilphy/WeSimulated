package edu.wesimulated.firstapp.simulation;

import java.util.Date;

import com.wesimulated.simulationmotor.DateUtils;
import com.wesimulated.simulationmotor.des.COperation;
import com.wesimulated.simulationmotor.des.TaskWithPriority;

import edu.wesimulated.firstapp.simulation.domain.Task;

public class StartSetupOfLaboralDay implements COperation, TaskWithPriority {

	private Date day;
	private RoleSimulator simulator;

	public StartSetupOfLaboralDay(RoleSimulator roleSimulator, Date day) {
		this.day = day;
		this.simulator = roleSimulator;
	}

	@Override
	public boolean testIfRequirementsAreMet() {
		return this.simulator.getPerson().isAvailable(this) && this.simulator.getExecutor().getClock().getCurrentDate().compareTo(this.day) > 0;
	}

	@Override
	public void doAction() {
		this.simulator.getPerson().setAvailable(false);
		Task task = this.simulator.getProject().findTaskToWorkOn(day, this.simulator.getPerson(), this.simulator.getRole());
		this.simulator.setCurrentTask(task);
		long timeRequiredToConfigureWorkbench = task.findTimeToConfigureWorkbench();
		long timeRequiredToFocusOnTask = task.findTimeToFocus();
		Date startSetupEnd = DateUtils.addMilis(day, timeRequiredToConfigureWorkbench + timeRequiredToFocusOnTask);
		this.simulator.addBOperation(new EndSetupOfLaboralDay(this.simulator, day, startSetupEnd));
	}
}
