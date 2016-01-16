package edu.wesimulated.firstapp.simulation;

import hla.rti1516e.InteractionClassHandle;
import hla.rti1516e.LogicalTime;
import hla.rti1516e.ParameterHandleValueMap;
import hla.rti1516e.RTIambassador;
import hla.rti1516e.RtiFactoryFactory;
import hla.rti1516e.exceptions.FederateNotExecutionMember;
import hla.rti1516e.exceptions.InteractionClassNotDefined;
import hla.rti1516e.exceptions.InteractionClassNotPublished;
import hla.rti1516e.exceptions.InteractionParameterNotDefined;
import hla.rti1516e.exceptions.InvalidInteractionClassHandle;
import hla.rti1516e.exceptions.InvalidLogicalTime;
import hla.rti1516e.exceptions.NameNotFound;
import hla.rti1516e.exceptions.NotConnected;
import hla.rti1516e.exceptions.RTIinternalError;
import hla.rti1516e.exceptions.RestoreInProgress;
import hla.rti1516e.exceptions.SaveInProgress;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;

public class HLAInformInteraction {

	public static final String INFORM_INTERACTION_NAME = "InformInteraction";
	public static final String INFORM_INTERACTION_MESSAGE_PARAM_NAME = "Message";
	public static final String ENCODING = "UTF-8";
	
	private String message;
	private RTIambassador rtiAmbassador;
	private Object messageParamClass;
	private InteractionClassHandle interactionClass;

	public HLAInformInteraction(InteractionClassHandle interactionClass) throws RTIinternalError, NameNotFound, InvalidInteractionClassHandle, FederateNotExecutionMember, NotConnected {
		this.rtiAmbassador = RtiFactoryFactory.getRtiFactory().getRtiAmbassador();
		this.interactionClass = interactionClass;
		this.messageParamClass = this.getRtiAmbassador().getParameterHandle(interactionClass, INFORM_INTERACTION_MESSAGE_PARAM_NAME);
	}
	
	public HLAInformInteraction(InteractionClassHandle interactionClass, String mesage) throws RTIinternalError, NameNotFound, InvalidInteractionClassHandle, FederateNotExecutionMember, NotConnected {
		this(interactionClass);
		this.message = mesage;
	}

	public void receiveInteraction(ParameterHandleValueMap parameterValues) {
		this.message = decodeMessage(parameterValues.get(this.getMessageParamClass())); 
	}
	
	public void sendInteraction(LogicalTime<?, ?> logicalTime) 
			throws NameNotFound, InvalidInteractionClassHandle, FederateNotExecutionMember, NotConnected, RTIinternalError, InvalidLogicalTime, InteractionClassNotPublished, InteractionParameterNotDefined, 
			InteractionClassNotDefined, SaveInProgress, RestoreInProgress {
		ParameterHandleValueMap informInteractionParameterHandleValueMap = this.getRtiAmbassador().getParameterHandleValueMapFactory().create(1);
		informInteractionParameterHandleValueMap.put(this.getRtiAmbassador().getParameterHandle(this.getInteractionClass(), HLAInformInteraction.INFORM_INTERACTION_MESSAGE_PARAM_NAME), this.encodeMessage(this.getMessage()));
		this.rtiAmbassador.sendInteraction(this.getInteractionClass(), informInteractionParameterHandleValueMap, null, logicalTime);
	}
	
	public String getMessage() {
		return this.message;
	}
	
	public byte[] encodeMessage(String message) {
		return Charset.forName(ENCODING).encode(message).array();
	}
	
	public String decodeMessage(byte[] message) {
		return Charset.forName(ENCODING).decode(ByteBuffer.wrap(message)).toString();
	}
	
	public RTIambassador getRtiAmbassador() {
		return rtiAmbassador;
	}

	public void setRtiAmbassador(RTIambassador rtiAmbassador) {
		this.rtiAmbassador = rtiAmbassador;
	}

	public Object getMessageParamClass() {
		return messageParamClass;
	}

	public void setMessageParamClass(Object messageParamClass) {
		this.messageParamClass = messageParamClass;
	}

	public InteractionClassHandle getInteractionClass() {
		return interactionClass;
	}

	public void setInteractionClass(InteractionClassHandle interactionClass) {
		this.interactionClass = interactionClass;
	}
}
