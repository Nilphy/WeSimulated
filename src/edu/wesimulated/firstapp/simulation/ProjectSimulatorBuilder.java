package edu.wesimulated.firstapp.simulation;

import edu.wesimulated.firstapp.simulation.domain.Project;
import edu.wesimulated.firstapp.simulation.domain.avature.project.MaintenanceTask;
import edu.wesimulated.firstapp.simulation.domain.avature.project.ProjectSimulator;
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
			Long period = maintenanceTask.getPeriodInMinutes();
			if (period > 0) {
				projectSimulator.registerPeriodicMaintenance(period, maintenanceTask);
			} else {
				projectSimulator.addCOperation(maintenanceTask);
			}
		}
		return projectSimulator;
	}
}
