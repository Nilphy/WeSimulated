package edu.wesimulated.firstapp.simulation;

import java.util.Date;

import edu.wesimulated.firstapp.simulation.domain.InstantMessenger;
import edu.wesimulated.firstapp.simulation.domain.Person;
import edu.wesimulated.firstapp.simulation.domain.Project;
import edu.wesimulated.firstapp.simulation.domain.Role;
import edu.wesimulated.firstapp.simulation.domain.Task;
import edu.wesimulated.firstapp.simulation.domain.worktype.WorkType;

public class RoleSimulatorBuilder {

	/**
	 * Generic role simulator builder, not used by now
	 * 
	 * @param role
	 * @param project
	 * @param person
	 * @return
	 */
	@Deprecated
	public static RoleSimulator build(Role role, Project project, Person person) {
		return null;
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
		WorkType workType = taskToWorkIn.findWorkTypeForRole(roleSimulator);
		roleSimulator.addCOperation(new WorkSlabStart(roleSimulator, workType, taskToWorkIn, roleWorkStart));
		return roleSimulator;
	}

	public static HighlyInterruptibleRoleSimulator buildAvatureInterruptibleRoleSimulator(Project project, Person person) {
		HighlyInterruptibleRoleSimulator simulator = new HighlyInterruptibleRoleSimulator(project);
		simulator.registerSimulationEntity(new InstantMessenger(person));
		return simulator;
	}
}
