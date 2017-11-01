package edu.wesimulated.firstapp.simulation.domain.mywork.project;

import java.util.Date;

public class TaskExtension extends Risk {

	private AvatureProjectTask task;

	public TaskExtension(float probability, Date periodStart, Date periodEnd, AvatureProjectTask task) {
		super(probability, periodStart, periodEnd);
		this.setTask(task);
	}

	@Override
	public void doAction() {
		double scale = Math.random();
		task.extendDuration(scale);
	}

	public void setTask(AvatureProjectTask task) {
		this.task = task;
	}
}
