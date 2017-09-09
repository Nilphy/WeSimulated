package edu.wesimulated.firstapp.simulation.domain;

import java.util.Collection;

import edu.wesimulated.firstapp.simulation.domain.avature.project.Meeting;

public class ManagementFramework {

	public enum Type {
		SCRUM, CASCADE
	}

	private Collection<Meeting> meetings;
	private Type type;

	public static ManagementFramework createScrum() {
		ManagementFramework fwk = new ManagementFramework();
		// FIXME: generate meetings (to configure the project with different frameworks
		// Kick off
		// Daily meetings
		// Cycle overview
		// Cycle planning
		return fwk;
	}

	public Collection<Meeting> getMeetings() {
		return meetings;
	}

	public void setMeetings(Collection<Meeting> meetings) {
		this.meetings = meetings;
	}

	public void addMeeting(Meeting meeting) {
		this.getMeetings().add(meeting);
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}
}
