package edu.wesimulated.firstapp.simulation;

import hla.rti1516e.AttributeHandleSet;
import hla.rti1516e.AttributeHandleValueMap;
import hla.rti1516e.FederateAmbassador;
import hla.rti1516e.FederateHandle;
import hla.rti1516e.InteractionClassHandle;
import hla.rti1516e.LogicalTime;
import hla.rti1516e.MessageRetractionHandle;
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
import hla.rti1516e.exceptions.InteractionClassNotDefined;
import hla.rti1516e.exceptions.NameNotFound;
import hla.rti1516e.exceptions.NotConnected;
import hla.rti1516e.exceptions.ObjectClassNotDefined;
import hla.rti1516e.exceptions.RTIinternalError;
import hla.rti1516e.exceptions.RestoreInProgress;
import hla.rti1516e.exceptions.SaveInProgress;

import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import com.wesimulated.simulation.hla.DateLogicalTime;

import edu.wesimulated.firstapp.simulation.hla.HlaInformInteraction;
import edu.wesimulated.firstapp.simulation.hla.HlaPerson;
import edu.wesimulated.firstapp.view.SimulationOverviewController;

public class LoggerFederate extends AbstractFederate implements Observer {

	private SimulationOverviewController simulationOverviewController;
	private Map<ObjectInstanceHandle, HlaPerson> people;

	public static final String FEDERATE_NAME = "LOGGER_FEDERATE";

	public LoggerFederate() {
		super();
		this.people = new HashMap<>();
	}

	@Override
	public void update(Observable o, Object arg) {
		((SimulationEvent) arg).updateSimulation(null, this);
	}

	@Override
	protected AttributeHandleSet configurePerson() {
		AttributeHandleSet personAttributesHandle = super.configurePerson();
		this.getController().log("Configuring person interaction", null);
		try {
			this.getRTIAmbassador().subscribeObjectClassAttributes(this.getPersonObjectClassHandle(), personAttributesHandle);
		} catch (FederateNotExecutionMember | NotConnected | RTIinternalError | AttributeNotDefined | ObjectClassNotDefined | SaveInProgress | RestoreInProgress e) {
			throw new RuntimeException(e);
		}
		return personAttributesHandle;
	}

	@Override
	protected void configureInformInteraction() {
		super.configureInformInteraction();
		this.getController().log("Configuring inform interaction", null);
		InteractionClassHandle informInteractionClassHandle;
		try {
			informInteractionClassHandle = this.getRTIAmbassador().getInteractionClassHandle(HlaInformInteraction.INFORM_INTERACTION_NAME);
			this.getRTIAmbassador().subscribeInteractionClass(informInteractionClassHandle);
		} catch (NameNotFound | FederateNotExecutionMember | NotConnected | RTIinternalError | FederateServiceInvocationsAreBeingReportedViaMOM | InteractionClassNotDefined | SaveInProgress
				| RestoreInProgress e) {
			throw new RuntimeException(e);
		}
	}

	public void joinFederationExcecution(String federateName) {
		super.joinFederationExcecution(federateName, new LoggerFederateAmbassador());
	}

	private void personDiscovered(HlaPerson hlaPerson) {
		if (hlaPerson != null) {
			getController().log("Discovered person: " + hlaPerson.getObjectInstanceName(), null);
			getPeople().put(hlaPerson.getObjectInstanceHandle(), hlaPerson);
		}
	}

	private SimulationOverviewController getController() {
		return this.simulationOverviewController;
	}

	public void setSimulationOverviewController(SimulationOverviewController simulationOverviewController) {
		this.simulationOverviewController = simulationOverviewController;
	}

	public Map<ObjectInstanceHandle, HlaPerson> getPeople() {
		return this.people;
	}

	public class LoggerFederateAmbassador extends NullFederateAmbassador implements FederateAmbassador {

		@Override
		public void discoverObjectInstance(ObjectInstanceHandle objectInstanceHandle, ObjectClassHandle objectClassHandle, String objectInstanceName) throws FederateInternalError {
			discoverObjectInstance(objectInstanceHandle, objectClassHandle, objectInstanceName, null);
		}

		@Override
		public void discoverObjectInstance(ObjectInstanceHandle objectInstanceHandle, ObjectClassHandle objectClassHandle, String objectInstanceName, FederateHandle producingFederate)
				throws FederateInternalError {
			HlaPerson hlaPerson = null;
			hlaPerson = new HlaPerson(getRTIAmbassador(), objectClassHandle, objectInstanceHandle, objectInstanceName);
			personDiscovered(hlaPerson);
		}

		@Override
		public void reflectAttributeValues(ObjectInstanceHandle objectInstanceHandle, AttributeHandleValueMap attributeValues, byte[] userSuppliedTag, OrderType sentOrdering,
				TransportationTypeHandle theTransport, @SuppressWarnings("rawtypes") LogicalTime theTime, OrderType receivedOrdering, SupplementalReflectInfo reflectInfo) throws FederateInternalError {
			this.reflectAttributeValues(objectInstanceHandle, attributeValues, userSuppliedTag, sentOrdering, theTransport, theTime, receivedOrdering, null, reflectInfo);
		}

		@Override
		public void reflectAttributeValues(ObjectInstanceHandle objectInstanceHandle, AttributeHandleValueMap attributeValues, byte[] userSuppliedTag, OrderType sentOrdering,
				TransportationTypeHandle theTransport, @SuppressWarnings("rawtypes") LogicalTime theTime, OrderType receivedOrdering, MessageRetractionHandle retractionHandle, SupplementalReflectInfo reflectInfo)
				throws FederateInternalError {
			HlaPerson person = getPeople().get(objectInstanceHandle);
			person.reflectAttributeValues(attributeValues);
			getController().log(person.getWorkDone(), ((DateLogicalTime) theTime).getValue());
		}

		@Override
		public void receiveInteraction(InteractionClassHandle interactionClass, ParameterHandleValueMap theParameters, byte[] userSuppliedTag, OrderType sentOrdering,
				TransportationTypeHandle theTransport, SupplementalReceiveInfo receiveInfo) throws FederateInternalError {
		}

		@Override
		public void receiveInteraction(InteractionClassHandle interactionClass, ParameterHandleValueMap theParameters, byte[] userSuppliedTag, OrderType sentOrdering,
				TransportationTypeHandle theTransport, @SuppressWarnings("rawtypes") LogicalTime theTime, OrderType receivedOrdering, SupplementalReceiveInfo receiveInfo) throws FederateInternalError {
			this.receiveInteraction(interactionClass, theParameters, userSuppliedTag, sentOrdering, theTransport, theTime, receivedOrdering, null, receiveInfo);
		}

		@Override
		public void receiveInteraction(InteractionClassHandle interactionClass, ParameterHandleValueMap theParameters, byte[] userSuppliedTag, OrderType sentOrdering,
				TransportationTypeHandle theTransport, @SuppressWarnings("rawtypes") LogicalTime theTime, OrderType receivedOrdering, MessageRetractionHandle retractionHandle, SupplementalReceiveInfo receiveInfo)
				throws FederateInternalError {
			HlaInformInteraction informInteraction = new HlaInformInteraction(getRTIAmbassador(), interactionClass);
			informInteraction.receiveInteraction(theParameters);
			getController().log("Receive Interaction: " + informInteraction.getMessage(), ((DateLogicalTime) theTime).getValue());
		}
	}
}
