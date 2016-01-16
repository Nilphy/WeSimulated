package edu.wesimulated.firstapp.simulation;

import hla.rti1516e.CallbackModel;
import hla.rti1516e.InteractionClassHandle;
import hla.rti1516e.NullFederateAmbassador;
import hla.rti1516e.ObjectClassHandle;
import hla.rti1516e.ParameterHandle;
import hla.rti1516e.RTIambassador;
import hla.rti1516e.ResignAction;
import hla.rti1516e.RtiFactoryFactory;
import hla.rti1516e.exceptions.AlreadyConnected;
import hla.rti1516e.exceptions.AttributeNotDefined;
import hla.rti1516e.exceptions.CallNotAllowedFromWithinCallback;
import hla.rti1516e.exceptions.ConnectionFailed;
import hla.rti1516e.exceptions.CouldNotCreateLogicalTimeFactory;
import hla.rti1516e.exceptions.CouldNotOpenFDD;
import hla.rti1516e.exceptions.ErrorReadingFDD;
import hla.rti1516e.exceptions.FederateAlreadyExecutionMember;
import hla.rti1516e.exceptions.FederateNotExecutionMember;
import hla.rti1516e.exceptions.FederateOwnsAttributes;
import hla.rti1516e.exceptions.FederateServiceInvocationsAreBeingReportedViaMOM;
import hla.rti1516e.exceptions.FederatesCurrentlyJoined;
import hla.rti1516e.exceptions.FederationExecutionAlreadyExists;
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

public class AbstractFederate {

	private RTIambassador rtiAmbassador;
	private ObjectClassHandle personObjectClassHandle;
	

	private InteractionClassHandle informInteractionClassHandle;
	private ParameterHandle messageParameterHandle;

	public AbstractFederate() throws RTIinternalError {
		rtiAmbassador = RtiFactoryFactory.getRtiFactory().getRtiAmbassador();
	}

	protected void joinFederationExcecution(String federateName, NullFederateAmbassador federateAmbassador) 
			throws ConnectionFailed, InvalidLocalSettingsDesignator, UnsupportedCallbackModel, AlreadyConnected, CallNotAllowedFromWithinCallback, RTIinternalError, InconsistentFDD, 
			ErrorReadingFDD, CouldNotOpenFDD, NotConnected, CouldNotCreateLogicalTimeFactory, FederationExecutionDoesNotExist, SaveInProgress, RestoreInProgress, 
			FederateAlreadyExecutionMember, NameNotFound, FederateNotExecutionMember, InvalidInteractionClassHandle, InteractionClassNotDefined, InTimeAdvancingState, 
			RequestForTimeConstrainedPending, TimeConstrainedAlreadyEnabled, InvalidObjectClassHandle, AttributeNotDefined, ObjectClassNotDefined, FederateServiceInvocationsAreBeingReportedViaMOM {
		getRTIAmbassador().connect(federateAmbassador, CallbackModel.HLA_IMMEDIATE);
		try {
			getRTIAmbassador().createFederationExecution(Simulation.FEDERATION_NAME, Simulation.class.getResource(Simulation.FDD));
		} catch (FederationExecutionAlreadyExists feae) {
			// The federation has already been created by another federate
		}
		getRTIAmbassador().joinFederationExecution(federateName, Simulation.FEDERATION_NAME);
		getRTIAmbassador().enableTimeConstrained();
	}
	
	public void destroyFederationExecution() throws FederatesCurrentlyJoined, FederationExecutionDoesNotExist, NotConnected, RTIinternalError {
		getRTIAmbassador().destroyFederationExecution(Simulation.FEDERATION_NAME);
	}
	
	protected void resignFromFederation() {
		try {
			getRTIAmbassador().resignFederationExecution(ResignAction.DELETE_OBJECTS);
		} catch (InvalidResignAction | OwnershipAcquisitionPending | FederateOwnsAttributes | FederateNotExecutionMember | NotConnected | CallNotAllowedFromWithinCallback | RTIinternalError e) {
			// TODO send interaction with the message of the exception
			e.printStackTrace();
		}
	}
	
	protected ObjectClassHandle getPersonObjectClassHandle() {
		return personObjectClassHandle;
	}

	protected void setProgrammerObjectClassHandle(ObjectClassHandle programmerObjectClassHandle) {
		this.personObjectClassHandle = programmerObjectClassHandle;
	}
	
	protected InteractionClassHandle getInformInteractionClassHandle() {
		return informInteractionClassHandle;
	}

	protected void setInformInteractionClassHandle(InteractionClassHandle informInteractionClassHandle) {
		this.informInteractionClassHandle = informInteractionClassHandle;
	}
	
	protected ParameterHandle getMessageParameterHandle() {
		return messageParameterHandle;
	}

	protected void setMessageParameterHandle(ParameterHandle messageParameterHandle) {
		this.messageParameterHandle = messageParameterHandle;
	}

	public RTIambassador getRTIAmbassador() {
		return rtiAmbassador;
	}
}
