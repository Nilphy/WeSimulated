package edu.wesimulated.firstapp.simulation.domain.avature.highlyinterruptiblerole;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javafx.util.Pair;
import edu.wesimulated.firstapp.simulation.domain.Person;

public class HighlyInterruptibleRolePerson extends Person {

	private List<ImMessage> unreadMessages;

	/** 
	 * Reeding messages is considered to not take time... if a message resultion involves an investigation,
	 * being responded, or to release the person to dedicate to do a role, that will be done in the processing
	 * of the pending messages.
	 * @param unreadMessages
	 * @return
	 */
	public Collection<ImMessage> readAndAnalizeUnreadIM() {
		Collection<ImMessage> pendingMessages = new ArrayList<>();
		for (ImMessage imMessage : this.getUnreadMessages()) {
			imMessage.analize();
			if (imMessage.isPending()) {
				pendingMessages.add(imMessage);
			}
		}
		return pendingMessages;
	}

	public Pair<Date, ImMessage> resolvePendingImMessages(List<ImMessage> pendingImMessages) {
		Date dateUntilResolution = null;
		ImMessage imMessage = pendingImMessages.get(0);
		switch (imMessage.getStatus()) {
		case RELEASE_PERSON:
			this.setAvailable(true);
			dateUntilResolution = null;
			break;
		case ANALYSIS:
			dateUntilResolution = imMessage.calculateTimeToResolve();
			break;
		default:
			throw new IllegalStateException("cannot resolve another type of message");
		}
		pendingImMessages.remove(imMessage);
		return new Pair<>(dateUntilResolution, imMessage);
	}

	public Float getPriorityOfImFrom(HighlyInterruptibleRolePerson sender) {
		this.isWorkingWithMe(sender);
		// TODO Auto-generated method stub
		return 1f;

	public void addUnreadMessages(Collection<ImMessage> newUnreadMessages) {
		this.getUnreadMessages().addAll(newUnreadMessages);
	}
	public List<ImMessage> getUnreadMessages() {
		return unreadMessages;
	}

	public void setUnreadMessages(List<ImMessage> unreadMessages) {
		this.unreadMessages = unreadMessages;
	}
}
