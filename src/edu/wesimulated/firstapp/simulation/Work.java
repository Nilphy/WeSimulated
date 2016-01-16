package edu.wesimulated.firstapp.simulation;

import java.util.Date;

import com.wesimulated.simulationmotor.operationbased.BOperation;

public class Work implements BOperation {

	private PersonSimulator personSimulator;
	private Date startTime;

	public Work(PersonSimulator personSimulator, Date startTime) {
		this.personSimulator = personSimulator;
		this.startTime = startTime;
	}

	@Override
	public void doAction() {
		this.personSimulator.doOneDayOfWork();
		this.personSimulator.addBOperation(new Work(this.personSimulator, this.startTime));
	}

	@Override
	public Date getStartTime() {
		return this.startTime;
	}
}
