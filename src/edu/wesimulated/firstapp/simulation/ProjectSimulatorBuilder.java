package edu.wesimulated.firstapp.simulation;

import edu.wesimulated.firstapp.simulation.domain.MaintenanceTask;
import edu.wesimulated.firstapp.simulation.domain.Project;
import edu.wesimulated.firstapp.simulation.domain.Risk;

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
		 * Los risks pueden tener dos efectos, o hacer que una persona no esté
		 * mas disponible en el trabajo, o crear tareas nuevas asignadas a una
		 * persona
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
