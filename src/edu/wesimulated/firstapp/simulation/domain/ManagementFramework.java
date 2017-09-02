package edu.wesimulated.firstapp.simulation.domain;

import java.util.Collection;

import edu.wesimulated.firstapp.simulation.domain.avature.project.Meeting;

public class ManagementFramework {

	private Collection<Meeting> meetings;

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
}
