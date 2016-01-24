package edu.wesimulated.firstapp.simulation;

import hla.rti1516e.AttributeHandleSet;
import hla.rti1516e.AttributeHandleValueMap;
import hla.rti1516e.FederateAmbassador;
import hla.rti1516e.FederateHandle;
import hla.rti1516e.InteractionClassHandle;
import hla.rti1516e.NullFederateAmbassador;
import hla.rti1516e.ObjectClassHandle;
import hla.rti1516e.ObjectInstanceHandle;
import hla.rti1516e.OrderType;
import hla.rti1516e.ParameterHandleValueMap;
import hla.rti1516e.TransportationTypeHandle;
import hla.rti1516e.exceptions.AttributeNotDefined;
import hla.rti1516e.exceptions.FederateInternalError;
import hla.rti1516e.exceptions.FederateNotExecutionMember;
import hla.rti1516e.exceptions.FederateServiceInvocationsAreBeingReportedViaMOM;
import hla.rti1516e.exceptions.FederatesCurrentlyJoined;
import hla.rti1516e.exceptions.FederationExecutionDoesNotExist;
import hla.rti1516e.exceptions.InteractionClassNotDefined;
import hla.rti1516e.exceptions.InvalidObjectClassHandle;
import hla.rti1516e.exceptions.NameNotFound;
import hla.rti1516e.exceptions.NotConnected;
import hla.rti1516e.exceptions.ObjectClassNotDefined;
import hla.rti1516e.exceptions.RTIinternalError;
import hla.rti1516e.exceptions.RestoreInProgress;
import hla.rti1516e.exceptions.SaveInProgress;

import java.util.HashMap;
import java.util.Map;

import edu.wesimulated.firstapp.view.SimulationOverviewController;

public class LoggerFederate extends AbstractFederate implements Observer {

	private SimulationOverviewController simulationOverviewController;
	private Map<ObjectInstanceHandle, HLAPerson> people;

	public LoggerFederate() {
		super();
		this.people = new HashMap<>();
	}
	
	protected void joinFederationExcecution(String federateName) {
		super.joinFederationExcecution(federateName, new LoggerFederateAmbassador());
		subscribeToPerson();
		subscribeToInformInteraction();
	}
	
	private void subscribeToPerson() {
		this.getController().log("LoggerSubscribingToPersonWorkDone");
		try {
			ObjectClassHandle personClassHandle = this.getRTIAmbassador().getObjectClassHandle(HLAPerson.CLASS_NAME);
			AttributeHandleSet personAttributesHandle = this.getRTIAmbassador().getAttributeHandleSetFactory().create();
			personAttributesHandle.add(this.getRTIAmbassador().getAttributeHandle(personClassHandle, HLAPerson.ATTRIBUTE_WORK_DONE_NAME));
			this.getRTIAmbassador().subscribeObjectClassAttributes(personClassHandle, personAttributesHandle);
		} catch (NameNotFound | FederateNotExecutionMember | NotConnected | RTIinternalError | InvalidObjectClassHandle | AttributeNotDefined | ObjectClassNotDefined | SaveInProgress | RestoreInProgress e) {
			throw new RuntimeException(e);
		}
	@Override
	public void update(Observable o, Object arg) {
		((SimulationEvent) arg).updateSimulation(null, this);
	}

	@Override
	protected void resignFromFederation() {
		super.resignFromFederation();
		try {
			getRTIAmbassador().destroyFederationExecution(Simulation.FEDERATION_NAME);
		} catch (FederatesCurrentlyJoined | FederationExecutionDoesNotExist | NotConnected | RTIinternalError e) {
			throw new RuntimeException(e);
		}
	}
	
	private void subscribeToInformInteraction() {
		this.getController().log("LoggerSubscribingToInformInteraction");
		InteractionClassHandle informInteractionClassHandle;
		try {
			informInteractionClassHandle = this.getRTIAmbassador().getInteractionClassHandle(HLAInformInteraction.INFORM_INTERACTION_NAME);
			this.getRTIAmbassador().subscribeInteractionClass(informInteractionClassHandle);
		} catch (NameNotFound | FederateNotExecutionMember | NotConnected | RTIinternalError | FederateServiceInvocationsAreBeingReportedViaMOM | InteractionClassNotDefined | SaveInProgress | RestoreInProgress e) {
			throw new RuntimeException(e);
		}
	}
	
	private void personDiscovered(HLAPerson hlaPerson) {
		if (hlaPerson != null) {
			getController().log("Discovered person: " + hlaPerson.getObjectInstanceName());
			getPeople().put(hlaPerson.getObjectInstanceHandle(), hlaPerson);
		}
	}
	
	private SimulationOverviewController getController() {
		return this.simulationOverviewController;
	}
		
	public void setSimulationOverviewController(SimulationOverviewController simulationOverviewController) {
		this.simulationOverviewController = simulationOverviewController;
	}

	public Map<ObjectInstanceHandle, HLAPerson> getPeople() {
		return this.people;
	}
	
	public class LoggerFederateAmbassador extends NullFederateAmbassador implements FederateAmbassador {

		@Override
		public void discoverObjectInstance(ObjectInstanceHandle objectInstanceHandle, ObjectClassHandle objectClassHandle, String objectInstanceName) 
				throws FederateInternalError {
			discoverObjectInstance(objectInstanceHandle, objectClassHandle, objectInstanceName, null);
		}

		@Override
		public void discoverObjectInstance(ObjectInstanceHandle objectInstanceHandle, ObjectClassHandle objectClassHandle, String objectInstanceName, FederateHandle producingFederate)
				throws FederateInternalError {
			if (getPersonObjectClassHandle().equals(objectClassHandle)) {
				HLAPerson hlaPerson = null;
				hlaPerson = new HLAPerson(getRTIAmbassador(), getPersonObjectClassHandle(), objectInstanceHandle, objectInstanceName);
				personDiscovered(hlaPerson);
			}
		}

		@Override
		public void reflectAttributeValues(ObjectInstanceHandle objectInstanceHandle, AttributeHandleValueMap attributeValues, byte[] tag, OrderType sentOrdering, TransportationTypeHandle transportationTypeHandle, SupplementalReflectInfo reflectInfo) 
				throws FederateInternalError {
			HLAPerson person = getPeople().get(objectInstanceHandle);
			person.reflectAttributeValues(attributeValues);
			getController().log(person.getWorkDone());
		}

		@Override
		public void receiveInteraction(InteractionClassHandle interactionClassHandle, ParameterHandleValueMap parameterValues, byte[] tag, OrderType sentOrdering, TransportationTypeHandle transportationTypeHandle, SupplementalReceiveInfo receiveInfo) 
				throws FederateInternalError {
			HLAInformInteraction informInteraction = new HLAInformInteraction(getRTIAmbassador(), interactionClassHandle);
			informInteraction.receiveInteraction(parameterValues);
			getController().log("Receive Interaction: " + informInteraction.getMessage());
		}

		@Override
		public void removeObjectInstance(ObjectInstanceHandle objectInstanceHandle, byte[] tag, OrderType sentOrdering, SupplementalRemoveInfo removeInfo) throws FederateInternalError {
			getController().log("Remove Object Instance");
		}

		@Override
		public void provideAttributeValueUpdate(ObjectInstanceHandle objectInstanceHandle, AttributeHandleSet attributeHandles, byte[] tag) throws FederateInternalError {
			getController().log("Provide Attribute Value Update");
		}

		@Override
		public void requestAttributeOwnershipAssumption(ObjectInstanceHandle objectInstanceHandle, AttributeHandleSet attributeHandles, byte[] tag) throws FederateInternalError {
			getController().log("Request Attribute Ownership Assumption");
		}

		@Override
		public void attributeOwnershipAcquisitionNotification(ObjectInstanceHandle objectInstanceHandle, AttributeHandleSet attributeHandles, byte[] tag) throws FederateInternalError {
			getController().log("Attribute Ownsership Acquisition Notification");
		}

		@Override
		public void attributeOwnershipUnavailable(ObjectInstanceHandle objectInstanceHandle, AttributeHandleSet attributeHandles) throws FederateInternalError {
			getController().log("Attribute Ownership Unavailable");
		}

		@Override
		public void requestAttributeOwnershipRelease(ObjectInstanceHandle objectInstanceHandle, AttributeHandleSet attributeHandles, byte[] tag) throws FederateInternalError {
			getController().log("Request Attriibute Ownsership Release"); 
		}
	}
}
