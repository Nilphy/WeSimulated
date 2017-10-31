package edu.wesimulated.firstapp.simulation.domain.avature.role;

import edu.wesimulated.firstapp.simulation.domain.Person;
import edu.wesimulated.firstapp.simulation.domain.Role;
import edu.wesimulated.firstapp.simulation.domain.Task;
import edu.wesimulated.firstapp.simulation.domain.worktype.WorkType;

public class WorkDone {

	private long timeAmountInMillies;
	private Role role;
	private Person person;
	private WorkType workType;

	public WorkDone(long timeAmountInMillies, Role role, Person person, WorkType workType) {
		this.timeAmountInMillies = timeAmountInMillies;
		this.role = role;
		this.person = person;
		this.workType = workType;
	}

	public long getAmount() {
		return timeAmountInMillies;
	}

	public Role getRole() {
		return role;
	}

	public Person getPerson() {
		return person;
	}

	public WorkType getWorkType() {
		return workType;
	}
}
