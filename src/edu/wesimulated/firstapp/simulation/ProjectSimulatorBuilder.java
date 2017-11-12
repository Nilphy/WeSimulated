package edu.wesimulated.firstapp.simulation;

import edu.wesimulated.firstapp.simulation.domain.mywork.project.ErrorsReported;
import edu.wesimulated.firstapp.simulation.domain.mywork.project.MaintenanceTask;
import edu.wesimulated.firstapp.simulation.domain.mywork.project.Meeting;
import edu.wesimulated.firstapp.simulation.domain.mywork.project.ProjectSimulator;
import edu.wesimulated.firstapp.simulation.domain.mywork.project.Risk;
import edu.wesimulated.firstapp.simulation.stochastic.ParametricAlgorithm;
import edu.wesimulated.firstapp.simulation.stochastic.StochasticVar;

/**
 * 
 * 
 * 
 * @author Carolina
 */
public class ProjectSimulatorBuilder {

	public enum ProjectSimulatorType implements SimulatorType {
		MY_WORK;
	}

	public ProjectSimulator buildMyWorkProjectSimulator(edu.wesimulated.firstapp.simulation.domain.mywork.project.Project project) {
		ProjectSimulator projectSimulator = new ProjectSimulator(project);
		/**
		 * Los risks pueden tener variso efectos, o hacer que una persona no
		 * esté mas disponible en el trabajo, crear tareas nuevas asignadas a
		 * una persona, cambiar la longitud de las treas, agregar nuevas
		 * personas al proyecto
		 */
		for (Risk risk : project.getRisks()) {
			projectSimulator.addCOperation(risk);
		}
		for (MaintenanceTask maintenanceTask : project.getMaintenanceTasks()) {
			projectSimulator.addCOperation(maintenanceTask);
		}
		ParametricAlgorithm periodOfErrors = ParametricAlgorithm.buildParametricAlgorithmForVar(StochasticVar.PERIOD_OF_ERRORS);
		periodOfErrors.consider(project);
		projectSimulator.addCOperation(new ErrorsReported(periodOfErrors.findSample().getPrediction().getValue().longValue()));
		for (Meeting meeting : project.findRegularMeetings()) {
			projectSimulator.addCOperation(meeting);
		}
		return projectSimulator;
	}
}
