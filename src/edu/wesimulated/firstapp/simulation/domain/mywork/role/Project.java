package edu.wesimulated.firstapp.simulation.domain.mywork.role;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import edu.wesimulated.firstapp.simulation.domain.Person;
import edu.wesimulated.firstapp.simulation.domain.Role;

public class Project extends edu.wesimulated.firstapp.simulation.domain.Project {

	public Task findTaskToWorkOn(Date endOfSlab, Person person, Role role) {
		List<edu.wesimulated.firstapp.simulation.domain.Task> tasksAssignedToPerson = this.castListToParent(this.findTasksToWorkOn(person, role));
		if (tasksAssignedToPerson.size() > 0) {
			Task firstTaskToWorkOn = (Task) edu.wesimulated.firstapp.simulation.domain.Task.orderTasksAndGetFirst(tasksAssignedToPerson,
					(edu.wesimulated.firstapp.simulation.domain.Task first, edu.wesimulated.firstapp.simulation.domain.Task second) -> {
						return first.getStartDate().compareTo(second.getStartDate());
					});
			return firstTaskToWorkOn;
		}
		throw null;
	}

	public Task findTaskToWorkForRole(Person person, Role role) {
		List<edu.wesimulated.firstapp.simulation.domain.Task> tasksAssignedToPerson = this.castListToParent(this.findTasksAssignedToPersonForRole(person, role));
		if (tasksAssignedToPerson.size() > 0) {
			Task firstTaskAssignedToPerson = (Task) edu.wesimulated.firstapp.simulation.domain.Task.orderTasksAndGetFirst(tasksAssignedToPerson, (edu.wesimulated.firstapp.simulation.domain.Task first,
					edu.wesimulated.firstapp.simulation.domain.Task second) -> {
				return first.getStartDate().compareTo(second.getStartDate());
			});
			return firstTaskAssignedToPerson;
		}
		return null;
	}

	private List<Task> findTasksToWorkOn(Person person, Role role) {
		return this.castListToChild(this.getTasks().stream().filter((task) -> {
			if ((task.isPersonAssigned(person) || task.hasNoOneAssigned()) && task.hasWorkForRole(role) && task.isReady()) {
				return true;
			}
			return false;
		}).collect(Collectors.toList()));
	}

	private List<Task> findTasksAssignedToPersonForRole(Person person, Role role) {
		return this.castListToChild(this.getTasks().stream().filter((task) -> {
			if (task.isPersonAssigned(person) && task.hasWorkForRole(role)) {
				return true;
			}
			return false;
		}).collect(Collectors.toList()));
	}

	private List<edu.wesimulated.firstapp.simulation.domain.Task> castListToParent(List<Task> tasks) {
		return tasks.stream().map((task) -> (edu.wesimulated.firstapp.simulation.domain.Task) task).collect(Collectors.toList());
	}

	private List<Task> castListToChild(List<edu.wesimulated.firstapp.simulation.domain.Task> tasks) {
		return tasks.stream().map((task) -> (Task) task).collect(Collectors.toList());
	}
}
