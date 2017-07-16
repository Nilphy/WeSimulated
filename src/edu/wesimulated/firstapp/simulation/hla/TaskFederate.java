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

import edu.wesimulated.firstapp.simulation.SimulationEvent;
import edu.wesimulated.firstapp.simulation.TaskSimulatorBuilder;
import edu.wesimulated.firstapp.simulation.domain.Person;
import edu.wesimulated.firstapp.simulation.domain.Project;
import edu.wesimulated.firstapp.simulation.domain.Task;
import edu.wesimulated.firstapp.simulation.domain.avature.task.TaskSimulator;

public class TaskFederate extends AbstractFederate implements Observer, TimeControllerEntity {
	private TaskSimulator taskSimulator;
	private Task task;
	private Project project;

	public TaskFederate(Task task) {
		super();
		this.task = task;
	}

	public void requestTimeAdvance(Date newDate) {
		try {
			this.getRTIAmbassador().timeAdvanceRequest(new DateLogicalTime(newDate));
		} catch (LogicalTimeAlreadyPassed | InvalidLogicalTime | InTimeAdvancingState | RequestForTimeRegulationPending | RequestForTimeConstrainedPending | SaveInProgress | RestoreInProgress | FederateNotExecutionMember | NotConnected | RTIinternalError e) {
			throw new RuntimeException(e);
		}
	}

	public void timeRequestGranted(@SuppressWarnings("rawtypes") LogicalTime time) {
		this.taskSimulator.getSystemDynamicsExecutor().continueFromDate(((DateLogicalTime) time).getValue());
	}

	@Override
	public void update(Observable o, Object arg) {
		((SimulationEvent) arg).updateSimulation(this.taskSimulator, this);
	}

	@Override
	public void initClock(Date time) {
		this.taskSimulator.getSystemDynamicsExecutor().initClock(time, this);
	}

	public void discoverProject() {
		this.taskSimulator = TaskSimulatorBuilder.getInstance().build(this.task, this.project);
		this.project.addTask(this.task);
	}

	public void discoverTask(Task task) {
		this.project.addTask(task);
	}

	public void discoverPerson(Person person) {
		this.project.addPerson(person);
	}

	public void joinFederationExcecution(String federateName) {
		super.joinFederationExcecution(federateName, new TaskFederateAmbassador());
		try {
			ObjectInstanceHandle objectInstanceHandle;
			String objectInstanceName;
			objectInstanceHandle = getRTIAmbassador().registerObjectInstance(getObjectClassHandle(HlaClass.getHlaTaskClassInstance()));
			objectInstanceName = getRTIAmbassador().getObjectInstanceName(objectInstanceHandle);
			HlaTask hlaTask = new HlaTask(this.getRTIAmbassador(), getObjectClassHandle(HlaClass.getHlaTaskClassInstance()), objectInstanceHandle, objectInstanceName);
			this.task.setHlaTask(hlaTask);
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
		this.sendInformInteraction(message, new DateLogicalTime(this.taskSimulator.getSystemDynamicsExecutor().getClock().getCurrentDate()));
	}

	public class TaskFederateAmbassador extends NullFederateAmbassador implements FederateAmbassador {

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
