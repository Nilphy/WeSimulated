package edu.wesimulated.firstapp.simulation.domain.avature.role;

import edu.wesimulated.firstapp.simulation.domain.Person;
import edu.wesimulated.firstapp.simulation.domain.PersonCharacteristic;
import edu.wesimulated.firstapp.simulation.domain.worktype.WorkType;

public class RolePerson extends Person {

	public void increaseExperience(int amount, WorkType workType) {
		this.getProfile().increase(
				PersonCharacteristic.valueOf(PersonCharacteristic.EXPERIENCE.toString() + "_" + workType.getName()),
				amount);
	}

}
