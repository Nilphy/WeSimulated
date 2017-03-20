package edu.wesimulated.firstapp.simulation;

import java.util.Date;

import com.wesimulated.simulation.runparameters.TaskCompletedEndCondition;
import com.wesimulated.simulationmotor.des.processbased.ProcessBasedExecutor;

import edu.wesimulated.firstapp.simulation.domain.InstantMessenger;
import edu.wesimulated.firstapp.simulation.domain.Person;
import edu.wesimulated.firstapp.simulation.domain.Project;
import edu.wesimulated.firstapp.simulation.domain.Role;
import edu.wesimulated.firstapp.simulation.domain.Task;
import edu.wesimulated.firstapp.simulation.domain.worktype.SetupWorkbench;
import edu.wesimulated.firstapp.simulation.domain.worktype.TypeOfWork;

public class RoleSimulatorBuilder {

	/**
	 * Generic role simulator builder, not used
	 * 
	 * @param role
	 * @param project
	 * @param person
	 * @return
	 */
	@Deprecated
	public static RoleSimulator build(Role role, Project project, Person person) {
		RoleSimulator roleSimulator = new RoleSimulator(role, project, person);
		Date roleWorkStart = project.findStartDateToWorkForRole(role, person);
		roleSimulator.addBOperation(new StartProject(roleWorkStart));
		// FIXME ask the project which is the first task to work in
		Task taskToWorkIn = null;
		// FIXME not every role starts the project with a SetupWorkbench
		TypeOfWork typeOfWork = new SetupWorkbench(roleSimulator);
		roleSimulator.addCOperation(new WorkSlabStart(roleSimulator, typeOfWork, taskToWorkIn, roleWorkStart));
		return roleSimulator;
	}

	/**
	 * Role simulator for an avature proyect
	 * 
	 * @param role
	 * @param project
	 * @param person
	 * @return the simulator builded
	 */
	public static RoleSimulator buildAvatureDeveloperSimulator(Project project, Person person) {
		Role role = RolePool.getAvatureDeveloperRole();
		RoleSimulator roleSimulator = new RoleSimulator(role, project, person);
		Date roleWorkStart = project.findStartDateToWorkForRole(role, person);
		roleSimulator.addBOperation(new StartProject(roleWorkStart));
		Task taskToWorkIn = project.findTaskToWorkForRole(role);
		TypeOfWork typeOfWork = taskToWorkIn.findTypeOfTaskToWorkForRole(roleSimulator);
		roleSimulator.addCOperation(new WorkSlabStart(roleSimulator, typeOfWork, taskToWorkIn, roleWorkStart));
		return roleSimulator;
	}

	public static HighlyInterruptibleRoleSimulator buildAvatureInterruptibleRoleSimulator(Project project, Person person) {
		HighlyInterruptibleRoleSimulator simulator = new HighlyInterruptibleRoleSimulator(project);
		simulator.registerSimulationEntity(new InstantMessenger(person));
		return simulator;
	}
}
