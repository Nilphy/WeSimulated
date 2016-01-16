package edu.wesimulated.firstapp.simulation;

import hla.rti1516e.AttributeHandle;
import hla.rti1516e.AttributeHandleValueMap;
import hla.rti1516e.ObjectClassHandle;
import hla.rti1516e.ObjectInstanceHandle;
import hla.rti1516e.RTIambassador;
import hla.rti1516e.RtiFactoryFactory;
import hla.rti1516e.exceptions.AttributeNotDefined;
import hla.rti1516e.exceptions.AttributeNotOwned;
import hla.rti1516e.exceptions.FederateNotExecutionMember;
import hla.rti1516e.exceptions.InvalidObjectClassHandle;
import hla.rti1516e.exceptions.NameNotFound;
import hla.rti1516e.exceptions.NotConnected;
import hla.rti1516e.exceptions.ObjectInstanceNotKnown;
import hla.rti1516e.exceptions.RTIinternalError;
import hla.rti1516e.exceptions.RestoreInProgress;
import hla.rti1516e.exceptions.SaveInProgress;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;

public class HLAPerson {
	public static final String CLASS_NAME = "Person";
	public static final String ATTRIBUTE_WORK_DONE_NAME = "WorkDone";

	private RTIambassador rtiAmbassador;
	private ObjectInstanceHandle objectInstanceHandle;
	private String objectInstanceName;
	private AttributeHandle workDoneAttributeInstanceHandle;
	private Float workDone;
	
	public HLAPerson(ObjectClassHandle classHandle, ObjectInstanceHandle personHandle, String personName) 
			throws RTIinternalError, NameNotFound, InvalidObjectClassHandle, FederateNotExecutionMember, NotConnected {
		this.rtiAmbassador = RtiFactoryFactory.getRtiFactory().getRtiAmbassador();
		this.objectInstanceHandle = personHandle;
		this.objectInstanceName = personName;
		this.workDoneAttributeInstanceHandle = this.getRtiAmbassador().getAttributeHandle(classHandle, ATTRIBUTE_WORK_DONE_NAME);
	}

	public void incrementWorkDone(float workDone) 
			throws FederateNotExecutionMember, NotConnected, AttributeNotOwned, AttributeNotDefined, ObjectInstanceNotKnown, SaveInProgress, RestoreInProgress, RTIinternalError {
		this.workDone += this.workDone;
		AttributeHandleValueMap attributeValues = this.rtiAmbassador.getAttributeHandleValueMapFactory().create(1);
		attributeValues.put(workDoneAttributeInstanceHandle, encodeWorkDone(this.workDone));
		this.rtiAmbassador.updateAttributeValues(objectInstanceHandle, attributeValues, null);
	}

	public void reflectAttributeValues(AttributeHandleValueMap attributeValues) {
		byte[] workDone = attributeValues.get(getWorkDoneAttributeHandle());
		this.setWorkDone(decodeWorkDone(workDone));
	}

	public byte[] encodeWorkDone(float workDone) {
		return ByteBuffer.allocate(8).putFloat(workDone).array();
	}

	public float decodeWorkDone(byte[] buffer) {
		return ByteBuffer.wrap(buffer).getFloat();
	}

	protected byte[] encodeName(String objectInstanceName) {
		return Charset.forName("UTF-8").encode(objectInstanceName).array();
	}

	public ObjectInstanceHandle getObjectInstanceHandle() {
		return objectInstanceHandle;
	}

	public void setObjectInstanceHandle(ObjectInstanceHandle objectInstanceHandle) {
		this.objectInstanceHandle = objectInstanceHandle;
	}

	public String getObjectInstanceName() {
		return objectInstanceName;
	}

	public void setObjectInstanceName(String objectInstanceName) {
		this.objectInstanceName = objectInstanceName;
	}

	public float getWorkDone() {
		return workDone;
	}

	public void setWorkDone(float workDone) {
		this.workDone = workDone;
	}

	public AttributeHandle getWorkDoneAttributeHandle() {
		return workDoneAttributeInstanceHandle;
	}

	public void setWorkDoneAttributeHandle(AttributeHandle workDoneAttributeHandle) {
		this.workDoneAttributeInstanceHandle = workDoneAttributeHandle;
	}
	
	public RTIambassador getRtiAmbassador() {
		return this.rtiAmbassador;
	}

	public void setRtiAmbassador(RTIambassador rtiAmbassador) {
		this.rtiAmbassador = rtiAmbassador;
	}
}
