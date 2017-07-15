package edu.wesimulated.firstapp.simulation.domain.avature.highlyinterruptiblerole;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import edu.wesimulated.firstapp.simulation.domain.Person;

public class HighlyInterruptibleRolePerson extends Person {

	/** 
	 * Reeding messages is considered to not take time... if a message resultion involves an investigation,
	 * being responded, or to release the person to dedicate to do a role, that will be done in the processing
	 * of the pending messages.
	 * @param unreadMessages
	 * @return
	 */
	public Collection<ImMessage> readAndAnalizeUnreadIM(List<ImMessage> unreadMessages) {
		Collection<ImMessage> pendingMessages = new ArrayList<>();
		for (ImMessage imMessage : unreadMessages) {
			imMessage.analize();
			if (imMessage.isPending()) {
				pendingMessages.add(imMessage);
			}
		}
		return pendingMessages;
	}

	public Date resolvePendingImMessages(List<ImMessage> pendingImMessages) {
		Date dateUntilResolution = null;
		for (ImMessage imMessage : pendingImMessages) {
			switch (imMessage.getState()) {
			case RELEASE_PERSON:
				this.setAvailable(true);
				break;
			case ANALYSIS:
				dateUntilResolution = imMessage.calculateTimeToResolve();
			default:
				break;
			}
		}
		return dateUntilResolution;
	}

	public Float getPriorityOfImFrom(HighlyInterruptibleRolePerson sender) {
		this.isWorkingWithMe(sender);
		// TODO Auto-generated method stub
		return 1f;
	}
}
