package edu.wesimulated.firstapp.simulation.domain;

import hla.rti1516e.AttributeHandleValueMap;
import hla.rti1516e.ObjectInstanceHandle;

import com.wesimulated.simulationmotor.des.Resource;

import edu.wesimulated.firstapp.simulation.hla.HlaPerson;
import edu.wesimulated.firstapp.simulation.stochastic.NumericallyModeledEntity;

public class Person implements Resource, NumericallyModeledEntity {

	private boolean available;
	private HlaPerson hlaPerson;

	public String getLastWorkDone() {
		return this.getHlaPerson().getLastWorkDone();
	}

	public ObjectInstanceHandle getHlaObjectInstanceHandle() {
		return this.getHlaPerson().getObjectInstanceHandle();
	}

	public String getName() {
		return this.getHlaPerson().getObjectInstanceName();
	}

	public void reflectAttributeValues(AttributeHandleValueMap attributeValues) {
		this.hlaPerson.reflectAttributeValues(attributeValues);
	}
	
	@Override
	public void setAvailable(boolean available) {
		this.available = available;
	}

	@Override
	public boolean isAvailable() {
		return available;
	}

	private HlaPerson getHlaPerson() {
		return hlaPerson;
	}

	public void setHlaPerson(HlaPerson hlaPerson) {
		this.hlaPerson = hlaPerson;
	}
 }
