package edu.wesimulated.firstapp.simulation.hla;

import java.util.Observable;

import hla.rti1516e.ObjectClassHandle;
import hla.rti1516e.ObjectInstanceHandle;
import hla.rti1516e.RTIambassador;

public class HlaObject extends Observable {

	private RTIambassador rtiAmbassador;
	private ObjectClassHandle objectClassHandle;
	private ObjectInstanceHandle objectInstanceHandle;
	private String objectInstanceName;

	public HlaObject(RTIambassador rtiAmbassador, ObjectClassHandle classHandle, ObjectInstanceHandle personHandle, String personName) {
		this.setRtiAmbassador(rtiAmbassador);
		this.setObjectInstanceHandle(personHandle);
		this.setObjectInstanceName(personName);
		this.setObjectClassHandle(classHandle);
	}

	public ObjectInstanceHandle getObjectInstanceHandle() {
		return objectInstanceHandle;
	}

	protected void setObjectInstanceHandle(ObjectInstanceHandle objectInstanceHandle) {
		this.objectInstanceHandle = objectInstanceHandle;
	}

	public String getObjectInstanceName() {
		return objectInstanceName;
	}

	protected void setObjectInstanceName(String objectInstanceName) {
		this.objectInstanceName = objectInstanceName;
	}

	protected RTIambassador getRtiAmbassador() {
		return this.rtiAmbassador;
	}

	protected void setRtiAmbassador(RTIambassador rtiAmbassador) {
		this.rtiAmbassador = rtiAmbassador;
	}

	protected ObjectClassHandle getObjectClassHandle() {
		return objectClassHandle;
	}

	protected void setObjectClassHandle(ObjectClassHandle objectClassHandle) {
		this.objectClassHandle = objectClassHandle;
	}

}
