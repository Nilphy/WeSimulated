package edu.wesimulated.firstapp.simulation.domain;

import java.util.Date;

import javafx.collections.ObservableList;
import edu.wesimulated.firstapp.model.PersonData;
import edu.wesimulated.firstapp.model.TaskData;
import edu.wesimulated.firstapp.model.TaskDependencyData;
import edu.wesimulated.firstapp.model.TaskNeed;

public class TaskBuilder {

	/**
	 * If the date is null it means that all attribute changes should be sent to hla rmi when the simulation starts all togheter
	 * @param task
	 * @param when
	 * @return
	 */
	public static Task createFromTaskData(TaskData task, Date when, SimulatorFactory factory) {
		Task newTask = factory.makeTask();
		newTask.setName(task.getName(), when);
		TaskBuilder.incorporatePersonToTask((Person personParam, Task taskParam) -> taskParam.addResponsiblePerson(personParam), task.getResponsiblePeople(), newTask, factory);
		TaskBuilder.incorporatePersonToTask((Person personParam, Task taskParam) -> taskParam.addAccountablePerson(personParam), task.getAccountablePeople(), newTask, factory);
		TaskBuilder.incorporatePersonToTask((Person personParam, Task taskParam) -> taskParam.addConsultedPerson(personParam), task.getConsultedPeople(), newTask, factory);
		TaskBuilder.incorporatePersonToTask((Person personParam, Task taskParam) -> taskParam.addInformedPerson(personParam), task.getInformedPeople(), newTask, factory);
		newTask.setEndDate(task.getEndDate());
		newTask.setStartDate(task.getStartDate());
		for (TaskDependencyData taskDependency : task.getTaskDependencies()) {
			Task dependentTask = TaskBuilder.createFromTaskData(taskDependency.getTask(), when, factory);
			newTask.addTaskDependency(new TaskDependency(dependentTask, taskDependency.getPrecedence()));
		}
		newTask.setCostInHours(task.getUnitsOfWork(), TaskNeed.Development);
		return newTask;
	}

	private static void incorporatePersonToTask(PersonAssignerToTask personAssigner, ObservableList<PersonData> peopleData, Task task, SimulatorFactory factory) {
		for (PersonData person : peopleData) {
			personAssigner.assign(PersonBuilder.createFromPersonData(person, factory), task);
		}
	}

	@FunctionalInterface
	private interface PersonAssignerToTask {
		public void assign(Person person, Task task);
	}
}
