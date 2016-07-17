package edu.wesimulated.firstapp.simulation.domain;

import hla.rti1516e.AttributeHandleValueMap;
import hla.rti1516e.ObjectInstanceHandle;
import edu.wesimulated.firstapp.simulation.hla.HlaPerson;
import edu.wesimulated.firstapp.simulation.stochastic.NumericallyModeledEntity;

public class Role implements NumericallyModeledEntity {

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
	
	public void setHlaPerson(HlaPerson hlaPerson) {
		this.hlaPerson = hlaPerson;
	}

	private HlaPerson getHlaPerson() {
		return hlaPerson;
	}
 }
