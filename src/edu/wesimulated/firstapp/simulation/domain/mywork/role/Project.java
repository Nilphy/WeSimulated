package edu.wesimulated.firstapp.simulation.domain.mywork.role;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import edu.wesimulated.firstapp.simulation.domain.Person;
import edu.wesimulated.firstapp.simulation.domain.Role;
import edu.wesimulated.firstapp.simulation.domain.Task;

public class Project extends edu.wesimulated.firstapp.simulation.domain.Project {

	public Task findTaskToWorkOn(Date endOfSlab, Person person, Role role) {
		List<Task> tasksAssignedToPerson = this.findTasksToWorkOn(person, role);
		if (tasksAssignedToPerson.size() > 0) {
			Task firstTaskToWorkOn = Task.orderTasksAndGetFirst(tasksAssignedToPerson, (Task first, Task second) -> {
				return first.getStartDate().compareTo(second.getStartDate());
			});
			return firstTaskToWorkOn;
		}
		throw null;
	}

	public Task findTaskToWorkForRole(Person person, Role role) {
		List<Task> tasksAssignedToPerson = this.findTasksAssignedToPersonForRole(person, role);
		if (tasksAssignedToPerson.size() > 0) {
			Task firstTaskAssignedToPerson = Task.orderTasksAndGetFirst(tasksAssignedToPerson, (Task first, Task second) -> {
				return first.getStartDate().compareTo(second.getStartDate());
			});
			return firstTaskAssignedToPerson;
		}
		return null;
	}

	private List<Task> findTasksToWorkOn(Person person, Role role) {
		return this.getTasks().stream().filter((task) -> {
			if ((task.isPersonAssigned(person) || task.hasNoOneAssigned()) && task.hasWorkForRole(role) && task.isReady()) {
				return true;
			}
			return false;
		}).collect(Collectors.toList());
	}

	private List<Task> findTasksAssignedToPersonForRole(Person person, Role role) {
		return this.getTasks().stream().filter((task) -> {
			if (task.isPersonAssigned(person) && task.hasWorkForRole(role)) {
				return true;
			}
			return false;
		}).collect(Collectors.toList());
	}
}
