package edu.wesimulated.firstapp.simulation.domain.avature.highlyinterruptiblerole;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import javafx.util.Pair;

import com.wesimulated.simulationmotor.des.Prioritized;
import com.wesimulated.simulationmotor.des.processbased.Entity;

import edu.wesimulated.firstapp.simulation.domain.avature.highlyinterruptiblerole.Message.Status;

public abstract class ComunicativeEntity extends Entity implements HighlyInterruptibleRolePrioritized {

	private HighlyInterruptibleRolePerson person;
	private Message messageInProcess;
	private MessageValuator messageAgeValuator;
	private MessageValuator messageAmountValuator;

	public ComunicativeEntity(HighlyInterruptibleRolePerson person) {
		this.person = person;
	}

	protected abstract void finishProcessingOfPreviousMessage();

	protected abstract MessageValuator createMessageAgeValuator();

	protected abstract MessageValuator createMessageAmountValuator();

	protected abstract Pair<Date, Message> resolvePendingMessages();

	@Override
	protected Date doProcess() {
		this.finishProcessingOfPreviousMessage();
		Pair<Date, Message> toProcess = this.resolvePendingMessages();
		this.messageInProcess = toProcess.getValue();
		return toProcess.getKey();
	}

	@Override
	public void acceptInterruption(Date interruptionDate) {
		this.messageInProcess.setStatus(Status.NEW);
	}

	@Override
	public Float calculatePriority() {
		ImMessage mostPrioritaryMessage = getMostPrioritaryMessage();
		if (mostPrioritaryMessage != null) {
			if (Prioritized.Priority.fromValue(mostPrioritaryMessage.calculatePriority()) == Priority.HIGH) {
				return mostPrioritaryMessage.calculatePriority();
			}
			Integer amountOfUnreadMessages = this.getPerson().getPendingImMessages().size();
			return mostPrioritaryMessage.calculatePriority() * getMessageAmountValuatorInstance().getAssociatedPriority(amountOfUnreadMessages);
		}
		return 0f;
	}

	public ImMessage getMostPrioritaryMessage() {
		if (this.getPerson().getPendingImMessages().size() > 0) {
			return this.getSortedPendingMessages().iterator().next();
		}
		return null;
	}

	private List<ImMessage> getSortedPendingMessages() {
		Collections.sort(this.person.getPendingImMessages(), (ImMessage first, ImMessage second) -> {
			return -first.calculatePriority().compareTo(second.calculatePriority());
		});
		return this.person.getPendingImMessages();
	}

	public HighlyInterruptibleRolePerson getPerson() {
		return person;
	}

	protected Message getMessageInProcess() {
		return this.messageInProcess;
	}

	protected void setMessageInProcess(Message message) {
		this.messageInProcess = message;
	}

	public MessageValuator getMessageAgeValuatorInstance() {
		if (messageAgeValuator == null) {
			messageAgeValuator = this.createMessageAgeValuator();
		}
		return messageAgeValuator;
	}

	public MessageValuator getMessageAmountValuatorInstance() {
		if (messageAmountValuator == null) {
			messageAmountValuator = this.createMessageAmountValuator();
		}
		return messageAmountValuator;
	}
}
