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
import java.nio.charset.Charset;
import java.util.Collection;

import com.wesimulated.simulation.hla.DateLogicalTime;

import edu.wesimulated.firstapp.simulation.domain.Work;

public class HlaPerson extends HlaObject {
	private AttributeHandle workDoneAttributeInstanceHandle;
	private AttributeHandle isAvailableAttributeInstanceHandle;
	private Collection<Work> workDone;
	private boolean isAvailable;

	public HlaPerson(RTIambassador rtiAmbassador, ObjectClassHandle classHandle, ObjectInstanceHandle personHandle, String personName) {
		super(rtiAmbassador, classHandle, personHandle, personName);
		try {
			this.setWorkDoneAttributeHandle(this.getRtiAmbassador().getAttributeHandle(classHandle, HlaAttribute.getHlaWorkDoneAttributeInstance().getName()));
		} catch (RTIinternalError | NameNotFound | InvalidObjectClassHandle | FederateNotExecutionMember | NotConnected e) {
			throw new RuntimeException(e);
		}
	}

	public void incrementWorkDone(Work workDone, DateLogicalTime time) {
		this.workDone.add(workDone);
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
		decodeWorkDone(workDone);
		this.setChanged();
		this.notifyObservers();
	}

	private byte[] encodeWorkDone() {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] encodedWorkDone;
		try (ObjectOutputStream oos = new ObjectOutputStream(baos)) {
			oos.writeObject(this.workDone);
			encodedWorkDone = baos.toByteArray();
		} catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
		return encodedWorkDone;
	}

	@SuppressWarnings("unchecked")
	private void decodeWorkDone(byte[] buffer) {
		ByteArrayInputStream bais = new ByteArrayInputStream(buffer);
		try (ObjectInputStream ois = new ObjectInputStream(bais)) {
			this.setWorkDone((Collection<Work>) ois.readObject());
		} catch (IOException | ClassNotFoundException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	@SuppressWarnings("unused")
	// TODO make that the hla person has the name of the corresponding person
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
