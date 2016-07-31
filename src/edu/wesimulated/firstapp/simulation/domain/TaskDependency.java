package edu.wesimulated.firstapp.simulation.domain;


public class TaskDependency {

	private Task task;
	private PrecedenceType precedence;

	public TaskDependency() {
	}

	public TaskDependency(Task task, PrecedenceType precedenceType) {
		this.task = task;
		this.precedence = precedenceType;
	}

	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}

	public PrecedenceType getPrecedence() {
		return precedence;
	}

	public void setPrecedence(PrecedenceType precedence) {
		this.precedence = precedence;
	}
}
