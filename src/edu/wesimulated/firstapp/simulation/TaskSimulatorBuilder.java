package edu.wesimulated.firstapp.simulation;

import edu.wesimulated.firstapp.simulation.domain.Project;
import edu.wesimulated.firstapp.simulation.domain.Task;

/**
 * 
 * Comportamiento a simular sobre una tarea:
 * 
 * * Irá completando el trabajo requerido para realizarse cuando 
 * las personas asignadas realizen trabajo para la tarea.
 * * Irá teniendo bugs 
 * * Irá generando necesidades de cambio
 * 
 * @author Carolina
 *
 */
public class TaskSimulatorBuilder {

	public static TaskSimulator build(Task task, Project project) {
		TaskSimulator simulator = new TaskSimulator(task);
		simulator.addStock((double previousValue, double dt) -> {
			// TODO do the magic here
			double someVar = 0;
			double nextValue = previousValue + dt * someVar;
			return nextValue;
		});
		// TODO add all of the stocks	
		// TODO add all of the flows
		return null;
	}

}
