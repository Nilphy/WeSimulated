package edu.wesimulated.firstapp.simulation.domain.mywork.role;

import edu.wesimulated.firstapp.simulation.domain.PersonCharacteristic;
import edu.wesimulated.firstapp.simulation.domain.Task;
import edu.wesimulated.firstapp.simulation.domain.worktype.WorkType;

public class Person extends edu.wesimulated.firstapp.simulation.domain.Person {

	private Task currentTask;

	public void increaseExperience(int amount, WorkType workType) {
		this.getProfile().increase(
				PersonCharacteristic.valueOf(PersonCharacteristic.EXPERIENCE.toString() + "_" + workType.getName()), 
				amount);
	}

	public void increaseExperienceWithWorkbenchTools(Task task, double timeExpended) {
		this.getProfile().increase(
				PersonCharacteristic.EXPERIENCE_WITH_WORKBENCH_TOOLS, 
				timeExpended);
	}

	public void setCurrentTask(Task task) {
		this.currentTask = task;
	}

	public Task getCurrentTask() {
		return this.currentTask;
	}
}
