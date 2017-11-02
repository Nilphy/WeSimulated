package edu.wesimulated.firstapp.simulation;

import java.util.Date;

import edu.wesimulated.firstapp.simulation.domain.MisconfiguredProject;
import edu.wesimulated.firstapp.simulation.domain.Role;
import edu.wesimulated.firstapp.simulation.domain.Team;
import edu.wesimulated.firstapp.simulation.domain.mywork.highlyinterruptiblerole.EmailClient;
import edu.wesimulated.firstapp.simulation.domain.mywork.highlyinterruptiblerole.HighlyInterruptibleRoleSimulator;
import edu.wesimulated.firstapp.simulation.domain.mywork.highlyinterruptiblerole.InstantMessenger;
import edu.wesimulated.firstapp.simulation.domain.mywork.highlyinterruptiblerole.Squealer;
import edu.wesimulated.firstapp.simulation.domain.mywork.highlyinterruptiblerole.TeamWork;
import edu.wesimulated.firstapp.simulation.domain.mywork.role.AvatureDeveloperTask;
import edu.wesimulated.firstapp.simulation.domain.mywork.role.RoleSimulator;
import edu.wesimulated.firstapp.simulation.domain.mywork.role.WorkSlabStart;
import edu.wesimulated.firstapp.simulation.domain.worktype.WorkType;

public class RoleSimulatorBuilder {

	public enum RoleSimulatorType implements SimulatorType {
		AvatureDeveloper, HighlyInterruptible;
	}
	
	/**
	 * Generic role simulator builder, not used by now
	 * 
	 * @param role
	 * @param project
	 * @param person
	 * @return
	 */
	@Deprecated
	public static RoleSimulator build(Role role, edu.wesimulated.firstapp.simulation.domain.Project project, edu.wesimulated.firstapp.simulation.domain.Person person) {
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
	public static RoleSimulator buildAvatureDeveloperSimulator(edu.wesimulated.firstapp.simulation.domain.mywork.role.Project project, edu.wesimulated.firstapp.simulation.domain.mywork.role.Person person) throws MisconfiguredProject {
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
	public static HighlyInterruptibleRoleSimulator buildAvatureInterruptibleRoleSimulator(edu.wesimulated.firstapp.simulation.domain.Project project, edu.wesimulated.firstapp.simulation.domain.mywork.highlyinterruptiblerole.Person person) {
		HighlyInterruptibleRoleSimulator simulator = new HighlyInterruptibleRoleSimulator(project, person);
		simulator.registerSimulationEntity(new InstantMessenger(person));
		simulator.registerSimulationEntity(new Squealer(person));
		simulator.registerSimulationEntity(new EmailClient(person));
		for (Team team : person.getTeams()) {
			simulator.registerSimulationEntity(new TeamWork(person));
		}
		return simulator;
	}
}
