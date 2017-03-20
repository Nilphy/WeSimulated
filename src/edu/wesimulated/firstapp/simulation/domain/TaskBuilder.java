package edu.wesimulated.firstapp.simulation.domain;

import javafx.collections.ObservableList;
import edu.wesimulated.firstapp.model.PersonData;
import edu.wesimulated.firstapp.model.TaskData;
import edu.wesimulated.firstapp.model.TaskDependencyData;

public class TaskBuilder {

	public static Task createFromTaskData(TaskData task) {
		Task newTask = new Task();
		newTask.setName(task.getName());
		TaskBuilder.incorporatePersonToTask((Person personParam, Task taskParam) -> taskParam.addResponsiblePerson(personParam), task.getResponsiblePeople(), newTask);
		TaskBuilder.incorporatePersonToTask((Person personParam, Task taskParam) -> taskParam.addAccountablePerson(personParam), task.getAccountablePeople(), newTask);
		TaskBuilder.incorporatePersonToTask((Person personParam, Task taskParam) -> taskParam.addConsultedPerson(personParam), task.getConsultedPeople(), newTask);
		TaskBuilder.incorporatePersonToTask((Person personParam, Task taskParam) -> taskParam.addInformedPerson(personParam), task.getInformedPeople(), newTask);
		newTask.setEndDate(task.getEndDate());
		newTask.setStartDate(task.getStartDate());
		for (TaskDependencyData taskDependency : task.getTaskDependencies()) {
			Task dependentTask = TaskBuilder.createFromTaskData(taskDependency.getTask());
			newTask.addTaskDependency(new TaskDependency(dependentTask, taskDependency.getPrecedence()));
		}
		// TODO this couldn't be this way the units of work should be separated into different categories of work
		newTask.setUnitsOfWork(task.getUnitsOfWork());
		return newTask;
	}

	private static void incorporatePersonToTask(PersonAssignerToTask personAssigner, ObservableList<PersonData> peopleData, Task task) {
		for (PersonData person : peopleData) {
			personAssigner.assign(PersonBuilder.createFromPersonData(person), task);
		}
	}

	@FunctionalInterface
	private interface PersonAssignerToTask {
		public void assign(Person person, Task task);
	}
}
