package edu.wesimulated.firstapp.simulation.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.wesimulated.simulationmotor.des.Prioritized;

import edu.wesimulated.firstapp.simulation.HighlyInterruptibleRole;
import edu.wesimulated.firstapp.simulation.HighlyInterruptibleRoleActivity;

/**
 * 
 * This task is done by the highlyInterruptibleRole periodically and depending
 * of its priority the tasks
 * 
 * @author Carolina
 *
 */
public class CheckIM implements HighlyInterruptibleRoleActivity {

	private List<ImMessage> unreadMessages;
	private List<ImMessage> pendingImMessages;

	public CheckIM() {
		this.unreadMessages = new ArrayList<>();
		this.pendingImMessages = new ArrayList<>();
	}

	@Override
	public Date process(HighlyInterruptibleRole highlyInterruptibleRole) {
		this.getUnreadMessages().addAll(highlyInterruptibleRole.readAndCategorizeUnreadIM(this.unreadMessages));
		this.getPendingMessages().addAll(highlyInterruptibleRole.resolvePendingImMessages(this.pendingImMessages));
		return null;
	}

	@Override
	public Float MaxPriority() {
		// FIXME this shouldn't be hardcoded
		return 0.7f;
	}

	@Override
	public Float calculatePriority() {
		ImMessage mostPrioritaryMessage = getMostPrioritaryMessage();
		if (mostPrioritaryMessage != null) {
			if (Prioritized.Priority.fromValue(mostPrioritaryMessage.calculatePriority()) == Priority.HIGH) {
				return mostPrioritaryMessage.calculatePriority();
			}
			Integer amountOfUnreadMessages = this.getUnreadMessages().size();
			switch (AmountRange.fromValue(amountOfUnreadMessages)) {
			case LOW:
				return mostPrioritaryMessage.calculatePriority() * Priority.LOW.get();
			case MED:
				return mostPrioritaryMessage.calculatePriority() * Priority.MED.get();
			case HIGH:
				return mostPrioritaryMessage.calculatePriority();
			}
		}
		return 0f;
	}

	public void addUnreadMessage(ImMessage message) {
		this.getUnreadMessages().add(message);
	}

	public ImMessage getMostPrioritaryMessage() {
		if (this.getUnreadMessages().size() > 0) {
			return getSortedUnreadMessages().iterator().next();
		} else if (this.getPendingMessages().size() > 0) {
			return this.getSortedPendingMessages().iterator().next();
		}
		return null;
	}

	private List<ImMessage> getSortedPendingMessages() {
		Collections.sort(this.getPendingMessages(), (ImMessage first, ImMessage second) -> { return - first.calculatePriority().compareTo(second.calculatePriority()); });
		return this.getPendingMessages();
	}

	private List<ImMessage> getSortedUnreadMessages() {
		Collections.sort(this.getUnreadMessages(), (ImMessage first, ImMessage second) -> { return - first.calculatePriority().compareTo(second.calculatePriority()); });
		return this.getUnreadMessages();
	}

	private List<ImMessage> getPendingMessages() {
		return this.pendingImMessages;
	}

	public List<ImMessage> getUnreadMessages() {
		return this.unreadMessages;
	}

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
	}
}
