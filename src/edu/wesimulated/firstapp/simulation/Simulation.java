package edu.wesimulated.firstapp.simulation;

import java.util.Observable;

import hla.rti1516e.exceptions.AlreadyConnected;
import hla.rti1516e.exceptions.AttributeNotDefined;
import hla.rti1516e.exceptions.CallNotAllowedFromWithinCallback;
import hla.rti1516e.exceptions.ConnectionFailed;
import hla.rti1516e.exceptions.CouldNotCreateLogicalTimeFactory;
import hla.rti1516e.exceptions.CouldNotOpenFDD;
import hla.rti1516e.exceptions.ErrorReadingFDD;
import hla.rti1516e.exceptions.FederateAlreadyExecutionMember;
import hla.rti1516e.exceptions.FederateNotExecutionMember;
import hla.rti1516e.exceptions.FederateServiceInvocationsAreBeingReportedViaMOM;
import hla.rti1516e.exceptions.FederatesCurrentlyJoined;
import hla.rti1516e.exceptions.FederationExecutionDoesNotExist;
import hla.rti1516e.exceptions.InTimeAdvancingState;
import hla.rti1516e.exceptions.InconsistentFDD;
import hla.rti1516e.exceptions.InteractionClassNotDefined;
import hla.rti1516e.exceptions.InvalidInteractionClassHandle;
import hla.rti1516e.exceptions.InvalidLocalSettingsDesignator;
import hla.rti1516e.exceptions.InvalidLookahead;
import hla.rti1516e.exceptions.InvalidObjectClassHandle;
import hla.rti1516e.exceptions.NameNotFound;
import hla.rti1516e.exceptions.NotConnected;
import hla.rti1516e.exceptions.ObjectClassNotDefined;
import hla.rti1516e.exceptions.RTIinternalError;
import hla.rti1516e.exceptions.RequestForTimeConstrainedPending;
import hla.rti1516e.exceptions.RequestForTimeRegulationPending;
import hla.rti1516e.exceptions.RestoreInProgress;
import hla.rti1516e.exceptions.SaveInProgress;
import hla.rti1516e.exceptions.TimeConstrainedAlreadyEnabled;
import hla.rti1516e.exceptions.TimeRegulationAlreadyEnabled;
import hla.rti1516e.exceptions.UnsupportedCallbackModel;
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
	
	public void destroyFederation() throws FederatesCurrentlyJoined, FederationExecutionDoesNotExist, NotConnected, RTIinternalError {
		this.notifyObservers(SimulationEvent.buildEndEvent());
		this.logger.destroyFederationExecution();
	}
	
	public void startFederation() {
		ProjectSimulator.getInstance().assignTasks();
		this.notifyObservers(SimulationEvent.buildStartEvent());
	}
	
	public void registerLogger() throws RTIinternalError {
		if (this.logger == null) {
			this.logger = new LoggerFederate();
			this.logger.setSimulationOverviewController(this.simulationOverviewController);
		}
	}
	
	public void addPerson(Person person) throws RTIinternalError, ConnectionFailed, InvalidLocalSettingsDesignator, UnsupportedCallbackModel, AlreadyConnected, CallNotAllowedFromWithinCallback, InconsistentFDD, ErrorReadingFDD, CouldNotOpenFDD, NotConnected, CouldNotCreateLogicalTimeFactory, FederationExecutionDoesNotExist, SaveInProgress, RestoreInProgress, FederateAlreadyExecutionMember, NameNotFound, FederateNotExecutionMember, InvalidInteractionClassHandle, InteractionClassNotDefined, InTimeAdvancingState, RequestForTimeConstrainedPending, TimeConstrainedAlreadyEnabled, InvalidObjectClassHandle, AttributeNotDefined, ObjectClassNotDefined, FederateServiceInvocationsAreBeingReportedViaMOM, InvalidLookahead, RequestForTimeRegulationPending, TimeRegulationAlreadyEnabled {
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
