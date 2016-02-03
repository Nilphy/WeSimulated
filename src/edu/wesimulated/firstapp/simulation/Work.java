package edu.wesimulated.firstapp.simulation;

import java.util.Date;

import com.wesimulated.simulationmotor.DateUtils;
import com.wesimulated.simulationmotor.des.BOperation;

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
		this.personSimulator.addBOperation(new Work(this.personSimulator, DateUtils.convertToStartOfNextLabDay(this.startTime)));
	}

	@Override
	public Date getStartTime() {
		return this.startTime;
	}
}
