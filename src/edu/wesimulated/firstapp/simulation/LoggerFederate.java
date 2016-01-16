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
import hla.rti1516e.exceptions.AlreadyConnected;
import hla.rti1516e.exceptions.AttributeNotDefined;
import hla.rti1516e.exceptions.CallNotAllowedFromWithinCallback;
import hla.rti1516e.exceptions.ConnectionFailed;
import hla.rti1516e.exceptions.CouldNotCreateLogicalTimeFactory;
import hla.rti1516e.exceptions.CouldNotOpenFDD;
import hla.rti1516e.exceptions.ErrorReadingFDD;
import hla.rti1516e.exceptions.FederateAlreadyExecutionMember;
import hla.rti1516e.exceptions.FederateInternalError;
import hla.rti1516e.exceptions.FederateNotExecutionMember;
import hla.rti1516e.exceptions.FederateOwnsAttributes;
import hla.rti1516e.exceptions.FederateServiceInvocationsAreBeingReportedViaMOM;
import hla.rti1516e.exceptions.FederatesCurrentlyJoined;
import hla.rti1516e.exceptions.FederationExecutionDoesNotExist;
import hla.rti1516e.exceptions.InTimeAdvancingState;
import hla.rti1516e.exceptions.InconsistentFDD;
import hla.rti1516e.exceptions.InteractionClassNotDefined;
import hla.rti1516e.exceptions.InvalidInteractionClassHandle;
import hla.rti1516e.exceptions.InvalidLocalSettingsDesignator;
import hla.rti1516e.exceptions.InvalidObjectClassHandle;
import hla.rti1516e.exceptions.InvalidResignAction;
import hla.rti1516e.exceptions.NameNotFound;
import hla.rti1516e.exceptions.NotConnected;
import hla.rti1516e.exceptions.ObjectClassNotDefined;
import hla.rti1516e.exceptions.OwnershipAcquisitionPending;
import hla.rti1516e.exceptions.RTIinternalError;
import hla.rti1516e.exceptions.RequestForTimeConstrainedPending;
import hla.rti1516e.exceptions.RestoreInProgress;
import hla.rti1516e.exceptions.SaveInProgress;
import hla.rti1516e.exceptions.TimeConstrainedAlreadyEnabled;
import hla.rti1516e.exceptions.UnsupportedCallbackModel;

import java.util.HashMap;
import java.util.Map;

import edu.wesimulated.firstapp.view.SimulationOverviewController;

public class LoggerFederate extends AbstractFederate {

	private SimulationOverviewController simulationOverviewController;
	private Map<ObjectInstanceHandle, HLAPerson> people;

	public LoggerFederate() throws RTIinternalError {
		super();
		this.people = new HashMap<>();
	}
	
	protected void joinFederationExcecution(String federateName, NullFederateAmbassador federateAmbassador) throws FederateNotExecutionMember, NotConnected, NameNotFound, RTIinternalError, InvalidObjectClassHandle, AttributeNotDefined, ObjectClassNotDefined, SaveInProgress, RestoreInProgress, FederateServiceInvocationsAreBeingReportedViaMOM, InteractionClassNotDefined, ConnectionFailed, InvalidLocalSettingsDesignator, UnsupportedCallbackModel, AlreadyConnected, CallNotAllowedFromWithinCallback, InconsistentFDD, ErrorReadingFDD, CouldNotOpenFDD, CouldNotCreateLogicalTimeFactory, FederationExecutionDoesNotExist, FederateAlreadyExecutionMember, InvalidInteractionClassHandle, InTimeAdvancingState, RequestForTimeConstrainedPending, TimeConstrainedAlreadyEnabled {
		super.joinFederationExcecution(federateName, federateAmbassador);
		subscribeToPerson();
		subscribeToInformInteraction();
	}
	
	private void subscribeToPerson() 
			throws NameNotFound, FederateNotExecutionMember, NotConnected, RTIinternalError, InvalidObjectClassHandle, AttributeNotDefined, ObjectClassNotDefined, SaveInProgress, RestoreInProgress {
		this.getController().log("LoggerSubscribingToPersonWorkDone");
		ObjectClassHandle personClassHandle = this.getRTIAmbassador().getObjectClassHandle(HLAPerson.CLASS_NAME);
		AttributeHandleSet personAttributesHandle = this.getRTIAmbassador().getAttributeHandleSetFactory().create();
		personAttributesHandle.add(this.getRTIAmbassador().getAttributeHandle(personClassHandle, HLAPerson.ATTRIBUTE_WORK_DONE_NAME));
		this.getRTIAmbassador().subscribeObjectClassAttributes(
				personClassHandle,
				personAttributesHandle);
	}
	
	@Override
	protected void resignFromFederation() {
		super.resignFromFederation();
		try {
			getRTIAmbassador().destroyFederationExecution(Simulation.FEDERATION_NAME);
		} catch (FederatesCurrentlyJoined | FederationExecutionDoesNotExist | NotConnected | RTIinternalError e) {
			// TODO send interaction with the message of the exception
			e.printStackTrace();
		}
	}
	
	private void subscribeToInformInteraction() 
			throws FederateNotExecutionMember, NotConnected, NameNotFound, RTIinternalError, FederateServiceInvocationsAreBeingReportedViaMOM, InteractionClassNotDefined, SaveInProgress, RestoreInProgress {
		this.getController().log("LoggerSubscribingToInformInteraction");
		InteractionClassHandle informInteractionClassHandle = this.getRTIAmbassador().getInteractionClassHandle(HLAInformInteraction.INFORM_INTERACTION_NAME);
		this.getRTIAmbassador().subscribeInteractionClass(informInteractionClassHandle);
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
				try {
					hlaPerson = new HLAPerson(getPersonObjectClassHandle(), objectInstanceHandle, objectInstanceName);
				} catch (RTIinternalError | NameNotFound | InvalidObjectClassHandle | FederateNotExecutionMember | NotConnected e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
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
			HLAInformInteraction informInteraction;
			try {
				informInteraction = new HLAInformInteraction(interactionClassHandle);
			} catch (RTIinternalError | NameNotFound | InvalidInteractionClassHandle | FederateNotExecutionMember | NotConnected e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
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
