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

	protected abstract List<Message> getPendingMessagesFromPerson();

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
		Message mostPrioritaryMessage = getMostPrioritaryMessage();
		if (mostPrioritaryMessage != null) {
			Float highestPriority = mostPrioritaryMessage.calculatePriority();
			if (Prioritized.Priority.fromValue(highestPriority) == Priority.HIGH) {
				return highestPriority;
			}
			Integer amountOfUnreadMessages = getPendingMessagesFromPerson().size();
			return highestPriority * getMessageAmountValuatorInstance().getAssociatedPriority(amountOfUnreadMessages);
		}
		return 0f;
	}

	public Message getMostPrioritaryMessage() {
		if (getPendingMessagesFromPerson().size() > 0) {
			return this.getSortedPendingMessages().iterator().next();
		}
		return null;
	}

	private List<Message> getSortedPendingMessages() {
		Collections.sort(this.person.getPendingImMessages(), (Message first, Message second) -> {
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
