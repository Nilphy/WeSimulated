package edu.wesimulated.firstapp.simulation.hla;

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
import hla.rti1516e.exceptions.FederateInternalError;
import hla.rti1516e.exceptions.FederateNotExecutionMember;
import hla.rti1516e.exceptions.FederateServiceInvocationsAreBeingReportedViaMOM;
import hla.rti1516e.exceptions.InteractionClassNotDefined;
import hla.rti1516e.exceptions.NameNotFound;
import hla.rti1516e.exceptions.NotConnected;
import hla.rti1516e.exceptions.RTIinternalError;
import hla.rti1516e.exceptions.RestoreInProgress;
import hla.rti1516e.exceptions.SaveInProgress;

import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import com.wesimulated.simulation.hla.DateLogicalTime;

import edu.wesimulated.firstapp.simulation.SimulationEvent;
import edu.wesimulated.firstapp.simulation.domain.Person;
import edu.wesimulated.firstapp.simulation.domain.RoleBuilder;
import edu.wesimulated.firstapp.view.SimulationOverviewController;

public class LoggerFederate extends AbstractFederate implements Observer {

	private SimulationOverviewController simulationOverviewController;
	private Map<ObjectInstanceHandle, Person> people;

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

	private void personDiscovered(Person person) {
		if (person != null) {
			getController().log("Discovered person: " + person.getName(), null);
			getPeople().put(person.getHlaObjectInstanceHandle(), person);
		}
	}

	private SimulationOverviewController getController() {
		return this.simulationOverviewController;
	}

	public void setSimulationOverviewController(SimulationOverviewController simulationOverviewController) {
		this.simulationOverviewController = simulationOverviewController;
	}

	public Map<ObjectInstanceHandle, Person> getPeople() {
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
			personDiscovered(RoleBuilder.createFromHlaPerson(hlaPerson));
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
			Person person = getPeople().get(objectInstanceHandle);
			person.reflectAttributeValues(attributeValues);
			getController().log(person.getLastWorkDone(), ((DateLogicalTime) theTime).getValue());
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
