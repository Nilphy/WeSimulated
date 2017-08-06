package edu.wesimulated.firstapp.simulation.domain.avature.highlyinterruptiblerole;

import java.util.Collection;
import java.util.Date;

import com.wesimulated.simulation.Clock;

public class Email extends Message {
	private final static Status[] statusThatRequireWork = { Status.RESPOND, Status.RELEASE_PERSON, Status.NEW };

	public Email(HighlyInterruptibleRolePerson sender, Collection<HighlyInterruptibleRolePerson> recipients, Date timestamp, Clock clock, Status status) {
		super(sender, recipients, timestamp, clock, status);
	}

	@Override
	protected Status[] getStatusThatRequireWork() {
		return statusThatRequireWork;
	}
}
