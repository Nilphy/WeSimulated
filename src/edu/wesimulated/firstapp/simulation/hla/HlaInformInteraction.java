package edu.wesimulated.firstapp.simulation.hla;

import hla.rti1516e.InteractionClassHandle;
import hla.rti1516e.LogicalTime;
import hla.rti1516e.ParameterHandleValueMap;
import hla.rti1516e.RTIambassador;
import hla.rti1516e.exceptions.FederateNotExecutionMember;
import hla.rti1516e.exceptions.InvalidInteractionClassHandle;
import hla.rti1516e.exceptions.NameNotFound;
import hla.rti1516e.exceptions.NotConnected;
import hla.rti1516e.exceptions.RTIinternalError;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;

public class HlaInformInteraction {

	public static final String INFORM_INTERACTION_NAME = "InformInteraction";
	public static final String INFORM_INTERACTION_MESSAGE_PARAM_NAME = "Message";
	public static final String ENCODING = "UTF-8";

	private String message;
	private RTIambassador rtiAmbassador;
	private Object messageParamClass;
	private InteractionClassHandle interactionClass;

	public HlaInformInteraction(RTIambassador rtiAmbassador, InteractionClassHandle interactionClass) {
		try {
			this.rtiAmbassador = rtiAmbassador;
			this.messageParamClass = this.getRtiAmbassador().getParameterHandle(interactionClass, INFORM_INTERACTION_MESSAGE_PARAM_NAME);
		} catch (RTIinternalError | NameNotFound | InvalidInteractionClassHandle | FederateNotExecutionMember | NotConnected e) {
			throw new RuntimeException(e);
		}
		this.interactionClass = interactionClass;
	}

	public HlaInformInteraction(RTIambassador rtiAmbassador, InteractionClassHandle interactionClass, String mesage) {
		this(rtiAmbassador, interactionClass);
		this.message = mesage;
	}

	public void receiveInteraction(ParameterHandleValueMap parameterValues) {
		this.message = decodeMessage(parameterValues.get(this.getMessageParamClass()));
	}

	public void sendInteraction(@SuppressWarnings("rawtypes") LogicalTime logicalTime) {
		ParameterHandleValueMap informInteractionParameterHandleValueMap;
		try {
			informInteractionParameterHandleValueMap = this.getRtiAmbassador().getParameterHandleValueMapFactory().create(1);
			informInteractionParameterHandleValueMap.put(this.getRtiAmbassador().getParameterHandle(this.getInteractionClass(), HlaInformInteraction.INFORM_INTERACTION_MESSAGE_PARAM_NAME), this.encodeMessage(this.getMessage()));
			// FIXME: make this work
			// this.rtiAmbassador.sendInteraction(this.getInteractionClass(),
			// informInteractionParameterHandleValueMap, null, logicalTime);
		} catch (FederateNotExecutionMember | NotConnected | NameNotFound | InvalidInteractionClassHandle | RTIinternalError e) {
			throw new RuntimeException(e);
		}
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
