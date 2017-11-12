package edu.wesimulated.firstapp.simulation.domain;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import edu.wesimulated.firstapp.simulation.domain.mywork.project.Meeting;
import edu.wesimulated.firstapp.simulation.stochastic.EntryValue;
import edu.wesimulated.firstapp.simulation.stochastic.NumericallyModeledEntity;

public class ManagementFramework implements NumericallyModeledEntity {

	public enum Type {
		SCRUM, CASCADE
	}

	private Collection<Meeting> meetings;
	private Type type;

	public static ManagementFramework createScrum() {
		ManagementFramework fwk = new ManagementFramework();
		fwk.setType(Type.SCRUM);
		// FIXME: generate meetings (to configure the project with different frameworks
		// Kick off
		// Daily meetings
		// Cycle overview
		// Cycle planning
		return fwk;
	}

	@Override
	public Map<Characteristic, EntryValue> extractValues() {
		Map<Characteristic, EntryValue> values = new HashMap<Characteristic, EntryValue>();
		values.put(ProjectCharacteristic.MANAGEMENT_FRAMEWORK_TYPE, new EntryValue(edu.wesimulated.firstapp.simulation.stochastic.EntryValue.Type.STRING, this.type.toString()));
		values.put(ProjectCharacteristic.AMOUNT_MEETINGS, new EntryValue(edu.wesimulated.firstapp.simulation.stochastic.EntryValue.Type.LONG, this.meetings.size()));
		return values;
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
