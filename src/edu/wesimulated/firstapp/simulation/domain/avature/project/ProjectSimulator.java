package edu.wesimulated.firstapp.simulation.domain.avature.project;

import com.wesimulated.simulation.runparameters.TaskCompletedEndCondition;
import com.wesimulated.simulationmotor.des.COperation;
import com.wesimulated.simulationmotor.des.OperationBasedSimulator;
import com.wesimulated.simulationmotor.des.activityScanningAproach.ActivityScanningExecutor;

import edu.wesimulated.firstapp.simulation.domain.Project;

// TODO change to agregation (the simulator contains a simulator doesn't extends)
public class ProjectSimulator extends OperationBasedSimulator {

	private Project project;

	public ProjectSimulator(Project project) {
		super(new ActivityScanningExecutor(new TaskCompletedEndCondition(project)));
		this.project = project;
	}

	public void addCOperation(COperation operation) {
		this.getOperationBasedExecutor().addCOperation(operation);
	}
}
