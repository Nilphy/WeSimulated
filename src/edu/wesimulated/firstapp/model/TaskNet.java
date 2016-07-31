package edu.wesimulated.firstapp.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class TaskNet {
	private ObservableList<TaskNetNode> notRelatedTaskNets;
	private ObservableList<TaskData> taskData;

	public TaskNet() {
		this.setNotRelatedTaskNets(FXCollections.observableArrayList());
		this.setTaskData(FXCollections.observableArrayList());
	}

	public void initFromTasks(ObservableList<TaskData> taskData) {
		this.setTaskData(taskData);
		for (TaskData task : this.getTaskData()) {
			TaskNetNode taskNetNodeOfTask = findTaskNetNodeInAllNets(task);
			if (taskNetNodeOfTask == null) {
				taskNetNodeOfTask = addNetTaskToNet(task);
			}
			this.loadTaskDependenciesInTaskNet(taskNetNodeOfTask);
		}
	}

	public TaskNetNode addNetTaskToNet(TaskData task) {
		TaskNetNode taskNetNodeOfTask = new TaskNetNode(task);
		this.getNotRelatedTaskNets().add(taskNetNodeOfTask);
		return taskNetNodeOfTask;
	}

	public boolean validateIfTaskCouldBeDeleted(TaskData task) {
		TaskNetNode taskNetNode = this.findTaskNetNodeInAllNets(task);
		return taskNetNode.getDependencies().size() == 0 && taskNetNode.getDependents().size() == 0;
	}

	public boolean validateIfDependencyCouldBeAddedToTask(TaskData task, TaskDependencyData taskDependency) {
		TaskNetNode taskNetNode = this.findTaskNetNodeInAllNets(task);
		return taskNetNode.taskIsDependencyOrDependent(taskDependency.getTask());
	}

	public void addNewDependencyToTask(TaskData task, TaskDependencyData newDependencyNode) {
		TaskNetNode taskNetNode = findTaskNetNodeInAllNets(task);
		TaskNetNode taskNetNodeOfDependency = findTaskNetNodeInAllNets(newDependencyNode.getTask());
		taskNetNode.getDependencies().add(taskNetNodeOfDependency);
		taskNetNodeOfDependency.getDependents().add(taskNetNode);
		this.removeNotRelatedTaskNetOfDependency(taskNetNodeOfDependency);
	}

	public TaskNetNode findTaskNetNodeInAllNets(TaskData task) {
		TaskNetNode found = null;
		for (TaskNetNode taskNetNode : this.getNotRelatedTaskNets()) {
			found = taskNetNode.findTaskNetNodeByTask(task);
			if (found != null) {
				return found;
			}
		}
		return null;
	}

	private void loadTaskDependenciesInTaskNet(TaskNetNode taskNetNode) {
		for (TaskDependencyData taskDependency : taskNetNode.getTask().getTaskDependencies()) {
			TaskNetNode taskNetNodeOfDependency = findTaskNetNodeInAllNets(taskDependency.getTask());
			if (taskNetNodeOfDependency == null) {
				taskNetNodeOfDependency = new TaskNetNode(taskDependency.getTask());
			} else {
				this.removeNotRelatedTaskNetOfDependency(taskNetNodeOfDependency);
			}
			taskNetNodeOfDependency.getDependents().add(taskNetNode);
			taskNetNode.getDependencies().add(taskNetNodeOfDependency);
			this.loadTaskDependenciesInTaskNet(taskNetNodeOfDependency);
		}
	}

	private void removeNotRelatedTaskNetOfDependency(TaskNetNode taskNetNodeOfDependency) {
		int index = 0;
		boolean found = false;
		for (TaskNetNode taskNet : this.getNotRelatedTaskNets()) {
			if (taskNet.taskIsDependencyOrDependent(taskNetNodeOfDependency.getTask())) {
				found = true;
				break;
			}
			index++;
		}
		if (found) {
			this.getNotRelatedTaskNets().remove(index);
		}
	}

	public void removeDependency(TaskData task, TaskDependencyData taskDependency) {
		TaskNetNode nodeFromTask = this.findTaskNetNodeInAllNets(task);
		TaskNetNode nodeFromDependency = this.findTaskNetNodeInAllNets(taskDependency.getTask());
		nodeFromTask.removeDependency(taskDependency.getTask());
		nodeFromDependency.removeDependent(task);
		this.getNotRelatedTaskNets().add(nodeFromDependency);
	}

	private ObservableList<TaskData> getTaskData() {
		return taskData;
	}

	private void setTaskData(ObservableList<TaskData> taskData) {
		this.taskData = taskData;
	}

	private ObservableList<TaskNetNode> getNotRelatedTaskNets() {
		return notRelatedTaskNets;
	}

	private void setNotRelatedTaskNets(ObservableList<TaskNetNode> notRelatedTaskNets) {
		this.notRelatedTaskNets = notRelatedTaskNets;
	}
}
