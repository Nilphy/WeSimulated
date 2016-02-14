package edu.wesimulated.firstapp.simulation;

import java.util.Observable;

import edu.wesimulated.firstapp.model.PersonData;
import edu.wesimulated.firstapp.model.TaskData;
import edu.wesimulated.firstapp.simulation.domain.PersonBuilder;
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
		this.logger.destroyFederationExecution();
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

	public void addPerson(PersonData person) {
		PersonFederate personFederate = new PersonFederate(PersonBuilder.createFromPersonData(person));
		this.addObserver(personFederate);
		personFederate.joinFederationExcecution(PersonFederate.FEDERATE_NAME);
	}

	public void addTask(TaskData task) {
		// TODO create task federate
	}

	public void setSimulationOverviewController(SimulationOverviewController simulationOverviewController) {
		this.simulationOverviewController = simulationOverviewController;
	}

	public void registerProject(Object projectData) {
		// TODO Auto-generated method stub
		
	}
}
