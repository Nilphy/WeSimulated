package edu.wesimulated.firstapp.simulation.domain.avature.highlyinterruptiblerole;

import java.util.Collection;
import java.util.Date;

import com.wesimulated.simulationmotor.des.Prioritized;
import com.wesimulated.simulationmotor.des.processbased.Entity;

public class Squealer extends Entity implements HighlyInterruptibleRolePrioritized {

	private Collection<SquealerReadmeEmails> readmeEmails;
	private Collection<SquealerEmails> unreadEmails; 
	private Collection<SquealerUnissuedReports> reportsWithoutIssue;
	private Collection<SquealerIssuedReports> issuedRepors;
	private HighlyInterruptibleRolePerson person;
	
	public Squealer(HighlyInterruptibleRolePerson person) {
		this.setPerson(person);
	}

	@Override
	protected Date doProcess() {
		if (this.readmeEmails.size() > 0) {
			this.processReamdeEmails();
		}
		if (this.getPerson().hasToProcessUnreadSquealerEmails()) {
			this.processUnreadEmails();
		}
		if (this.getPerson().hasToProcessReportsWithoutIssueToday()) {
			this.processReportsWithoutIssue();
		}
		if (this.getPerson().hastToProcessIssuedReportsToday()) {
			this.processIssuedReports();
		}
		return null;
	}

	private void processUnreadEmails() {
		// TODO Auto-generated method stub
		
	}

	private void processReamdeEmails() {
		// TODO Auto-generated method stub
		
	}

	private void processIssuedReports() {
		// TODO Auto-generated method stub
		
	}

	private void processReportsWithoutIssue() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void acceptInterruption(Date interruptionDate) {
		// TODO Auto-generated method stub
		
	}

	public HighlyInterruptibleRolePerson getPerson() {
		return person;
	}

	public void setPerson(HighlyInterruptibleRolePerson person) {
		this.person = person;
	}

	public Collection<SquealerIssuedReports> getIssuedRepors() {
		return issuedRepors;
	}

	public void setIssuedRepors(Collection<SquealerIssuedReports> issuedRepors) {
		this.issuedRepors = issuedRepors;
	}

	public Collection<SquealerUnissuedReports> getReportsWithoutIssue() {
		return reportsWithoutIssue;
	}

	public void setReportsWithoutIssue(Collection<SquealerUnissuedReports> reportsWithoutIssue) {
		this.reportsWithoutIssue = reportsWithoutIssue;
	}

	public Collection<SquealerEmails> getUnreadEmails() {
		return unreadEmails;
	}

	public void setUnreadEmails(Collection<SquealerEmails> emails) {
		this.unreadEmails = emails;
	}

	public Collection<SquealerReadmeEmails> getReadmeEmails() {
		return readmeEmails;
	}

	public void setReadmeEmails(Collection<SquealerReadmeEmails> readmeEmails) {
		this.readmeEmails = readmeEmails;
	}

	@Override
	public Float calculatePriority() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int compareTo(Prioritized o) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Float MaxPriority() {
		// TODO Auto-generated method stub
		return null;
	}

}
