package edu.wesimulated.firstapp.simulation.domain.avature.highlyinterruptiblerole;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javafx.util.Pair;

import com.wesimulated.simulationmotor.DateUtils;
import com.wesimulated.simulationmotor.des.Prioritized.Priority;

import edu.wesimulated.firstapp.simulation.domain.Person;
import edu.wesimulated.firstapp.simulation.domain.Team;
import edu.wesimulated.firstapp.simulation.stochastic.ParametricAlgorithm;
import edu.wesimulated.firstapp.simulation.stochastic.StochasticVar;

public class HighlyInterruptibleRolePerson extends Person {

	private List<ImMessage> pendingImMessages;
	private List<Email> pendingEmails;
	private List<TeamMemberQuestion> pendingFaceToFaceQuestions;

	public Pair<Date, Message> resolvePendingEmails() {
		Date dateUntilResolution = null;
		Email pendingEmailToResolve = null;
		Collection<Email> pendingEmailsResolved = new ArrayList<>();
		boolean endProcessing = false;
		for (Email pendingEmail : this.getPendingEmails()) {
			switch (pendingEmail.getStatus()) {
			case NEW:
				dateUntilResolution = this.calculateMessageTime(pendingEmail, StochasticVar.TimeToReadEmail);
				pendingEmailToResolve = pendingEmail;
				endProcessing = true;
				break;
			case RELEASE_PERSON:
				this.setAvailable(true);
				pendingEmailsResolved.add(pendingEmail);
				pendingEmailToResolve = pendingEmail;
				endProcessing = true;
				break;
			case RESPOND:
				dateUntilResolution = this.calculateMessageTime(pendingEmail, StochasticVar.TimeToRespondEmail);
				pendingEmailToResolve = pendingEmail;
				endProcessing = true;
				break;
			case PROCESSED:
				pendingEmailsResolved.add(pendingEmail);
				break;
			default:
				throw new IllegalStateException("cannot resolve another type of message");
			}
			if (endProcessing) {
				break;
			}
		}
		this.getPendingEmails().removeAll(pendingEmailsResolved);
		return new Pair<>(dateUntilResolution, pendingEmailToResolve);
	}

	public Pair<Date, Message> resolvePendingImMessages() {
		Date dateUntilResolution = null;
		ImMessage pendingImMessageToResolve = null;
		Collection<ImMessage> pendingImMessagesResolved = new ArrayList<>();
		boolean endProcessing = false;
		for (ImMessage pendingImMessage : this.getPendingImMessages()) {
			// The difference is here that ImMessages don't take time to read
			pendingImMessage.analize();
			switch (pendingImMessage.getStatus()) {
			case RELEASE_PERSON:
				this.setAvailable(true);
				pendingImMessagesResolved.add(pendingImMessage);
				pendingImMessageToResolve = pendingImMessage;
				endProcessing = true;
				break;
			case RESPOND:
				dateUntilResolution = this.calculateMessageTime(pendingImMessage, StochasticVar.TimeToResolveIm);
				pendingImMessageToResolve = pendingImMessage;
				endProcessing = true;
				break;
			case PROCESSED:
				pendingImMessagesResolved.add(pendingImMessage);
				break;
			default:
				throw new IllegalStateException("cannot resolve another type of message");
			}
			if (endProcessing) {
				break;
			}
		}
		pendingImMessages.removeAll(pendingImMessagesResolved);
		return new Pair<>(dateUntilResolution, pendingImMessageToResolve);
	}

	public Date calculateMessageTime(Message message, StochasticVar stochasticVar) {
		ParametricAlgorithm timeToResolveIm = ParametricAlgorithm.buildParametricAlgorithmForVar(stochasticVar);
		timeToResolveIm.consider(this);
		timeToResolveIm.consider(message);
		return DateUtils.addMilis(message.getCurrentDate(), timeToResolveIm.findSample().getPrediction().getValue().floatValue());
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

	private List<ImMessage> categorizeInteractions() {
		Collection<ImMessage> unreadImMessages = new ArrayList<ImMessage>();
		for (Question question : this.getQuestions()) {
			// FIXME si las dos personas están trabajando y no están en el mismo
			// lugar
			// seguramente sea un imMessage a menos de que el mensaje sea muy
			// grande
		}
		return null;
	}

	public List<ImMessage> getPendingImMessages() {
		this.categorizeInteractions();
		return this.pendingImMessages;
	}

	public List<Email> getPendingEmails() {
		this.categorizeInteractions();
		return this.pendingEmails;
	}

	public List<TeamMemberQuestion> getPendingFaceToFaceQuestions() {
		this.categorizeInteractions();
		return this.pendingFaceToFaceQuestions;
	}

	public void addUnreadImMessage(ImMessage message) {
		this.getPendingImMessages().add(message);
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

	/**
	 * This method is private because the questions per se doesn't have a form
	 * its needed to define the medium and that is defined in the
	 * generateInteracctions method
	 * 
	 * @return
	 */
	private List<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(List<Question> questions) {
		this.questions = questions;
	}

}
