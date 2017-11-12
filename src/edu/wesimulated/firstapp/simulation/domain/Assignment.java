package edu.wesimulated.firstapp.simulation.domain;

public class Assignment {

	public enum AssignmentType {
		RESPONSIBLE, ACCOUNTABLE, CONSULTED, INFORMED;
	}

	private Person person;
	private Role role;
	private Task task;
	private AssignmentType type;

	public Assignment(Task task, Person person, Role role, AssignmentType type) {
		this.setPerson(person);
		this.setRole(role);
		this.setType(type);
		this.setTask(task);
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public AssignmentType getType() {
		return type;
	}

	public void setType(AssignmentType type) {
		this.type = type;
	}

	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}

}
