package edu.wesimulated.firstapp.simulation.hla;

import hla.rti1516e.AttributeHandle;
import hla.rti1516e.AttributeHandleValueMap;
import hla.rti1516e.ObjectClassHandle;
import hla.rti1516e.ObjectInstanceHandle;
import hla.rti1516e.RTIambassador;
import hla.rti1516e.exceptions.AttributeNotDefined;
import hla.rti1516e.exceptions.AttributeNotOwned;
import hla.rti1516e.exceptions.FederateNotExecutionMember;
import hla.rti1516e.exceptions.InvalidLogicalTime;
import hla.rti1516e.exceptions.InvalidObjectClassHandle;
import hla.rti1516e.exceptions.NameNotFound;
import hla.rti1516e.exceptions.NotConnected;
import hla.rti1516e.exceptions.ObjectInstanceNotKnown;
import hla.rti1516e.exceptions.RTIinternalError;
import hla.rti1516e.exceptions.RestoreInProgress;
import hla.rti1516e.exceptions.SaveInProgress;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;

import com.wesimulated.simulation.hla.DateLogicalTime;

public class HlaPerson extends HlaObject {
	public static final String CLASS_NAME = "Person";
	public static final String ATTRIBUTE_WORK_DONE_NAME = "WorkDone";

	private AttributeHandle workDoneAttributeInstanceHandle;
	private Float workDone;
	
	public HlaPerson(RTIambassador rtiAmbassador, ObjectClassHandle classHandle, ObjectInstanceHandle personHandle, String personName) {
		super(rtiAmbassador, classHandle, personHandle, personName);
		this.workDone = 0f;
		try {
			this.setWorkDoneAttributeHandle(this.getRtiAmbassador().getAttributeHandle(classHandle, ATTRIBUTE_WORK_DONE_NAME));
		} catch (RTIinternalError | NameNotFound | InvalidObjectClassHandle | FederateNotExecutionMember | NotConnected e) {
			throw new RuntimeException(e);
		}
	}

	public void incrementWorkDone(float workDone, DateLogicalTime time) {
		this.workDone += workDone;
		try {
			AttributeHandleValueMap attributeValues = this.getRtiAmbassador().getAttributeHandleValueMapFactory().create(1);
			attributeValues.put(this.getWorkDoneAttributeHandle(), encodeWorkDone());
			this.getRtiAmbassador().updateAttributeValues(this.getObjectInstanceHandle(), attributeValues, null, time);
		} catch (FederateNotExecutionMember | NotConnected | AttributeNotOwned | AttributeNotDefined | ObjectInstanceNotKnown | SaveInProgress | RestoreInProgress | RTIinternalError | InvalidLogicalTime e) {
			throw new RuntimeException(e);
		}
	}

	public void reflectAttributeValues(AttributeHandleValueMap attributeValues) {
		byte[] workDone = attributeValues.get(getWorkDoneAttributeHandle());
		this.setWorkDone(decodeWorkDone(workDone));
	}

	private byte[] encodeWorkDone(float workDone) {
		return ByteBuffer.allocate(8).putFloat(workDone).array();
	}

	private float decodeWorkDone(byte[] buffer) {
		return ByteBuffer.wrap(buffer).getFloat();
	}

	private byte[] encodeName(String objectInstanceName) {
		return Charset.forName("UTF-8").encode(objectInstanceName).array();
	}

	private Collection<Work> getWorkDone() {
		return workDone;
	}

	private void setWorkDone(Collection<Work> workDone) {
		this.workDone = workDone;
	}

	public AttributeHandle getWorkDoneAttributeHandle() {
		return workDoneAttributeInstanceHandle;
	}

	public void setWorkDoneAttributeHandle(AttributeHandle workDoneAttributeHandle) {
		this.workDoneAttributeInstanceHandle = workDoneAttributeHandle;
	}
}
