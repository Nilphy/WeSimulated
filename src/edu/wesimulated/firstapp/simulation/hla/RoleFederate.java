package edu.wesimulated.firstapp.simulation.hla;

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
import hla.rti1516e.exceptions.FederateInternalError;
import hla.rti1516e.exceptions.FederateNotExecutionMember;
import hla.rti1516e.exceptions.IllegalTimeArithmetic;
import hla.rti1516e.exceptions.InTimeAdvancingState;
import hla.rti1516e.exceptions.InvalidLogicalTime;
import hla.rti1516e.exceptions.InvalidLogicalTimeInterval;
import hla.rti1516e.exceptions.InvalidLookahead;
import hla.rti1516e.exceptions.LogicalTimeAlreadyPassed;
import hla.rti1516e.exceptions.NotConnected;
import hla.rti1516e.exceptions.ObjectClassNotDefined;
import hla.rti1516e.exceptions.ObjectClassNotPublished;
import hla.rti1516e.exceptions.ObjectInstanceNotKnown;
import hla.rti1516e.exceptions.RTIinternalError;
import hla.rti1516e.exceptions.RequestForTimeConstrainedPending;
import hla.rti1516e.exceptions.RequestForTimeRegulationPending;
import hla.rti1516e.exceptions.RestoreInProgress;
import hla.rti1516e.exceptions.SaveInProgress;
import hla.rti1516e.exceptions.TimeConstrainedAlreadyEnabled;
import hla.rti1516e.exceptions.TimeRegulationAlreadyEnabled;

import java.time.Duration;
import java.util.Date;
import java.util.Observable;
import java.util.Observer;

import com.wesimulated.simulation.hla.DateLogicalTime;
import com.wesimulated.simulation.hla.DateLogicalTimeInterval;
import com.wesimulated.simulationmotor.des.TimeControllerEntity;

import edu.wesimulated.firstapp.simulation.RoleSimulatorBuilder;
import edu.wesimulated.firstapp.simulation.SimulationEvent;
import edu.wesimulated.firstapp.simulation.domain.Person;
import edu.wesimulated.firstapp.simulation.domain.Project;
import edu.wesimulated.firstapp.simulation.domain.Role;
import edu.wesimulated.firstapp.simulation.domain.Task;
import edu.wesimulated.firstapp.simulation.domain.avature.role.RoleSimulator;

public class RoleFederate extends AbstractFederate implements Observer, TimeControllerEntity {
	private RoleSimulator roleSimulator;
	private Project project;
	private Role role;
	private Person person;

	public RoleFederate(Role role, Person person) {
		super();
		this.role = role;
		this.person = person;
	}

	public void requestTimeAdvance(Date newDate) {
		try {
			this.getRTIAmbassador().timeAdvanceRequest(new DateLogicalTime(newDate));
		} catch (LogicalTimeAlreadyPassed | InvalidLogicalTime | InTimeAdvancingState | RequestForTimeRegulationPending | RequestForTimeConstrainedPending | SaveInProgress | RestoreInProgress | FederateNotExecutionMember | NotConnected | RTIinternalError e) {
			throw new RuntimeException(e);
		}
	}

	public void timeRequestGranted(@SuppressWarnings("rawtypes") LogicalTime time) {
		this.roleSimulator.getOperationBasedExecutor().continueFromDate(((DateLogicalTime) time).getValue());
	}

	@Override
	public void update(Observable o, Object arg) {
		((SimulationEvent) arg).updateSimulation(this.roleSimulator, this);
	}

	@Override
	public void initClock(Date time) {
		this.roleSimulator.getOperationBasedExecutor().initClock(time, this);
	}

	public void discoverProject() {
		this.roleSimulator = RoleSimulatorBuilder.build(this.role, this.project, this.person);
	}

	public void discoverPerson(Person person) {
		this.project.addPerson(person);
	}

	public void discoverTask(Task task) {
		this.project.addTask(task);
	}

	public void joinFederationExcecution(String federateName) {
		super.joinFederationExcecution(federateName, new PersonFederateAmbassador());
		try {
			// No va a pasar acá, primero hay que saber que persona va a estar cumpliendo el rol
			ObjectInstanceHandle objectInstanceHandle;
			String objectInstanceName;
			objectInstanceHandle = getRTIAmbassador().registerObjectInstance(getObjectClassHandle(HlaClass.getHlaPersonClassInstance()));
			objectInstanceName = getRTIAmbassador().getObjectInstanceName(objectInstanceHandle);
			HlaPerson hlaPerson = new HlaPerson(this.getRTIAmbassador(), getObjectClassHandle(HlaClass.getHlaPersonClassInstance()), objectInstanceHandle, objectInstanceName);
			this.getRTIAmbassador().enableTimeConstrained();
			this.getRTIAmbassador().enableTimeRegulation(new DateLogicalTimeInterval(Duration.ofMillis(LOOKAHEAD)));
		} catch (InvalidLookahead | InTimeAdvancingState | RequestForTimeRegulationPending | TimeRegulationAlreadyEnabled | SaveInProgress | RestoreInProgress | FederateNotExecutionMember | NotConnected | RTIinternalError | RequestForTimeConstrainedPending | TimeConstrainedAlreadyEnabled
				| ObjectClassNotPublished | ObjectClassNotDefined | ObjectInstanceNotKnown e) {
			throw new RuntimeException(e);
		}
	}

	protected void sendInformInteraction(String message, @SuppressWarnings("rawtypes") LogicalTime time) {
		HlaInformInteraction hlaInformInteraction = new HlaInformInteraction(getRTIAmbassador(), getInformInteractionClassHandle(), message);
		hlaInformInteraction.sendInteraction(time);
	}

	protected void sendInformInteraction(String message) {
		this.sendInformInteraction(message, new DateLogicalTime(this.roleSimulator.getOperationBasedExecutor().getClock().getCurrentDate()));
	}

	public class PersonFederateAmbassador extends NullFederateAmbassador implements FederateAmbassador {

		@Override
		public void discoverObjectInstance(ObjectInstanceHandle objectInstanceHandle, ObjectClassHandle objectClassHandle, String objectInstanceName) throws FederateInternalError {
			discoverObjectInstance(objectInstanceHandle, objectClassHandle, objectInstanceName, null);
			sendInformInteraction("discoverObjectInstance");
		}

		@Override
		public void discoverObjectInstance(ObjectInstanceHandle objectInstanceHandle, ObjectClassHandle objectClassHandle, String objectInstanceName, FederateHandle producingFederate) throws FederateInternalError {
			sendInformInteraction("discoverObjectInstance");
		}

		@Override
		public void reflectAttributeValues(ObjectInstanceHandle objectInstanceHandle, AttributeHandleValueMap attributeValues, byte[] tag, OrderType sentOrdering, TransportationTypeHandle transportationTypeHandle, SupplementalReflectInfo reflectInfo) throws FederateInternalError {
			sendInformInteraction("reflectAttributeValues");
		}

		@Override
		public void receiveInteraction(InteractionClassHandle interactionClassHandle, ParameterHandleValueMap parameterValues, byte[] tag, OrderType sentOrdering, TransportationTypeHandle transportationTypeHandle, SupplementalReceiveInfo receiveInfo) throws FederateInternalError {
			sendInformInteraction("receiveInteraction");
		}

		@Override
		public void removeObjectInstance(ObjectInstanceHandle objectInstanceHandle, byte[] tag, OrderType sentOrdering, SupplementalRemoveInfo removeInfo) throws FederateInternalError {
			sendInformInteraction("removeObjectInstance");
		}

		@Override
		public void provideAttributeValueUpdate(ObjectInstanceHandle objectInstanceHandle, AttributeHandleSet attributeHandles, byte[] tag) throws FederateInternalError {
			sendInformInteraction("provideAttributeValueUpdate");
		}

		@Override
		public void requestAttributeOwnershipAssumption(ObjectInstanceHandle objectInstanceHandle, AttributeHandleSet attributeHandles, byte[] tag) throws FederateInternalError {
			sendInformInteraction("requestAttributeOwnershipAssumption");
		}

		@Override
		public void attributeOwnershipAcquisitionNotification(ObjectInstanceHandle objectInstanceHandle, AttributeHandleSet attributeHandles, byte[] tag) throws FederateInternalError {
			sendInformInteraction("attributeOwnershipAcquisitionNotification");
		}

		@Override
		public void attributeOwnershipUnavailable(ObjectInstanceHandle objectInstanceHandle, AttributeHandleSet attributeHandles) throws FederateInternalError {
			sendInformInteraction("attributeOwnershipUnavailable");
		}

		@Override
		public void requestAttributeOwnershipRelease(ObjectInstanceHandle objectInstanceHandle, AttributeHandleSet attributeHandles, byte[] tag) throws FederateInternalError {
			sendInformInteraction("requestAttributeOwnershipRelease");
		}

		@SuppressWarnings("rawtypes")
		@Override
		public void timeRegulationEnabled(LogicalTime time) throws FederateInternalError {
			initClock(((DateLogicalTime) time).getValue());
			System.out.println("timeRegulationEnabled");
		}

		@SuppressWarnings({ "rawtypes", "unchecked" })
		@Override
		public void timeAdvanceGrant(LogicalTime time) {
			timeRequestGranted(time);
			try {
				sendInformInteraction("timeAdvanceGrant", time.add(new DateLogicalTimeInterval(Duration.ofMillis(6))));
			} catch (IllegalTimeArithmetic | InvalidLogicalTimeInterval e) {
				throw new RuntimeException(e);
			}
		}
	}
}
