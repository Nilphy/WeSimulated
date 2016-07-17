package edu.wesimulated.firstapp.simulation;

import java.util.Date;

import com.wesimulated.simulationmotor.des.BOperation;

public class StartProject implements BOperation {

	private Date day;

	public StartProject(Date day) {
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
