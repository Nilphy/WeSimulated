package edu.wesimulated.firstapp.simulation.domain.avature.highlyinterruptiblerole;

import java.util.Collection;
import java.util.Date;

import com.wesimulated.simulation.Clock;

public class ImMessage extends Message {
	// Analysis is not considered here because I'll consider that ImMessages are
	// more simple
	private final static Status[] statusThatRequireWork = { Status.RESPOND, Status.RELEASE_PERSON, Status.NEW };

	public ImMessage(HighlyInterruptibleRolePerson sender, Collection<HighlyInterruptibleRolePerson> recipients, Date timestamp, Clock clock) {
		super(sender, recipients, timestamp, clock, Status.NEW);
	}

	@Override
	protected Status[] getStatusThatRequireWork() {
		return statusThatRequireWork;
	}

	protected boolean needsReesponse() {
		// TODO Auto-generated method stub
		return false;
	}
}
