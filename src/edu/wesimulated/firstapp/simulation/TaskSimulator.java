package edu.wesimulated.firstapp.simulation;

import edu.wesimulated.firstapp.model.Task;


public class TaskSimulator {

	private Task task;
	private float workDoneInMiliseconds;
	
	public TaskSimulator(Task task) {
		this.task = task;
		this.workDoneInMiliseconds = 0; 
	}
	
	public boolean isCompleted() {
		return this.task.calculateEffortInMilliseconds() - this.workDoneInMiliseconds <= 0;
	}

	public Float consumeTime(Float availableMilisecondsPerDay) {
		if (!this.isCompleted()) {
			float remainingTime = availableMilisecondsPerDay - this.task.calculateEffortInMilliseconds() - this.workDoneInMiliseconds;
			this.workDoneInMiliseconds += availableMilisecondsPerDay; // it doesn't matter that this number could be greater than the total effort in milliseconds
			return remainingTime;
		} else {
			return availableMilisecondsPerDay;
		}
	}
}
