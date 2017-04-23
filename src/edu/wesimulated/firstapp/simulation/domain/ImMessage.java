package edu.wesimulated.firstapp.simulation.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import com.wesimulated.simulation.Clock;
import com.wesimulated.simulationmotor.DateUtils;
import com.wesimulated.simulationmotor.des.Prioritized;

public class ImMessage implements Prioritized {

	private HighlyInterruptibleRolePerson sender;
	private Collection<HighlyInterruptibleRolePerson> recipients;
	private Date timestamp;
	private Clock clock;

	public ImMessage(HighlyInterruptibleRolePerson sender, Collection<HighlyInterruptibleRolePerson> recipients, Date timestamp, Clock clock) {
		this.sender = sender;
		this.recipients = recipients;
		this.timestamp = timestamp;
		this.clock = clock;
	}

	public ImMessage(HighlyInterruptibleRolePerson sender, Date timestamp, Clock clock) {
		this(sender, null, timestamp, clock);
	}

	@Override
	public Float calculatePriority() {
		return this.escalateConsideringAntiquity(this.calculateTimelessPriority());
	}

	public void addRecipient(HighlyInterruptibleRolePerson person) {
		this.getRecipients().add(person);
	}

	private Float calculateTimelessPriority() {
		if (this.getRecipients().size() > 1) {
			return Prioritized.Priority.LOW.get();
		}
		HighlyInterruptibleRolePerson recipient = this.getRecipients().iterator().next();
		return recipient.getPriorityOfImFrom(sender);
	}

	private Float escalateConsideringAntiquity(Float priority) {
		return priority * Antiquity.fromValue(this.getAgeInMinutes()).getAssociatedPriority();

	}

	private Integer getAgeInMinutes() {
		return DateUtils.calculateDifferenceInMinutes(this.timestamp, this.clock.getCurrentDate());
	}

	private Collection<HighlyInterruptibleRolePerson> getRecipients() {
		if (this.recipients == null) {
			this.recipients = new ArrayList<HighlyInterruptibleRolePerson>();
		}
		return this.recipients;
	}

	/**
	 * Constant used to define the antiquity of an ImMessage, it has associated
	 * the minutes of age related with the antiquity
	 * 
	 * @author Carolina
	 */
	public enum Antiquity {
		LOW(5), MED(30), HIGH(60);

		private Integer minutes;

		private Antiquity(Integer minutes) {
			this.minutes = minutes;
		}

		public Integer get() {
			return this.minutes;
		}

		static public Antiquity fromValue(Integer minutes) {
			if (minutes < LOW.get()) {
				return LOW;
			}
			if (minutes < MED.get()) {
				return MED;
			}
			return HIGH;
		}

		public Float getAssociatedPriority() {
			switch (this) {
			case LOW:
				return Priority.LOW.get();
			case MED:
				return Priority.MED.get();
			case HIGH:
				return Priority.HIGH.get();
			default:
				throw new IllegalStateException("The priority given has not been considered in getAssociatedPriority method");
			}
		}
	}
}
