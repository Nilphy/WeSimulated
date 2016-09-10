package edu.wesimulated.firstapp.simulation.domain;

import hla.rti1516e.AttributeHandleValueMap;
import hla.rti1516e.ObjectInstanceHandle;

import com.wesimulated.simulationmotor.des.Resource;
import com.wesimulated.simulationmotor.des.TaskWithPriority;

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
	public boolean isAvailable(TaskWithPriority taskWithPriority) {
		registerRequestOfAvailability(taskWithPriority);
		return available;
	}

	public void setHlaPerson(HlaPerson hlaPerson) {
		this.hlaPerson = hlaPerson;
	}

	public void increaseExperienceWithWorkbenchTools(Task task, long timeExpended) {
		// TODO Auto-generated method stub
	}

	private void registerRequestOfAvailability(TaskWithPriority taskWithPriority) {
		// TODO Auto-generated method stub
	}

	private HlaPerson getHlaPerson() {
		return hlaPerson;
	}

	public void increaseExperience() {
		// TODO Auto-generated method stub
		
	}

	public Double getEfficiency() {
		// TODO Auto-generated method stub
		return null;
	}

	public Double getQualityOfWork() {
		// TODO Auto-generated method stub
		return null;
	}

	public Double getTimeReviewPerDetection() {
		// TODO Auto-generated method stub the task should be involved too
		return null;
	}

	public Double getTimeQcPerDetection() {
		// TODO Auto-generated method stub
		return null;
	}
}
