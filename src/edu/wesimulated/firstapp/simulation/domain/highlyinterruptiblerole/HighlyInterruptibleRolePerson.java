package edu.wesimulated.firstapp.simulation.domain.highlyinterruptiblerole;

import java.util.Collection;
import java.util.List;

import edu.wesimulated.firstapp.simulation.domain.ImMessage;
import edu.wesimulated.firstapp.simulation.domain.Person;

public class HighlyInterruptibleRolePerson extends Person {

	public Collection<? extends ImMessage> readAndCategorizeUnreadIM(List<ImMessage> unreadMessages) {
		// TODO Auto-generated method stub
		return null;
	}

	public Collection<? extends ImMessage> resolvePendingImMessages(List<ImMessage> pendingImMessages) {
		// TODO Auto-generated method stub
		return null;
	}

	public Float getPriorityOfImFrom(HighlyInterruptibleRolePerson sender) {
		this.isWorkingWithMe(sender);
		// TODO Auto-generated method stub
		return 1f;
	}
}
