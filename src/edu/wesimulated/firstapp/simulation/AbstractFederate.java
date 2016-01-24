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
import hla.rti1516e.exceptions.CallNotAllowedFromWithinCallback;
import hla.rti1516e.exceptions.ConnectionFailed;
import hla.rti1516e.exceptions.CouldNotCreateLogicalTimeFactory;
import hla.rti1516e.exceptions.CouldNotOpenFDD;
import hla.rti1516e.exceptions.ErrorReadingFDD;
import hla.rti1516e.exceptions.FederateAlreadyExecutionMember;
import hla.rti1516e.exceptions.FederateNotExecutionMember;
import hla.rti1516e.exceptions.FederateOwnsAttributes;
import hla.rti1516e.exceptions.FederatesCurrentlyJoined;
import hla.rti1516e.exceptions.FederationExecutionAlreadyExists;
import hla.rti1516e.exceptions.FederationExecutionDoesNotExist;
import hla.rti1516e.exceptions.InTimeAdvancingState;
import hla.rti1516e.exceptions.InconsistentFDD;
import hla.rti1516e.exceptions.InvalidLocalSettingsDesignator;
import hla.rti1516e.exceptions.InvalidResignAction;
import hla.rti1516e.exceptions.NotConnected;
import hla.rti1516e.exceptions.OwnershipAcquisitionPending;
import hla.rti1516e.exceptions.RTIinternalError;
import hla.rti1516e.exceptions.RequestForTimeConstrainedPending;
import hla.rti1516e.exceptions.RestoreInProgress;
import hla.rti1516e.exceptions.SaveInProgress;
import hla.rti1516e.exceptions.TimeConstrainedAlreadyEnabled;
import hla.rti1516e.exceptions.UnsupportedCallbackModel;

import java.net.URL;

import com.wesimulated.simulation.hla.DateLogicalTimeFactory;

public class AbstractFederate {

	private RTIambassador rtiAmbassador;
	private ObjectClassHandle personObjectClassHandle;
	private InteractionClassHandle informInteractionClassHandle;
	private ParameterHandle messageParameterHandle;

	protected void joinFederationExcecution(String federateName, NullFederateAmbassador federateAmbassador) {
		try {
			rtiAmbassador = RtiFactoryFactory.getRtiFactory().getRtiAmbassador();
			this.getRTIAmbassador().connect(federateAmbassador, CallbackModel.HLA_IMMEDIATE);
			try {
				getRTIAmbassador().createFederationExecution(Simulation.FEDERATION_NAME, new URL[] {Simulation.class.getResource(Simulation.FDD)}, DateLogicalTimeFactory.NAME);
			} catch (FederationExecutionAlreadyExists feae) {
				System.out.println("The federation has already been created by another federate");
			}
			this.getRTIAmbassador().joinFederationExecution(federateName, Simulation.FEDERATION_NAME);
			new Thread(() -> {
				try {
					while (true) {
						getRTIAmbassador().evokeMultipleCallbacks(1, 1);
					}
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}).start();
		} catch (CallNotAllowedFromWithinCallback | InconsistentFDD | ErrorReadingFDD | CouldNotOpenFDD | NotConnected | RTIinternalError | CouldNotCreateLogicalTimeFactory
				| FederationExecutionDoesNotExist | SaveInProgress | RestoreInProgress | FederateAlreadyExecutionMember | ConnectionFailed | InvalidLocalSettingsDesignator | UnsupportedCallbackModel
				| AlreadyConnected e) {
			throw new RuntimeException(e);
		}
	}
			throw new RuntimeException(e);
		}
	}

	public void destroyFederationExecution() {
		try {
			getRTIAmbassador().destroyFederationExecution(Simulation.FEDERATION_NAME);
		} catch (FederatesCurrentlyJoined | FederationExecutionDoesNotExist | NotConnected | RTIinternalError e) {
			throw new RuntimeException(e);
		}
	}

	protected void resignFromFederation() {
		try {
			getRTIAmbassador().resignFederationExecution(ResignAction.DELETE_OBJECTS);
		} catch (InvalidResignAction | OwnershipAcquisitionPending | FederateOwnsAttributes | FederateNotExecutionMember | NotConnected | CallNotAllowedFromWithinCallback | RTIinternalError e) {
			throw new RuntimeException(e);
		}
	}

	protected ObjectClassHandle getPersonObjectClassHandle() {
		return personObjectClassHandle;
	}

	protected void setPersonObjectClassHandle(ObjectClassHandle programmerObjectClassHandle) {
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
