package edu.wesimulated.firstapp.simulation.hla;

import hla.rti1516e.AttributeHandleSet;
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
import hla.rti1516e.exceptions.FederatesCurrentlyJoined;
import hla.rti1516e.exceptions.FederationExecutionAlreadyExists;
import hla.rti1516e.exceptions.FederationExecutionDoesNotExist;
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
import hla.rti1516e.exceptions.RestoreInProgress;
import hla.rti1516e.exceptions.SaveInProgress;
import hla.rti1516e.exceptions.UnsupportedCallbackModel;

import java.net.URL;

import com.wesimulated.simulation.hla.DateLogicalTimeFactory;

import edu.wesimulated.firstapp.simulation.Simulation;

public class AbstractFederate {
	protected static final long LOOKAHEAD = 5000;

	private RTIambassador rtiAmbassador;
	private ObjectClassHandle personObjectClassHandle;
	private InteractionClassHandle informInteractionClassHandle;
	private ParameterHandle messageParameterHandle;

	protected void joinFederationExcecution(String federateName, NullFederateAmbassador federateAmbassador) {
		try {
			rtiAmbassador = RtiFactoryFactory.getRtiFactory().getRtiAmbassador();
			this.getRTIAmbassador().connect(federateAmbassador, CallbackModel.HLA_IMMEDIATE);
			try {
				getRTIAmbassador().createFederationExecution(Simulation.FEDERATION_NAME, new URL[] { Simulation.class.getResource(Simulation.FDD) }, DateLogicalTimeFactory.NAME);
			} catch (FederationExecutionAlreadyExists feae) {
				System.out.println("The federation has already been created by another federate");
			}
			this.getRTIAmbassador().joinFederationExecution(federateName, Simulation.FEDERATION_NAME);
			this.configurePerson();
			this.configureInformInteraction();
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

	protected AttributeHandleSet configurePerson() {
		AttributeHandleSet personAttributeHandles = null;
		try {
			setPersonObjectClassHandle(getRTIAmbassador().getObjectClassHandle(HlaPerson.CLASS_NAME));
			personAttributeHandles = this.getRTIAmbassador().getAttributeHandleSetFactory().create();
			personAttributeHandles.add(this.getRTIAmbassador().getAttributeHandle(getPersonObjectClassHandle(), HlaPerson.ATTRIBUTE_WORK_DONE_NAME));
			getRTIAmbassador().publishObjectClassAttributes(this.getPersonObjectClassHandle(), personAttributeHandles);
		} catch (NameNotFound | FederateNotExecutionMember | NotConnected | RTIinternalError | InvalidObjectClassHandle | AttributeNotDefined | ObjectClassNotDefined | SaveInProgress
				| RestoreInProgress e) {
			throw new RuntimeException(e);
		}
		return personAttributeHandles;
	}

	protected void configureInformInteraction() {
		try {
			setInformInteractionClassHandle(getRTIAmbassador().getInteractionClassHandle(HlaInformInteraction.INFORM_INTERACTION_NAME));
			setMessageParameterHandle(getRTIAmbassador().getParameterHandle(getInformInteractionClassHandle(), HlaInformInteraction.INFORM_INTERACTION_MESSAGE_PARAM_NAME));
			getRTIAmbassador().publishInteractionClass(getInformInteractionClassHandle());
		} catch (NameNotFound | FederateNotExecutionMember | NotConnected | RTIinternalError | InvalidInteractionClassHandle | InteractionClassNotDefined | SaveInProgress | RestoreInProgress e) {
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

	public void resignFromFederation() {
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
