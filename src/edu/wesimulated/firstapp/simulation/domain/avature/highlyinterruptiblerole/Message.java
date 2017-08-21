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

public class Message implements Prioritized, NumericallyModeledEntity {

	private MessageValuator messageAgeValuator;
	private HighlyInterruptibleRolePerson sender;
	private Collection<HighlyInterruptibleRolePerson> recipients;
	private Date timestamp;
	private Clock clock;
	private Status status;
	private static final Status[] statusThatRequireWork = { Status.RESPOND, Status.RELEASE_PERSON, Status.NEW };
	
	public enum Status implements Classification, NumericallyModeledEntity {
		NEW, RESPOND, RELEASE_PERSON, PROCESSED;

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

	public Message(HighlyInterruptibleRolePerson sender, Collection<HighlyInterruptibleRolePerson> recipients, Date timestamp, Clock clock, Status status) {
		super();
		this.sender = sender;
		this.recipients = recipients;
		this.timestamp = timestamp;
		this.clock = clock;
		this.status = status;
	}
	
	public Message(HighlyInterruptibleRolePerson sender, Collection<HighlyInterruptibleRolePerson> recipients, Date timestamp, Clock clock) {
		this(sender, recipients, timestamp, clock, Status.NEW);
	}

	private Status[] getStatusThatRequireWork() {
		return statusThatRequireWork;
	}

	/**
	 * Decide if after new a message becames processed, respond or release_person
	 */
	public void analize() {
		ParametricAlgorithm status = ParametricAlgorithm.buildParametricAlgorithmForVar(StochasticVar.MessageStatus);
		status.consider(this.getSender());
		status.consider(this.getStatus());
		status.considerSingleValue(new Pair<>(VariableName.AmountOfRecipients, new EntryValue(EntryValue.Type.Long, this.getRecipients().size())));
		this.setStatus((Status) status.findSample().getClassifictation());
	}

	@Override
	public Map<StochasticVariableName, EntryValue> extractValues() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Float calculatePriority() {
		return this.escalateConsideringAntiquity(this.calculateTimelessPriority());
	}

	public boolean isPending() {
		return Arrays.asList(this.getStatusThatRequireWork()).contains(this.status);
	}

	public Date getCurrentDate() {
		return this.clock.getCurrentDate();
	}

	private Float calculateTimelessPriority() {
		if (this.getRecipients().size() > 1) {
			return Prioritized.Priority.LOW.get();
		}
		// FIXME: really? to the first one?
		HighlyInterruptibleRolePerson recipient = this.getRecipients().iterator().next();
		return recipient.getPriorityMessageFrom(sender);
	}

	private Integer getAgeInMinutes() {
		return DateUtils.calculateDifferenceInMinutes(this.timestamp, this.clock.getCurrentDate());
	}

	private Float escalateConsideringAntiquity(Float priority) {
		return priority * this.messageAgeValuator.getAssociatedPriority(this.getAgeInMinutes());
	}

	public Date getTimestamp() {
		return this.timestamp;
	}

	public Status getStatus() {
		return this.status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public void addRecipient(HighlyInterruptibleRolePerson person) {
		this.getRecipients().add(person);
	}

	protected HighlyInterruptibleRolePerson getSender() {
		return this.sender;
	}

	protected Collection<HighlyInterruptibleRolePerson> getRecipients() {
		if (this.recipients == null) {
			this.recipients = new ArrayList<HighlyInterruptibleRolePerson>();
		}
		return this.recipients;
	}
}
