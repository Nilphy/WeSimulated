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
import java.util.HashMap;
import java.util.Map;

import com.wesimulated.simulation.hla.DateLogicalTimeFactory;

import edu.wesimulated.firstapp.simulation.Simulation;

public abstract class AbstractFederate {
	protected static final long LOOKAHEAD = 5000;

	private RTIambassador rtiAmbassador;
	private Map<HlaClass, ObjectClassHandle> objectClassHandles;
	private InteractionClassHandle informInteractionClassHandle;
	private ParameterHandle messageParameterHandle;

	public AbstractFederate() {
		this.setObjectClassHandles(new HashMap<>());
	}

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
			for (HlaClass hlaClass : HlaClass.getAllClasses()) {
				this.configureHlaClass(hlaClass);
			}
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
		} catch (CallNotAllowedFromWithinCallback | InconsistentFDD | ErrorReadingFDD | CouldNotOpenFDD | NotConnected | RTIinternalError | CouldNotCreateLogicalTimeFactory | FederationExecutionDoesNotExist | SaveInProgress | RestoreInProgress | FederateAlreadyExecutionMember | ConnectionFailed
				| InvalidLocalSettingsDesignator | UnsupportedCallbackModel | AlreadyConnected e) {
			throw new RuntimeException(e);
		}
	}

	private void configureHlaClass(HlaClass hlaClass) {
		try {
			setObjectClassHandle(hlaClass, getRTIAmbassador().getObjectClassHandle(hlaClass.getName()));
			AttributeHandleSet attributeHandles = this.getRTIAmbassador().getAttributeHandleSetFactory().create();
			for (HlaAttribute attributeName : hlaClass.getAttributes()) {
				attributeHandles.add(this.getRTIAmbassador().getAttributeHandle(this.getObjectClassHandle(hlaClass), attributeName.getName()));
			}
			getRTIAmbassador().publishObjectClassAttributes(this.getObjectClassHandle(hlaClass), attributeHandles);
			this.getRTIAmbassador().subscribeObjectClassAttributes(this.getObjectClassHandle(hlaClass), attributeHandles);
		} catch (NameNotFound | FederateNotExecutionMember | NotConnected | RTIinternalError | InvalidObjectClassHandle | AttributeNotDefined | ObjectClassNotDefined | SaveInProgress | RestoreInProgress e) {
			throw new RuntimeException(e);
		}
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

	protected ObjectClassHandle getObjectClassHandle(HlaClass hlaClass) {
		return this.getObjectClassHandles().get(hlaClass);
	}

	protected void setObjectClassHandle(HlaClass hlaClass, ObjectClassHandle objectClassHandle) {
		this.getObjectClassHandles().put(hlaClass, objectClassHandle);
	}

	private Map<HlaClass, ObjectClassHandle> getObjectClassHandles() {
		return objectClassHandles;
	}

	private void setObjectClassHandles(Map<HlaClass, ObjectClassHandle> objectClassHandles) {
		this.objectClassHandles = objectClassHandles;
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
