package edu.wesimulated.firstapp.simulation.domain;

import java.io.Serializable;

public class Work implements Serializable {
	private static final long serialVersionUID = 2655337231966603356L;

	private Float timeSpent;
	private Float uowDone;

	public Work(long duration) {
		this.timeSpent = new Float(duration);
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
