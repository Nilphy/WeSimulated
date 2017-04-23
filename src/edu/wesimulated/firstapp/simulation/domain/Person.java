package edu.wesimulated.firstapp.simulation.domain;

import hla.rti1516e.AttributeHandleValueMap;
import hla.rti1516e.ObjectInstanceHandle;

import java.util.Date;
import java.util.Map;

import com.wesimulated.simulationmotor.des.Prioritized;
import com.wesimulated.simulationmotor.des.Resource;

import edu.wesimulated.firstapp.simulation.hla.HlaPerson;
import edu.wesimulated.firstapp.simulation.stochastic.EntryValue;
import edu.wesimulated.firstapp.simulation.stochastic.NumericallyModeledEntity;
import edu.wesimulated.firstapp.simulation.stochastic.StochasticVariableName;

public abstract class Person implements Resource, NumericallyModeledEntity {

	private boolean available;
	private HlaPerson hlaPerson;
	private Task currentTask;
	private Profile profile;
	private Project project;

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

	public void setHlaPerson(HlaPerson hlaPerson) {
		this.hlaPerson = hlaPerson;
	}

	public void increaseExperienceWithWorkbenchTools(Task task, double timeExpended) {
		this.profile.increase(PersonCharacteristic.ExperienceWithWorkbenchTools, timeExpended);
	}


	private HlaPerson getHlaPerson() {
		return hlaPerson;
	}

	public void increaseExperience() {
		// TODO Auto-generated method stub
	}

	public Double getQualityOfWork() {
		// TODO Auto-generated method stub
		return null;
	}

	public Date findNextAvailableDate() {
		// TODO find hour to start working today
		// TODO find current hour
		// TODO if not reached the end of the day return now
		// TODO if reached return first laboral day tomorrow
		return null;
	}

	public void setCurrentTask(Task task) {
		this.currentTask = task;
	}

	@Override
	public Map<StochasticVariableName, EntryValue> extractValues() {
		// TODO Auto-generated method stub
		return null;
	}

	protected void isWorkingWithMe(Person sender) {
		// TODO Auto-generated method stub

	}

	public void resign() {
		this.getProject().getPeople().remove(this);
	}

	private Project getProject() {
		return this.project;
	}

	public Profile getProfile() {
		return this.profile;
	}
}
