package edu.wesimulated.firstapp.simulation;

import java.util.Date;

import com.wesimulated.simulationmotor.DateUtils;
import com.wesimulated.simulationmotor.des.BOperation;

public class Work implements BOperation {

	private PersonRolSimulator personRolSimulator;
	private Date startTime;

	public Work(PersonRolSimulator personRolSimulator, Date startTime) {
		this.personRolSimulator = personRolSimulator;
		this.startTime = startTime;
	}

	@Override
	public void doAction() {
		this.personRolSimulator.doOneDayOfWork();
		this.personRolSimulator.addBOperation(new Work(this.personRolSimulator, DateUtils.convertToStartOfNextLabDay(this.startTime)));
	}

	@Override
	public Date getStartTime() {
		return this.startTime;
	}
}
