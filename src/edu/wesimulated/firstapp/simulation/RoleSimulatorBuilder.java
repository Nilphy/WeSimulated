package edu.wesimulated.firstapp.simulation;

import java.util.Date;

import edu.wesimulated.firstapp.simulation.domain.MisconfiguredProject;
import edu.wesimulated.firstapp.simulation.domain.Person;
import edu.wesimulated.firstapp.simulation.domain.Project;
import edu.wesimulated.firstapp.simulation.domain.Role;
import edu.wesimulated.firstapp.simulation.domain.Team;
import edu.wesimulated.firstapp.simulation.domain.avature.highlyinterruptiblerole.EmailClient;
import edu.wesimulated.firstapp.simulation.domain.avature.highlyinterruptiblerole.HighlyInterruptibleRolePerson;
import edu.wesimulated.firstapp.simulation.domain.avature.highlyinterruptiblerole.HighlyInterruptibleRoleSimulator;
import edu.wesimulated.firstapp.simulation.domain.avature.highlyinterruptiblerole.InstantMessenger;
import edu.wesimulated.firstapp.simulation.domain.avature.highlyinterruptiblerole.Squealer;
import edu.wesimulated.firstapp.simulation.domain.avature.highlyinterruptiblerole.TeamWork;
import edu.wesimulated.firstapp.simulation.domain.avature.role.AvatureDeveloperTask;
import edu.wesimulated.firstapp.simulation.domain.avature.role.RolePerson;
import edu.wesimulated.firstapp.simulation.domain.avature.role.RoleSimulator;
import edu.wesimulated.firstapp.simulation.domain.avature.role.WorkSlabStart;
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
	 * @throws MisconfiguredProject 
	 */
	public static RoleSimulator buildAvatureDeveloperSimulator(Project project, RolePerson person) throws MisconfiguredProject {
		Role role = RolePool.getAvatureDeveloperRole();
		RoleSimulator roleSimulator = new RoleSimulator(role, project, person);
		AvatureDeveloperTask taskToWorkIn = (AvatureDeveloperTask) project.findTaskToWorkForRole(person, role);
		Date startTime = taskToWorkIn.getStartDate();
		roleSimulator.addBOperation(new StartProject(startTime));
		WorkType workType = taskToWorkIn.findWorkTypeForRole(roleSimulator);
		roleSimulator.addCOperation(new WorkSlabStart(roleSimulator, workType, taskToWorkIn, startTime));
		return roleSimulator;
	}

	/**
	 * Some persons are exposed to some heavy interruption agents like the
	 * system monitoring systems, customer service areas
	 * 
	 * @param project
	 * @param person
	 * @return
	 */
	public static HighlyInterruptibleRoleSimulator buildAvatureInterruptibleRoleSimulator(Project project, HighlyInterruptibleRolePerson person) {
		HighlyInterruptibleRoleSimulator simulator = new HighlyInterruptibleRoleSimulator(project, person);
		simulator.registerSimulationEntity(new InstantMessenger(person));
		simulator.registerSimulationEntity(new Squealer(person));
		simulator.registerSimulationEntity(new EmailClient(person));
		for (Team team : person.getTeams()) {
			simulator.registerSimulationEntity(new TeamWork(person));
		}
		return simulator;
	}

	public enum RoleSimulatorType implements SimulatorType {
		HighlyInterruptible, AvatureDeveloper;
	}
}
