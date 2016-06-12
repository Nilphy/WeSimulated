package edu.wesimulated.firstapp.simulation.domain;

import java.io.Serializable;

public class Work implements Serializable {
	private static final long serialVersionUID = 2655337231966603356L;

	private WorkInProcessState workInProcessState;
	private Float timeSpent;
	private Float uowDone;

	public WorkInProcessState getWorkInProcessState() {
		return workInProcessState;
	}

	public void setWorkInProcessState(WorkInProcessState workInProcessState) {
		this.workInProcessState = workInProcessState;
	}

	public Float getTimeSpent() {
		return timeSpent;
	}

	public void setTimeSpent(Float timeSpent) {
		this.timeSpent = timeSpent;
	}

	public Float getUowDone() {
		return uowDone;
	}

	public void setUowDone(Float uowDone) {
		this.uowDone = uowDone;
	}
}
