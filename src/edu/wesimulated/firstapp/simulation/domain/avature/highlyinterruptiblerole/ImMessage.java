package edu.wesimulated.firstapp.simulation.domain.avature.highlyinterruptiblerole;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javafx.util.Pair;

import com.wesimulated.simulation.Clock;
import com.wesimulated.simulationmotor.DateUtils;
import com.wesimulated.simulationmotor.des.Prioritized;

import edu.wesimulated.firstapp.simulation.stochastic.Classification;
import edu.wesimulated.firstapp.simulation.stochastic.EntryValue;
import edu.wesimulated.firstapp.simulation.stochastic.EntryValue.Type;
import edu.wesimulated.firstapp.simulation.stochastic.NumericallyModeledEntity;
import edu.wesimulated.firstapp.simulation.stochastic.ParametricAlgorithm;
import edu.wesimulated.firstapp.simulation.stochastic.StochasticVar;
import edu.wesimulated.firstapp.simulation.stochastic.StochasticVariableName;
import edu.wesimulated.firstapp.simulation.stochastic.VariableName;

public class ImMessage implements Prioritized {
	private final static Status[] statesThatRequireWork = { Status.ANALYSIS, Status.RELEASE_PERSON, Status.UNREAD };

	private HighlyInterruptibleRolePerson sender;
	private Collection<HighlyInterruptibleRolePerson> recipients;
	private Date timestamp;
	private Clock clock;
	private Status status;

	public ImMessage(HighlyInterruptibleRolePerson sender, Collection<HighlyInterruptibleRolePerson> recipients, Date timestamp, Clock clock) {
		this.sender = sender;
		this.recipients = recipients;
		this.timestamp = timestamp;
		this.clock = clock;
		this.setStatus(Status.UNREAD);
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

	public void analize() {
		ParametricAlgorithm status = ParametricAlgorithm.buildParametricAlgorithmForVar(StochasticVar.ImMessageStatus);
		status.consider(sender);
		status.consider(this.status);
		status.considerSingleValue(new Pair<>(VariableName.AmountOfRecipients, new EntryValue(EntryValue.Type.Long, this.recipients.size())));
		this.status = (Status) status.findSample().getClassifictation();
	}

	public boolean isPending() {
		return Arrays.asList(statesThatRequireWork).contains(this.status);
	}

	public Date calculateTimeToResolve() {
		ParametricAlgorithm timeToResolveIm = ParametricAlgorithm.buildParametricAlgorithmForVar(StochasticVar.TimeToResolveIm);
		return DateUtils.addMilis(this.clock.getCurrentDate(), timeToResolveIm.findSample().getPrediction().getValue().floatValue());
	}

	private Float calculateTimelessPriority() {
		if (this.getRecipients().size() > 1) {
			return Prioritized.Priority.LOW.get();
		}
		// FIXME: really? to the first one?
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

	public Status getState() {
		return this.status;
	}

	private void setStatus(Status status) {
		this.status = status;
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

	public enum Status implements Classification, NumericallyModeledEntity {
		UNREAD, ANALYSIS, RELEASE_PERSON, RESPONDED;

		@Override
		public String getName() {
			return this.toString();
		}

		@Override
		public Map<StochasticVariableName, EntryValue> extractValues() {
			Map<StochasticVariableName, EntryValue> values = new HashMap<>();
			values.put(VariableName.ImMessageStatus, new EntryValue(Type.String, this.toString()));
			return values;
		}
	}

}
