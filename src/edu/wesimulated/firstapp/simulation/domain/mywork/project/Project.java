package edu.wesimulated.firstapp.simulation.domain.mywork.project;

import java.util.Collection;
import java.util.List;

import edu.wesimulated.firstapp.simulation.domain.Person;

public class Project extends edu.wesimulated.firstapp.simulation.domain.Project {

	private List<Risk> risks;
	private List<MaintenanceTask> maintenanceTasks;

	public void registerPersonResign(Person person) {
		this.getPeople().remove(person);
	}

	public Collection<MaintenanceTask> getMaintenanceTasks() {
		return this.maintenanceTasks;
	}

	public Collection<Risk> getRisks() {
		return this.risks;
	}

	public Collection<Meeting> findRegularMeetings() {
		return this.getManagementFramework().getMeetings();
	}

}
