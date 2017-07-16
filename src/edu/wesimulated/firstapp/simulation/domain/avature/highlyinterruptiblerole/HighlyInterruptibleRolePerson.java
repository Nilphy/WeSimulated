package edu.wesimulated.firstapp.simulation.domain.avature.highlyinterruptiblerole;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javafx.util.Pair;

import com.wesimulated.simulationmotor.des.Prioritized.Priority;

import edu.wesimulated.firstapp.simulation.domain.Person;
import edu.wesimulated.firstapp.simulation.domain.Team;

public class HighlyInterruptibleRolePerson extends Person {

	private List<ImMessage> unreadImMessages;

	/**
	 * Reeding messages is considered to not take time... if a message
	 * resolution involves an investigation, being responded, or to release the
	 * person to dedicate to do a role, that will be done in the processing of
	 * the pending messages.
	 * 
	 * @param unreadImMessages
	 * @return
	 */
	public Collection<ImMessage> readAndAnalizeUnreadIM() {
		Collection<ImMessage> pendingMessages = new ArrayList<>();
		for (ImMessage imMessage : this.getUnreadImMessages()) {
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
		if (this.isWorkingWithMe(sender)) {
			return Priority.HIGH.get();
		} else if (this.isInOneOfMyTeams(sender)) {
			return Priority.MED.get();
		} else if (this.isASuperior(sender)) {
			return Priority.HIGH.get();
		} else {
			return Priority.LOW.get();
		}
	}

	private boolean isASuperior(HighlyInterruptibleRolePerson sender) {
		// TODO Auto-generated method stub
		return false;
	}

	private boolean isInOneOfMyTeams(HighlyInterruptibleRolePerson person) {
		for (Team team : this.getTeams()) {
			if (team.hasPersonAsMember(person)) {
				return true;
			}
		}
		return false;
	}

	public void addUnreadMessages(Collection<ImMessage> newUnreadImMessages) {
		this.getUnreadImMessages().addAll(newUnreadImMessages);
	}

	public List<ImMessage> getUnreadImMessages() {
		return unreadImMessages;
	}

	public void setUnreadMessages(List<ImMessage> unreadImMessages) {
		this.unreadImMessages = unreadImMessages;
	}

	public void addUnreadImMessage(ImMessage message) {
		this.getUnreadImMessages().add(message);
	}

	public boolean hasToProcessReportsWithoutIssuesToday() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean hasToProcessReportsWithoutIssueToday() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean hastToProcessIssuedReportsToday() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean hasToProcessUnreadSquealerEmails() {
		// TODO Auto-generated method stub
		return false;
	}

}
