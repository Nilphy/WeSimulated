package edu.wesimulated.firstapp.simulation.domain.mywork.project;

import com.wesimulated.simulation.BaseSimulator;
import com.wesimulated.simulation.runparameters.TaskCompletedEndCondition;
import com.wesimulated.simulationmotor.des.COperation;
import com.wesimulated.simulationmotor.des.OperationBasedExecutor;
import com.wesimulated.simulationmotor.des.activityScanningAproach.ActivityScanningExecutor;

import edu.wesimulated.firstapp.simulation.domain.Project;

public class ProjectSimulator extends BaseSimulator<OperationBasedExecutor> {

	private Project project;

	public ProjectSimulator(Project project) {
		super(new ActivityScanningExecutor(new TaskCompletedEndCondition(project)));
		this.project = project;
	}

	public void addCOperation(COperation operation) {
		this.getExecutor().addCOperation(operation);
	}
}
