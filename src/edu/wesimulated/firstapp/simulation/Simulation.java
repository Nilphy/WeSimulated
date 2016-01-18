package edu.wesimulated.firstapp.simulation;

import java.util.Observable;

import edu.wesimulated.firstapp.model.Person;
import edu.wesimulated.firstapp.model.Task;
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
		this.notifyObservers(SimulationEvent.buildEndEvent());
		this.logger.destroyFederationExecution();
	}
	
	public void startFederation() {
		ProjectSimulator.getInstance().assignTasks();
		this.notifyObservers(SimulationEvent.buildStartEvent());
	}
	

	public void registerLogger() {
		if (this.logger == null) {
			this.logger = new LoggerFederate();
			this.logger.setSimulationOverviewController(this.simulationOverviewController);
		}
	}
	
	public void addPerson(Person person) {
		PersonFederate personFederate = new PersonFederate(person);
		this.addObserver(personFederate);
		personFederate.joinFederationExcecution(PersonFederate.FEDERATE_NAME);
	}

	public void addTask(Task task) {
		ProjectSimulator.getInstance().addTask(new TaskSimulator(task));
	}
	
	public void setSimulationOverviewController(SimulationOverviewController simulationOverviewController) {
		this.simulationOverviewController = simulationOverviewController;
	}
}
