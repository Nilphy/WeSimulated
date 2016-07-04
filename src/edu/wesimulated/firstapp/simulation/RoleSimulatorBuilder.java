package edu.wesimulated.firstapp.simulation;

import com.wesimulated.simulation.runparameters.TaskCompletedEndCondition;
import com.wesimulated.simulationmotor.des.threefaseaproach.ThreePhaseExecutor;

import edu.wesimulated.firstapp.simulation.domain.Project;
import edu.wesimulated.firstapp.simulation.domain.Role;

public class RoleSimulatorBuilder {

	public static RoleSimulator build(Role role, Project project) {
		ThreePhaseExecutor executor = new ThreePhaseExecutor(new TaskCompletedEndCondition(project));
		RoleSimulator roleSimulator = new RoleSimulator(executor, role, project);
		// Assign the task to work 8 hours (in blocks of lets start by 1 minute) each day from the start to the end of the project
		return roleSimulator;
	}

}
