package edu.wesimulated.firstapp.simulation.domain.mywork.highlyinterruptiblerole;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.wesimulated.simulation.Clock;
import com.wesimulated.simulationmotor.DateUtils;
import com.wesimulated.simulationmotor.des.Prioritized;

import edu.wesimulated.firstapp.simulation.domain.Characteristic;
import edu.wesimulated.firstapp.simulation.domain.MessageCharacteristic;
import edu.wesimulated.firstapp.simulation.stochastic.Classification;
import edu.wesimulated.firstapp.simulation.stochastic.EntryValue;
import edu.wesimulated.firstapp.simulation.stochastic.EntryValue.Type;
import edu.wesimulated.firstapp.simulation.stochastic.NumericallyModeledEntity;
import edu.wesimulated.firstapp.simulation.stochastic.ParametricAlgorithm;
import edu.wesimulated.firstapp.simulation.stochastic.StochasticVar;

public class Message implements Prioritized, NumericallyModeledEntity {

	private MessageValuator messageAgeValuator;
	private Person sender;
	private Collection<Person> recipients;
	private Date timestamp;
	private Clock clock;
	private Status status;
	protected static Status[] statusThatRequireWork = { Status.RESPOND, Status.RELEASE_PERSON, Status.NEW };

	public enum Status implements Classification, NumericallyModeledEntity {
		NEW, RESPOND, RELEASE_PERSON, PROCESSED, ISSUED, NOT_ISSUED, RESOLVE;

		@Override
		public String getName() {
			return this.toString();
		}

		@Override
		public Map<Characteristic, EntryValue> extractValues() {
			Map<Characteristic, EntryValue> values = new HashMap<>();
			values.put(MessageCharacteristic.STATUS, new EntryValue(Type.STRING, this.toString()));
			return values;
		}
	}

	public Message(Person sender, Collection<Person> recipients, Date timestamp, Clock clock, Status status) {
		super();
		this.sender = sender;
		this.recipients = recipients;
		this.timestamp = timestamp;
		this.clock = clock;
		this.status = status;
	}

	public Message(Person sender, Collection<Person> recipients, Date timestamp, Clock clock) {
		this(sender, recipients, timestamp, clock, Status.NEW);
	}

	public void applyEffectsOfResolution() {
		/**
		 * TODO tendr�a que tener efectos en el conocimiento de alguna task para la persona que pregunt�
		 * y aumentar el nivel de cercan�a entre el preguntado y el contestado.
		 */
	}

	private Status[] getStatusThatRequireWork() {
		return statusThatRequireWork;
	}

	/**
	 * Decide if after new a message becames processed, respond or
	 * release_person
	 */
	public void analize() {
		ParametricAlgorithm status = ParametricAlgorithm.buildParametricAlgorithmForVar(StochasticVar.MESSAGE_STATUS);
		status.consider(this);
		this.setStatus((Status) status.findSample().getClassifictation());
	}

	@Override
	public Map<Characteristic, EntryValue> extractValues() {
		Map<Characteristic, EntryValue> values = new HashMap<>();
		values.putAll(NumericallyModeledEntity.embedPersonCharacteristic(this.sender, "SENDER_"));
		values.put(MessageCharacteristic.AMOUNT_RECIPIENTS, new EntryValue(EntryValue.Type.LONG, this.getRecipients().size()));
		values.put(MessageCharacteristic.ANTIQUITY, new EntryValue(EntryValue.Type.STRING, this.messageAgeValuator.fromMinutes(this.getAgeInMinutes()).toString()));
		values.putAll(this.status.extractValues());
		return values;
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
		Person recipient = this.getRecipients().iterator().next();
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

	public void addRecipient(Person person) {
		this.getRecipients().add(person);
	}

	protected Person getSender() {
		return this.sender;
	}

	protected Collection<Person> getRecipients() {
		if (this.recipients == null) {
			this.recipients = new ArrayList<Person>();
		}
		return this.recipients;
	}
}
