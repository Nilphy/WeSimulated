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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Collection;
import java.util.LinkedList;

import com.wesimulated.simulation.hla.DateLogicalTime;

import edu.wesimulated.firstapp.simulation.domain.Work;

public class HlaTask extends HlaObject {
	public static final String CLASS_NAME = "Task";
	public static final String ATTRIBUTE_WORK_TO_DO_NAME = "WorkToDo";

	public AttributeHandle workToDoAttributeInstanceHandle;
	public Collection<Work> workToDo;

	public HlaTask(RTIambassador rtiAmbassador, ObjectClassHandle classHandle, ObjectInstanceHandle personHandle, String personName) {
		super(rtiAmbassador, classHandle, personHandle, personName);
		try {
			this.setWorkToDoAttributeHandle(this.getRtiAmbassador().getAttributeHandle(classHandle, ATTRIBUTE_WORK_TO_DO_NAME));
		} catch (RTIinternalError | NameNotFound | InvalidObjectClassHandle | FederateNotExecutionMember | NotConnected e) {
			throw new RuntimeException(e);
		}
		this.workToDo = new LinkedList<Work>();
	}

	public void registerWorkToDo(Work workToDo, DateLogicalTime time) {
		this.getWorkToDo().add(workToDo);
		try {
			AttributeHandleValueMap attributeValues = this.getRtiAmbassador().getAttributeHandleValueMapFactory().create(1);
			attributeValues.put(this.getWorkToDoAttributeHandle(), encodeWorkToDo());
			this.getRtiAmbassador().updateAttributeValues(this.getObjectInstanceHandle(), attributeValues, null, time);
		} catch (FederateNotExecutionMember | NotConnected | AttributeNotOwned | AttributeNotDefined | ObjectInstanceNotKnown | SaveInProgress | RestoreInProgress | RTIinternalError | InvalidLogicalTime e) {
			throw new RuntimeException(e);
		}
	}

	public void reflectAttributeValues(AttributeHandleValueMap attributeValues) {
		byte[] work = attributeValues.get(getWorkToDoAttributeHandle());
		this.decodeWorkToDo(work);
	}

	private byte[] encodeWorkToDo() {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] encodedWorkDone;
		try (ObjectOutputStream oos = new ObjectOutputStream(baos)) {
			oos.writeObject(this.workToDo);
			encodedWorkDone = baos.toByteArray();
		} catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
		return encodedWorkDone;
	}

	@SuppressWarnings("unchecked")
	private void decodeWorkToDo(byte[] buffer) {
		ByteArrayInputStream bais = new ByteArrayInputStream(buffer);
		try (ObjectInputStream ois = new ObjectInputStream(bais)) {
			this.setWorkToDo((Collection<Work>) ois.readObject());
		} catch (IOException | ClassNotFoundException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	private Collection<Work> getWorkToDo() {
		return workToDo;
	}

	private void setWorkToDo(Collection<Work> workToDo) {
		this.workToDo = workToDo;
	}

	protected AttributeHandle getWorkToDoAttributeHandle() {
		return workToDoAttributeInstanceHandle;
	}

	private void setWorkToDoAttributeHandle(AttributeHandle workToDoAttributeHandle) {
		this.workToDoAttributeInstanceHandle = workToDoAttributeHandle;
	}
}
