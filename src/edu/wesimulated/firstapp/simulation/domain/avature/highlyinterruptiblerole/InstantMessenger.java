package edu.wesimulated.firstapp.simulation.domain.avature.highlyinterruptiblerole;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javafx.util.Pair;

import com.wesimulated.simulationmotor.des.Prioritized;
import com.wesimulated.simulationmotor.des.processbased.Entity;

import edu.wesimulated.firstapp.simulation.domain.PersonCharacteristic;

/**
 * 
 * This task is done by the highlyInterruptibleRole periodically and depending
 * of its priority the tasks
 * 
 * @author Carolina
 *
 */
public class InstantMessenger extends Entity implements HighlyInterruptibleRolePrioritized {

	private List<ImMessage> pendingImMessages;
	private HighlyInterruptibleRolePerson person;
	private ImMessage messageInProccess;

	/**
	 * This enum is used to have ranges of amounts, because the priority to do
	 * this task will depend on the amount of pending messages
	 * 
	 * @author Carolina
	 *
	 */
	public enum AmountRange {
		LOW(3), MED(10), HIGH(20);

		private Integer amount;

		private AmountRange(Integer amount) {
			this.amount = amount;
		}

		public Integer get() {
			return this.amount;
		}

		static public AmountRange fromValue(Integer amount) {
			if (amount < LOW.get()) {
				return LOW;
			}
			if (amount < MED.get()) {
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
				return 1f;
			default:
				throw new IllegalStateException("The AmountRange is not considered into the getAssociatedPriority method");
			}
		}
	}

	public InstantMessenger(HighlyInterruptibleRolePerson person) {
		this.pendingImMessages = new ArrayList<>();
		this.person = person;
	}

	@Override
	protected Date doProcess() {
		this.finishProcessingOfPreviousMessage();
		this.getPendingMessages().addAll(this.getPerson().readAndAnalizeUnreadIM());
		Pair<Date, ImMessage> toProcess = this.getPerson().resolvePendingImMessages(this.getPendingMessages());
		this.messageInProccess = toProcess.getValue();
		return toProcess.getKey();
	}

	private void finishProcessingOfPreviousMessage() {
		this.messageInProccess.setStatus(ImMessage.Status.RESPONDED);
		this.messageInProccess = null;
	}

	@Override
	public Float MaxPriority() {
		return person.getProfile().get(PersonCharacteristic.MaxPriorityOfIM).getNumber().floatValue();
	}

	@Override
	public Float calculatePriority() {
		ImMessage mostPrioritaryMessage = getMostPrioritaryMessage();
		if (mostPrioritaryMessage != null) {
			if (Prioritized.Priority.fromValue(mostPrioritaryMessage.calculatePriority()) == Priority.HIGH) {
				return mostPrioritaryMessage.calculatePriority();
			}
			Integer amountOfUnreadMessages = this.getPerson().getUnreadMessages().size();
			return mostPrioritaryMessage.calculatePriority() * AmountRange.fromValue(amountOfUnreadMessages).getAssociatedPriority();
		}
		return 0f;
	}

	@Override
	public void acceptInterruption(Date interruptionDate) {
		this.pendingImMessages.add(this.messageInProccess);
	}

	public ImMessage getMostPrioritaryMessage() {
		if (this.getPerson().getUnreadMessages().size() > 0) {
			return getSortedUnreadMessages().iterator().next();
		} else if (this.getPendingMessages().size() > 0) {
			return this.getSortedPendingMessages().iterator().next();
		}
		return null;
	}

	private List<ImMessage> getSortedPendingMessages() {
		Collections.sort(this.getPendingMessages(), (ImMessage first, ImMessage second) -> {
			return -first.calculatePriority().compareTo(second.calculatePriority());
		});
		return this.getPendingMessages();
	}

	private List<ImMessage> getSortedUnreadMessages() {
		Collections.sort(this.getPerson().getUnreadMessages(), (ImMessage first, ImMessage second) -> {
			return -first.calculatePriority().compareTo(second.calculatePriority());
		});
		return this.getPerson().getUnreadMessages();
	}

	private List<ImMessage> getPendingMessages() {
		return this.pendingImMessages;
	}

	private HighlyInterruptibleRolePerson getPerson() {
		return this.person;
	}
}
