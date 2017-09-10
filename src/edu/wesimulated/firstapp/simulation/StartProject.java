package edu.wesimulated.firstapp.simulation;

import java.util.Date;

import com.wesimulated.simulationmotor.des.BOperation;

public class StartProject extends BOperation {

	private Date day;

	public StartProject(Date day) {
		super(null);
		this.day = day;
	}

	@Override
	public void doAction() {
	}

	@Override
	public Date getStartTime() {
		return day;
	}
}
