package edu.wesimulated.firstapp.simulation.domain.avature.role;

import edu.wesimulated.firstapp.simulation.domain.Person;
import edu.wesimulated.firstapp.simulation.domain.PersonCharacteristic;
import edu.wesimulated.firstapp.simulation.domain.Task;
import edu.wesimulated.firstapp.simulation.domain.worktype.WorkType;

public class RolePerson extends Person {

	private Task currentTask;

	public void increaseExperience(int amount, WorkType workType) {
		this.getProfile().increase(
				PersonCharacteristic.valueOf(PersonCharacteristic.EXPERIENCE.toString() + "_" + workType.getName()), 
				amount);
	}

	public void increaseExperienceWithWorkbenchTools(Task task, double timeExpended) {
		this.getProfile().increase(
				PersonCharacteristic.ExperienceWithWorkbenchTools, 
				timeExpended);
	}

	public void setCurrentTask(Task task) {
		this.currentTask = task;
	}

	public Task getCurrentTask() {
		return this.currentTask;
	}
}
