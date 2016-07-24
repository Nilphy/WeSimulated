package edu.wesimulated.firstapp.simulation;

import java.util.Date;

import com.wesimulated.simulation.runparameters.TaskCompletedEndCondition;
import com.wesimulated.simulationmotor.des.threefaseaproach.ThreePhaseExecutor;

import edu.wesimulated.firstapp.simulation.domain.Person;
import edu.wesimulated.firstapp.simulation.domain.Project;
import edu.wesimulated.firstapp.simulation.domain.Role;
import edu.wesimulated.firstapp.simulation.domain.Task;
import edu.wesimulated.firstapp.simulation.domain.worktype.SetupWorkbench;
import edu.wesimulated.firstapp.simulation.domain.worktype.TypeOfWork;

public class RoleSimulatorBuilder {

	public static RoleSimulator build(Role role, Project project, Person person) {
		ThreePhaseExecutor executor = new ThreePhaseExecutor(new TaskCompletedEndCondition(project));
		RoleSimulator roleSimulator = new RoleSimulator(executor, role, project, person);
		Date roleWorkStart = project.findWorkStartOfRole(role);
		roleSimulator.addBOperation(new StartProject(roleWorkStart));
		// TODO ask the project which is the first task to work in
		Task taskToWorkIn = null;
		// TODO not every role starts the project with a SetupWorkbench
		TypeOfWork typeOfWork = new SetupWorkbench(roleSimulator);
		roleSimulator.addCOperation(new WorkSlabStart(roleSimulator, typeOfWork, taskToWorkIn, roleWorkStart));
		return roleSimulator;
	}

}
