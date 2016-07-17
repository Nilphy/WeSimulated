package edu.wesimulated.firstapp.simulation;

import java.util.Date;

import com.wesimulated.simulation.runparameters.TaskCompletedEndCondition;
import com.wesimulated.simulationmotor.DateUtils;
import com.wesimulated.simulationmotor.des.threefaseaproach.ThreePhaseExecutor;

import edu.wesimulated.firstapp.simulation.domain.Person;
import edu.wesimulated.firstapp.simulation.domain.Project;
import edu.wesimulated.firstapp.simulation.domain.Role;

public class RoleSimulatorBuilder {

	public static RoleSimulator build(Role role, Project project, Person person) {
		ThreePhaseExecutor executor = new ThreePhaseExecutor(new TaskCompletedEndCondition(project));
		RoleSimulator roleSimulator = new RoleSimulator(executor, role, project, person);
		Date roleWorkStart = project.findWorkStartOfRole(role);
		Date roleWorkEnd = project.findWorkEndOfRole(role);
		Date dayInProgress = roleWorkStart;
		roleSimulator.addBOperation(new StartProject(roleWorkStart));
		while (dayInProgress.compareTo(roleWorkEnd) < 0) {
			dayInProgress = DateUtils.addOneDay(dayInProgress);
			roleSimulator.addCOperation(new StartSetupOfLaboralDay(roleSimulator, dayInProgress));
		}
		return roleSimulator;
	}

}
