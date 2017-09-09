package edu.wesimulated.firstapp.simulation.domain.avature.project;

import java.util.Date;

public class Meeting extends MaintenanceTask {

	private Collection<Person> participants;
	private Profile meetingProfile;

	public Meeting(EntryValue value, Collection<Person> participants, long period) {
		super(period);
		this.meetingProfile = new Profile();
		this.meetingProfile.set(MeetingCharacteristic.TYPE, value);
		this.participants = participants;
	}

	@Override
	public boolean testIfRequirementsAreMet() {
		return this.allParticipantsAreFree();
	}

	private boolean allParticipantsAreFree() {
		for (Person participant : this.getParticipants()) {
			// FIXME: will them be available or I have to ask if they are
			// interruptible
			if (!participant.isAvailable()) {
				return false;
			}
		}
		return true;
	}

	@Override
	public void doAction() {
		// FIXME le da conocimiento a la gente que participa
		// FIXME avanza el tiempo¿? no tengo idea como
		ParametricAlgorithm meetingDuration = ParametricAlgorithm.buildParametricAlgorithmForVar(StochasticVar.MeetingDuration);
		this.getParticipants().forEach(participant -> meetingDuration.consider(participant));
		meetingDuration.consider(this);
		//return meetingDuration.findSample().getPrediction().getValue().longValue();
	}

	@Override
	}

	private Collection<Person> getParticipants() {
		return this.participants;
	}
}
