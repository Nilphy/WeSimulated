package edu.wesimulated.firstapp.simulation.domain.mywork.highlyinterruptiblerole;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javafx.util.Pair;

import com.wesimulated.simulationmotor.DateUtils;
import com.wesimulated.simulationmotor.des.Prioritized.Priority;

import edu.wesimulated.firstapp.simulation.domain.Task;
import edu.wesimulated.firstapp.simulation.domain.Team;
import edu.wesimulated.firstapp.simulation.domain.mywork.highlyinterruptiblerole.Message.Status;
import edu.wesimulated.firstapp.simulation.stochastic.ParametricAlgorithm;
import edu.wesimulated.firstapp.simulation.stochastic.StochasticVar;

public class Person extends edu.wesimulated.firstapp.simulation.domain.Person {

	private List<Message> questions;
	private List<Message> pendingImMessages;
	private List<Message> pendingEmails;
	private List<Message> pendingFaceToFaceInteractions;
	private List<SquealerReport> pendingSquealerReports;
	private Task currentTask;

	public Pair<Date, Message> resolvePendingEmails() {
		Date dateUntilResolution = null;
		Message pendingEmailToResolve = null;
		Collection<Message> pendingEmailsResolved = new ArrayList<>();
		boolean endProcessing = false;
		for (Message pendingEmail : this.getPendingEmails()) {
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
		this.applyEffectsOfResolvedMessages(pendingEmailsResolved);
		this.getPendingEmails().removeAll(pendingEmailsResolved);
		return new Pair<>(dateUntilResolution, pendingEmailToResolve);
	}

	public Pair<Date, Message> resolvePendingFaceToFaceInteractions() {
		Date dateUntilResolution = null;
		Message pendingFaceToFaceInteractionToResolve = null;
		Collection<Message> faceToFaceInteractionsResolved = new ArrayList<>();
		boolean endProcessing = false;
		for (Message pendingFaceToFaceInteraction : this.getPendingFaceToFaceInteractions()) {
			switch (pendingFaceToFaceInteraction.getStatus()) {
			case NEW:
				dateUntilResolution = this.calculateMessageTime(pendingFaceToFaceInteractionToResolve, StochasticVar.TimeToListenFaceToFaceInteraction);
				pendingFaceToFaceInteractionToResolve = pendingFaceToFaceInteraction;
				endProcessing = true;
				break;
			case RELEASE_PERSON:
				this.setAvailable(true);
				faceToFaceInteractionsResolved.add(pendingFaceToFaceInteraction);
				endProcessing = true;
				break;
			case RESPOND:
				dateUntilResolution = this.calculateMessageTime(pendingFaceToFaceInteraction, StochasticVar.TimeToRespondFaceToFaceInteraction);
				pendingFaceToFaceInteractionToResolve = pendingFaceToFaceInteraction;
				endProcessing = true;
				break;
			default:
				throw new IllegalStateException("cannot resolve another type of message");
			}
			if (endProcessing) {
				break;
			}
		}
		this.applyEffectsOfResolvedMessages(faceToFaceInteractionsResolved);
		this.getPendingFaceToFaceInteractions().removeAll(faceToFaceInteractionsResolved);
		return new Pair<>(dateUntilResolution, pendingFaceToFaceInteractionToResolve);
	}

	private void applyEffectsOfResolvedMessages(Collection<Message> messagesResolved) {
		for (Message messageResolved : messagesResolved) {
			messageResolved.applyEffectsOfResolution();
		}

	}

	public Pair<Date, Message> resolvePendingImMessages() {
		Date dateUntilResolution = null;
		Message pendingImMessageToResolve = null;
		Collection<Message> pendingImMessagesResolved = new ArrayList<>();
		boolean endProcessing = false;
		for (Message pendingImMessage : this.getPendingImMessages()) {
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
		this.applyEffectsOfResolvedMessages(pendingImMessagesResolved);
		this.pendingImMessages.removeAll(pendingImMessagesResolved);
		return new Pair<>(dateUntilResolution, pendingImMessageToResolve);
	}

	public Pair<Date, Message> resolvePendingSquealerReports() {
		Date dateUntilResolution = null;
		Message PendingSquealerReportToResolve = null;
		Collection<Message> pendingSquealerReportsResolved = new ArrayList<>();
		boolean endProcessing = false;
		for (SquealerReport pendingSquealerReport : this.getPendingSquealerReports()) {
			pendingSquealerReport.analize();
			switch (pendingSquealerReport.getStatus()) {
			case ISSUED:
				if (pendingSquealerReport.isHappeningMoreThanUsual()) {
					pendingSquealerReport.setStatus(Status.RESOLVE);
					PendingSquealerReportToResolve = pendingSquealerReport;
					endProcessing = true;
					dateUntilResolution = this.calculateMessageTime(pendingSquealerReport, StochasticVar.TimeToResolveSquealerReport);
				} else {
					pendingSquealerReport.setStatus(Status.PROCESSED);
					pendingSquealerReportsResolved.add(pendingSquealerReport);
				}
				break;
			case NOT_ISSUED:
				PendingSquealerReportToResolve = pendingSquealerReport;
				endProcessing = true;
				dateUntilResolution = this.calculateMessageTime(pendingSquealerReport, StochasticVar.TimeToIssueReport);
			case PROCESSED:
				pendingSquealerReportsResolved.add(pendingSquealerReport);
				break;
			default:
				throw new IllegalStateException("Cannot resolve another type of message");
			}
			if (endProcessing) {
				break;
			}
		}
		this.applyEffectsOfResolvedMessages(pendingSquealerReportsResolved);
		this.pendingSquealerReports.removeAll(pendingSquealerReportsResolved);
		return new Pair<>(dateUntilResolution, PendingSquealerReportToResolve);
	}

	public Date calculateMessageTime(Message message, StochasticVar stochasticVar) {
		ParametricAlgorithm timeToResolveIm = ParametricAlgorithm.buildParametricAlgorithmForVar(stochasticVar);
		timeToResolveIm.consider(this);
		timeToResolveIm.consider(message);
		return DateUtils.addMilis(message.getCurrentDate(), timeToResolveIm.findSample().getPrediction().getValue().floatValue());
	}

	public Float getPriorityMessageFrom(Person sender) {
		if (this.isWorginWithMe(sender) || this.isASuperior(sender)) {
			return Priority.HIGH.get();
		} else if (this.isInOneOfMyTeams(sender)) {
			return Priority.MED.get();
		} else {
			return Priority.LOW.get();
		}
	}

	private boolean isASuperior(Person person) {
		return this.isWorginWithMe(person) 
				&& this.getCurrentTask().getResponsiblePeople().contains(this)
				&& (this.getCurrentTask().getAccountablePeople().contains(person) 
						|| this.getCurrentTask().getConsultedPeople().contains(person) 
						|| this.getCurrentTask().getInformedPeople().contains(person));
	}

	private boolean isInOneOfMyTeams(Person person) {
		for (Team team : this.getTeams()) {
			if (team.hasPersonAsMember(person)) {
				return true;
			}
		}
		return false;
	}

	private List<Message> categorizeInteractions() {
		Collection<Message> unreadImMessages = new ArrayList<>();
		for (Message question : this.getQuestions()) {
			// FIXME si las dos personas están trabajando y no están en el mismo
			// lugar
			// seguramente sea un imMessage a menos de que el mensaje sea muy
			// grande
		}
		return null;
	}

	public List<Message> getPendingImMessages() {
		this.categorizeInteractions();
		return this.pendingImMessages;
	}

	public List<Message> getPendingEmails() {
		this.categorizeInteractions();
		return this.pendingEmails;
	}

	public List<Message> getPendingFaceToFaceInteractions() {
		this.categorizeInteractions();
		return this.pendingFaceToFaceInteractions;
	}

	public void addUnreadImMessage(Message message) {
		this.getPendingImMessages().add(message);
	}

	/**
	 * This method is private because the questions per se doesn't have a form
	 * its needed to define the medium and that is defined in the
	 * generateInteracctions method
	 * 
	 * @return
	 */
	private List<Message> getQuestions() {
		return questions;
	}

	public void setQuestions(List<Message> questions) {
		this.questions = questions;
	}

	// TODO rename to squealer reports
	public List<SquealerReport> getPendingSquealerReports() {
		// FIXME this field gets populated prom the project simulator
		return pendingSquealerReports;
	}

	public void setCurrentTask(Task task) {
		this.currentTask = task;
	}

	public Task getCurrentTask() {
		return this.currentTask;
	}
}
