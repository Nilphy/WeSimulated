package edu.wesimulated.firstapp.simulation.domain.avature.project;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import edu.wesimulated.firstapp.simulation.domain.Characteristic;
import edu.wesimulated.firstapp.simulation.domain.MeetingCharacteristic;
import edu.wesimulated.firstapp.simulation.domain.Person;
import edu.wesimulated.firstapp.simulation.domain.Profile;
import edu.wesimulated.firstapp.simulation.stochastic.EntryValue;
import edu.wesimulated.firstapp.simulation.stochastic.EntryValue.Type;
import edu.wesimulated.firstapp.simulation.stochastic.NumericallyModeledEntity;
import edu.wesimulated.firstapp.simulation.stochastic.ParametricAlgorithm;
import edu.wesimulated.firstapp.simulation.stochastic.StochasticVar;

public class Meeting extends MaintenanceTask implements NumericallyModeledEntity {

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
	public Map<Characteristic, EntryValue> extractValues() {
		Map<Characteristic, EntryValue> values = new HashMap<>();
		values.putAll(this.meetingProfile.extractValues());
		values.put(MeetingCharacteristic.AMOUNT_PARTICIPANTS, new EntryValue(Type.Long, this.participants.size()));
		// FIXME ¿?  maybe consider some characteristics about the participants?
		return values;
	}

	private Collection<Person> getParticipants() {
		return this.participants;
	}
}
