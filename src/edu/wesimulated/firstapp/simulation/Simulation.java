package edu.wesimulated.firstapp.simulation;

import java.util.Observable;

import edu.wesimulated.firstapp.model.PersonData;
import edu.wesimulated.firstapp.model.ProjectData;
import edu.wesimulated.firstapp.model.RoleData;
import edu.wesimulated.firstapp.model.TaskData;
import edu.wesimulated.firstapp.simulation.domain.Project;
import edu.wesimulated.firstapp.simulation.domain.Role;
import edu.wesimulated.firstapp.simulation.domain.SimulatorFactory;
import edu.wesimulated.firstapp.simulation.domain.Task;
import edu.wesimulated.firstapp.simulation.domain.mywork.role.Person;
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

	public void registerRole(RoleData role, PersonData person) {
		SimulatorFactory factory = SimulatorFactory.getInstance(role);
		RoleFederate roleFederate = new RoleFederate((Role) factory.registerSimulationEntity(role), (Person) factory.registerSimulationEntity(person));
		this.addObserver(roleFederate);
		roleFederate.joinFederationExcecution(HlaClass.getHlaPersonClassInstance().getFederateName());
	}

	public void registerTask(TaskData task) {
		SimulatorFactory factory = SimulatorFactory.getInstance(task);
		TaskFederate taskFederate = new TaskFederate((Task) factory.registerSimulationEntity(task));
		this.addObserver(taskFederate);
		taskFederate.joinFederationExcecution(HlaClass.getHlaTaskClassInstance().getFederateName());
	}

	public void registerProject(ProjectData projectData) {
		SimulatorFactory simulatorFactory = SimulatorFactory.getInstance(projectData);
		ProjectFederate projectFederate = new ProjectFederate((Project) simulatorFactory.registerSimulationEntity(projectData));
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
