package edu.wesimulated.firstapp.simulation;

import com.wesimulated.simulation.runparameters.TaskCompletedEndCondition;
import com.wesimulated.simulationmotor.des.threefaseaproach.ThreePhaseExecutor;

import edu.wesimulated.firstapp.simulation.domain.Project;
import edu.wesimulated.firstapp.simulation.domain.Role;

public class RolSimulatorBuilder {

	public static RoleSimulator build(Role role, Project project) {
		ThreePhaseExecutor executor = new ThreePhaseExecutor(new TaskCompletedEndCondition(project));
		RoleSimulator roleSimulator = new RoleSimulator(executor, role, project);
		// el proyecto deber�a asginarle algunas tareas a la persona
		// y de una fase asignarle la siguiente tanda de tareas. 
		// como se le asignan las tareas a la perona depende de la metodolog�a del proyecto
		// Entonces la implementaci�n particular del proyecto sabe como asignar tareas a una persona
		return roleSimulator;
	}

}
