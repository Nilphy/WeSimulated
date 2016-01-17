package edu.wesimulated.firstapp.simulation;

import hla.rti1516e.AttributeHandleSet;
import hla.rti1516e.AttributeHandleValueMap;
import hla.rti1516e.FederateAmbassador;
import hla.rti1516e.FederateHandle;
import hla.rti1516e.InteractionClassHandle;
import hla.rti1516e.LogicalTime;
import hla.rti1516e.NullFederateAmbassador;
import hla.rti1516e.ObjectClassHandle;
import hla.rti1516e.ObjectInstanceHandle;
import hla.rti1516e.OrderType;
import hla.rti1516e.ParameterHandleValueMap;
import hla.rti1516e.TransportationTypeHandle;
import hla.rti1516e.exceptions.AttributeNotDefined;
import hla.rti1516e.exceptions.FederateInternalError;
import hla.rti1516e.exceptions.FederateNotExecutionMember;
import hla.rti1516e.exceptions.InTimeAdvancingState;
import hla.rti1516e.exceptions.InteractionClassNotDefined;
import hla.rti1516e.exceptions.InvalidInteractionClassHandle;
import hla.rti1516e.exceptions.InvalidLogicalTime;
import hla.rti1516e.exceptions.InvalidLookahead;
import hla.rti1516e.exceptions.InvalidObjectClassHandle;
import hla.rti1516e.exceptions.LogicalTimeAlreadyPassed;
import hla.rti1516e.exceptions.NameNotFound;
import hla.rti1516e.exceptions.NotConnected;
import hla.rti1516e.exceptions.ObjectClassNotDefined;
import hla.rti1516e.exceptions.ObjectClassNotPublished;
import hla.rti1516e.exceptions.ObjectInstanceNotKnown;
import hla.rti1516e.exceptions.RTIinternalError;
import hla.rti1516e.exceptions.RequestForTimeConstrainedPending;
import hla.rti1516e.exceptions.RequestForTimeRegulationPending;
import hla.rti1516e.exceptions.RestoreInProgress;
import hla.rti1516e.exceptions.SaveInProgress;
import hla.rti1516e.exceptions.TimeRegulationAlreadyEnabled;

import java.time.Duration;
import java.util.Date;
import java.util.Observable;
import java.util.Observer;

import com.wesimulated.simulation.hla.DateLogicalTime;
import com.wesimulated.simulation.hla.DateLogicalTimeInterval;
import com.wesimulated.simulationmotor.operationbased.TimeControllerEntity;

import edu.wesimulated.firstapp.model.Person;

public class PersonFederate extends AbstractFederate implements Observer, TimeControllerEntity {

	public static final String FEDERATE_NAME = "PERSON_FEDERATE";
	private static final long LOOKAHEAD = 5000;
	private PersonSimulator personSimulator;

	public PersonFederate(Person person) {
		super();
		ObjectInstanceHandle objectInstanceHandle;
		String objectInstanceName;
		try {
			objectInstanceHandle = getRTIAmbassador().registerObjectInstance(getPersonObjectClassHandle());
			objectInstanceName = getRTIAmbassador().getObjectInstanceName(objectInstanceHandle);
		} catch (ObjectClassNotPublished | ObjectClassNotDefined | SaveInProgress | RestoreInProgress | FederateNotExecutionMember | NotConnected | RTIinternalError | ObjectInstanceNotKnown e) {
			throw new RuntimeException(e);
		}
		HLAPerson hlaPerson = new HLAPerson(getPersonObjectClassHandle(), objectInstanceHandle, objectInstanceName);
		this.personSimulator = PersonSimulatorBuilder.build(person, hlaPerson, ProjectSimulator.getInstance().getStartDate());
		ProjectSimulator.getInstance().addPerson(this.personSimulator);
	}

	public void requestTimeAdvance(Date newDate) {
		try {
			this.getRTIAmbassador().timeAdvanceRequest(new DateLogicalTime(newDate));
		} catch (LogicalTimeAlreadyPassed | InvalidLogicalTime | InTimeAdvancingState | RequestForTimeRegulationPending | RequestForTimeConstrainedPending | SaveInProgress | RestoreInProgress
				| FederateNotExecutionMember | NotConnected | RTIinternalError e) {
			throw new RuntimeException(e);
		}
	}

	public void timeRequestGranted(LogicalTime time) {
		this.personSimulator.getExecutor().continueFromDate((DateLogicalTime) time);
	}

	@Override
	public void update(Observable o, Object arg) {
		((SimulationEvent) arg).updateSimulation(this.personSimulator, this);
	}

	public void initClock(DateLogicalTime time) {
		this.personSimulator.getExecutor().initClock(time, this);

	}

	protected void joinFederationExcecution(String federateName) {
		super.joinFederationExcecution(federateName, new PersonFederateAmbassador());
		this.publishPerson();
		this.publishInformInteraction();
		try {
			this.getRTIAmbassador().enableTimeRegulation(new DateLogicalTimeInterval(Duration.ofMillis(LOOKAHEAD)));
		} catch (InvalidLookahead | InTimeAdvancingState | RequestForTimeRegulationPending | TimeRegulationAlreadyEnabled | SaveInProgress | RestoreInProgress | FederateNotExecutionMember
				| NotConnected | RTIinternalError e) {
			throw new RuntimeException(e);
		}
	}

	protected void publishPerson() {
		try {
			setProgrammerObjectClassHandle(getRTIAmbassador().getObjectClassHandle(HLAPerson.CLASS_NAME));
			AttributeHandleSet personAttributeHandles = getRTIAmbassador().getAttributeHandleSetFactory().create();
			personAttributeHandles.add(getRTIAmbassador().getAttributeHandle(getPersonObjectClassHandle(), HLAPerson.ATTRIBUTE_WORK_DONE_NAME));
			getRTIAmbassador().publishObjectClassAttributes(getPersonObjectClassHandle(), personAttributeHandles);
		} catch (NameNotFound | FederateNotExecutionMember | NotConnected | RTIinternalError | InvalidObjectClassHandle | AttributeNotDefined | ObjectClassNotDefined | SaveInProgress
				| RestoreInProgress e) {
			throw new RuntimeException(e);
		}
	}

	private void publishInformInteraction() {
		try {
			setInformInteractionClassHandle(getRTIAmbassador().getInteractionClassHandle(HLAInformInteraction.INFORM_INTERACTION_NAME));
			setMessageParameterHandle(getRTIAmbassador().getParameterHandle(getInformInteractionClassHandle(), HLAInformInteraction.INFORM_INTERACTION_MESSAGE_PARAM_NAME));
			getRTIAmbassador().publishInteractionClass(getInformInteractionClassHandle());
		} catch (NameNotFound | FederateNotExecutionMember | NotConnected | RTIinternalError | InvalidInteractionClassHandle | InteractionClassNotDefined | SaveInProgress | RestoreInProgress e) {
			throw new RuntimeException(e);
		}
	}
	}
	
	public class PersonFederateAmbassador extends NullFederateAmbassador implements FederateAmbassador {

		@Override
		public void discoverObjectInstance(ObjectInstanceHandle objectInstanceHandle, ObjectClassHandle objectClassHandle, String objectInstanceName) throws FederateInternalError {
			discoverObjectInstance(objectInstanceHandle, objectClassHandle, objectInstanceName, null);
			// TODO replace all System.out.println to send interaction
			System.out.println("discoverObjectInstance");
		}

		@Override
		public void discoverObjectInstance(ObjectInstanceHandle objectInstanceHandle, ObjectClassHandle objectClassHandle, String objectInstanceName, FederateHandle producingFederate)
				throws FederateInternalError {
			System.out.println("discoverObjectInstance");
		}

		@Override
		public void reflectAttributeValues(ObjectInstanceHandle objectInstanceHandle, AttributeHandleValueMap attributeValues, byte[] tag, OrderType sentOrdering,
				TransportationTypeHandle transportationTypeHandle, SupplementalReflectInfo reflectInfo) throws FederateInternalError {
			System.out.println("reflectAttributeValues");
		}

		@Override
		public void receiveInteraction(InteractionClassHandle interactionClassHandle, ParameterHandleValueMap parameterValues, byte[] tag, OrderType sentOrdering,
				TransportationTypeHandle transportationTypeHandle, SupplementalReceiveInfo receiveInfo) throws FederateInternalError {
			System.out.println("receiveInteraction");
		}

		@Override
		public void removeObjectInstance(ObjectInstanceHandle objectInstanceHandle, byte[] tag, OrderType sentOrdering, SupplementalRemoveInfo removeInfo) throws FederateInternalError {
			System.out.println("removeObjectInstance");
		}

		@Override
		public void provideAttributeValueUpdate(ObjectInstanceHandle objectInstanceHandle, AttributeHandleSet attributeHandles, byte[] tag) throws FederateInternalError {
			System.out.println("provideAttributeValueUpdate");
		}

		@Override
		public void requestAttributeOwnershipAssumption(ObjectInstanceHandle objectInstanceHandle, AttributeHandleSet attributeHandles, byte[] tag) throws FederateInternalError {
			System.out.println("requestAttributeOwnershipAssumption");
		}

		@Override
		public void attributeOwnershipAcquisitionNotification(ObjectInstanceHandle objectInstanceHandle, AttributeHandleSet attributeHandles, byte[] tag) throws FederateInternalError {
			System.out.println("attributeOwnershipAcquisitionNotification");
		}

		@Override
		public void attributeOwnershipUnavailable(ObjectInstanceHandle objectInstanceHandle, AttributeHandleSet attributeHandles) throws FederateInternalError {
			System.out.println("attributeOwnershipUnavailable");
		}

		@Override
		public void requestAttributeOwnershipRelease(ObjectInstanceHandle objectInstanceHandle, AttributeHandleSet attributeHandles, byte[] tag) throws FederateInternalError {
			System.out.println("requestAttributeOwnershipRelease");
		}
		
		@Override
		public void timeRegulationEnabled(LogicalTime time) throws FederateInternalError {
			initClock((DateLogicalTime) time);
		}
		
		@Override
		public void timeAdvanceGrant(LogicalTime time) {
			timeRequestGranted(time);
		}
	}
}
