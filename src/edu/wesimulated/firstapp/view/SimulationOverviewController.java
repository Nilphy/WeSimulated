package edu.wesimulated.firstapp.view;

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

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import edu.wesimulated.firstapp.MainApp;
import edu.wesimulated.firstapp.model.LogEvent;
import edu.wesimulated.firstapp.model.Person;
import edu.wesimulated.firstapp.model.Task;
import edu.wesimulated.firstapp.simulation.Simulation;

public class SimulationOverviewController {

	@FXML
	private TextArea textDisplay;
	private MainApp mainApp;
	
	public SimulationOverviewController () {
		Simulation.getInstance().setSimulationOverviewController(this);
	}
	
	@FXML
	private void initialize() {
		this.textDisplay.setEditable(false);
		this.textDisplay.setText("Log: \n");
	}
	
	@FXML
	private void loadPeopleAndTasks() {
		try {
			for (Person person : this.mainApp.getPersonData()) {
				Simulation.getInstance().addPerson(person);
			}
			for (Task task : this.mainApp.getTaskData()) {
				Simulation.getInstance().addTask(task);
			}
		} catch (RTIinternalError | ConnectionFailed | InvalidLocalSettingsDesignator | UnsupportedCallbackModel | AlreadyConnected | CallNotAllowedFromWithinCallback | InconsistentFDD
				| ErrorReadingFDD | CouldNotOpenFDD | NotConnected | CouldNotCreateLogicalTimeFactory | FederationExecutionDoesNotExist | SaveInProgress | RestoreInProgress
				| FederateAlreadyExecutionMember | NameNotFound | FederateNotExecutionMember | InvalidInteractionClassHandle | InteractionClassNotDefined | InTimeAdvancingState
				| RequestForTimeConstrainedPending | TimeConstrainedAlreadyEnabled | InvalidObjectClassHandle | AttributeNotDefined | ObjectClassNotDefined
				| FederateServiceInvocationsAreBeingReportedViaMOM | InvalidLookahead | RequestForTimeRegulationPending | TimeRegulationAlreadyEnabled e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@FXML
	private void stopAndDestroySimulation() {
		try {
			Simulation.getInstance().destroyFederation();
		} catch (FederatesCurrentlyJoined | FederationExecutionDoesNotExist | NotConnected | RTIinternalError e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void log(String message) {
		this.textDisplay.appendText(this.getTimeToLog() + " " + message + "\n");
	}

	public void log(float workDone) {
		this.textDisplay.appendText(this.getTimeToLog() + " " + new LogEvent(workDone) + "\n");
	}
	
	private String getTimeToLog() {
		LocalDateTime now = LocalDateTime.now();
		return now.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.FULL));
	}

	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}
}
