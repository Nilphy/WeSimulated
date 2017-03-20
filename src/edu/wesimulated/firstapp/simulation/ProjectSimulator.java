package edu.wesimulated.firstapp.simulation;

import com.wesimulated.simulation.runparameters.TaskCompletedEndCondition;
import com.wesimulated.simulationmotor.des.COperation;
import com.wesimulated.simulationmotor.des.OperationBasedSimulator;
import com.wesimulated.simulationmotor.des.activityScanningAproach.ActivityScanningExecutor;

import edu.wesimulated.firstapp.simulation.domain.Project;

// TODO change to agregation
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
