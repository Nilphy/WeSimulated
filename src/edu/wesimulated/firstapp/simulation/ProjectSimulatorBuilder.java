package edu.wesimulated.firstapp.simulation;

import edu.wesimulated.firstapp.simulation.domain.MaintenanceTask;
import edu.wesimulated.firstapp.simulation.domain.Project;
import edu.wesimulated.firstapp.simulation.domain.avature.project.Risk;

/**
 * 
 * 
 * 
 * @author Carolina
 */
public class ProjectSimulatorBuilder {

	public ProjectSimulator buildAvatureProject() {
		Project project = new Project();
		ProjectSimulator projectSimulator = new ProjectSimulator(project);
		/**
		 * Los risks pueden tener variso efectos, o hacer que una persona no esté
		 * mas disponible en el trabajo, crear tareas nuevas asignadas a una
		 * persona, cambiar la longitud de las treas, agregar nuevas personas al proyecto
		 */
		for (Risk risk : project.getRisks()) {
			projectSimulator.addCOperation(risk);
		}
		for (MaintenanceTask maintenanceTask : project.getMaintenanceTasks()) {
			projectSimulator.addCOperation(maintenanceTask);
		}
		return projectSimulator;
	}
}
