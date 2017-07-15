package edu.wesimulated.firstapp.simulation;

import java.util.Observable;

import edu.wesimulated.firstapp.model.PersonData;
import edu.wesimulated.firstapp.model.ProjectData;
import edu.wesimulated.firstapp.model.RoleData;
import edu.wesimulated.firstapp.model.TaskData;
import edu.wesimulated.firstapp.simulation.domain.PersonBuilder;
import edu.wesimulated.firstapp.simulation.domain.ProjectBuilder;
import edu.wesimulated.firstapp.simulation.domain.RoleBuilder;
import edu.wesimulated.firstapp.simulation.domain.SimulatorFactory;
import edu.wesimulated.firstapp.simulation.domain.TaskBuilder;
import edu.wesimulated.firstapp.simulation.hla.HlaClass;
import edu.wesimulated.firstapp.simulation.hla.LoggerFederate;
import edu.wesimulated.firstapp.simulation.hla.ProjectFederate;
import edu.wesimulated.firstapp.simulation.hla.RoleFederate;
import edu.wesimulated.firstapp.simulation.hla.TaskFederate;
import edu.wesimulated.firstapp.view.SimulationOverviewController;

public class Simulation extends Observable {

	public final static String FEDERATION_NAME = "MY_FIRST_APP_FEDERATION_NAME";
	public final static String FDD = "WeSimulatedObjectModel-ieee-1516e.xml";

	private static Simulation instance = null;

	private LoggerFederate logger;
	private SimulationOverviewController simulationOverviewController;

	private Simulation() {
	}

	public static Simulation getInstance() {
		if (instance == null) {
			instance = new Simulation();
		}
		return instance;
	}

	public void destroyFederation() {
		this.setChanged();
		this.notifyObservers(SimulationEvent.buildEndEvent());
		if (this.logger != null) {
			this.logger.destroyFederationExecution();
		}
	}

	public void startFederation() {
		this.setChanged();
		this.notifyObservers(SimulationEvent.buildStartEvent());
	}

	public void registerLogger() {
		if (this.logger == null) {
			this.logger = new LoggerFederate();
			this.logger.setSimulationOverviewController(this.simulationOverviewController);
			this.logger.joinFederationExcecution(LoggerFederate.FEDERATE_NAME);
			this.addObserver(this.logger);
		}
	}

	public void addRole(RoleData role, PersonData person) {
		SimulatorFactory simulatorFactory = SimulatorFactory.getInstance(role);
		RoleFederate roleFederate = new RoleFederate(RoleBuilder.createFromRoleData(role, simulatorFactory), PersonBuilder.createFromPersonData(person, simulatorFactory));
		this.addObserver(roleFederate);
		roleFederate.joinFederationExcecution(HlaClass.getHlaPersonClassInstance().getFederateName());
	}

	public void addTask(TaskData task) {
		SimulatorFactory simulatorFactory = SimulatorFactory.getInstance(task);
		TaskFederate taskFederate = new TaskFederate(TaskBuilder.createFromTaskData(task, null, simulatorFactory));
		this.addObserver(taskFederate);
		taskFederate.joinFederationExcecution(HlaClass.getHlaTaskClassInstance().getFederateName());
	}

	public void registerProject(ProjectData projectData) {
		ProjectFederate projectFederate = new ProjectFederate(ProjectBuilder.createFromProjectData(projectData));
		this.addObserver(projectFederate);
		projectFederate.joinFederationExcecution(HlaClass.getHlaProjectClassInstance().getFederateName());
	}

	/**
	 * Esto está aquí porque el loggerFederate tiene que escribir mensajes en la UI
	 *  
	 * @param simulationOverviewController controlador de la UI donde se muestra el output de la simulación
	 */
	public void setSimulationOverviewController(SimulationOverviewController simulationOverviewController) {
		this.simulationOverviewController = simulationOverviewController;
	}
}
