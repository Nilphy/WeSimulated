package edu.wesimulated.firstapp.simulation.domain.mywork.project;

import java.util.Date;

public class TaskExtension extends Risk {

	private Task task;

	public TaskExtension(float probability, Date periodStart, Date periodEnd, Task task) {
		super(probability, periodStart, periodEnd);
		this.setTask(task);
	}

	@Override
	public void doAction() {
		double scale = Math.random();
		task.extendDuration(scale);
	}

	public void setTask(Task task) {
		this.task = task;
	}
}
