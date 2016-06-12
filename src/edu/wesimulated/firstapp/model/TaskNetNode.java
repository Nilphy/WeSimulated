package edu.wesimulated.firstapp.model;

import java.util.ArrayList;
import java.util.Collection;

public class TaskNetNode {

	private TaskData task;
	private Collection<TaskNetNode> dependencies;
	private Collection<TaskNetNode> dependents;

	public TaskNetNode(TaskData task) {
		dependencies = new ArrayList<>();
		dependents = new ArrayList<>();
		this.task = task;
	}

	public boolean taskIsDependencyOrDependent(TaskData soughtTask) {
		return findTaskNetNodeByTask(soughtTask) != null;
	}

	public TaskNetNode findTaskNetNodeByTask(TaskData soughtTask) {
		TaskNetNode found = this.findTaskInDependencies(soughtTask);
		if (found != null) {
			return found;
		}
		return findTaskInDependendents(soughtTask);
	}

	public TaskNetNode findTaskInDependencies(TaskData soughtTask) {
		if (soughtTask.equals(this.task)) {
			return this;
		}
		for (TaskNetNode child : this.dependencies) {
			return child.findTaskInDependencies(soughtTask);
		}
		return null;
	}

	public TaskNetNode findTaskInDependendents(TaskData soughtTask) {
		if (soughtTask.equals(this.task)) {
			return this;
		}
		for (TaskNetNode child : this.dependents) {
			return child.findTaskInDependendents(soughtTask);
		}
		return null;
	}

	public void removeDependency(TaskData taskToRemove) {
		this.removeFromList(taskToRemove, this.getDependencies());
	}

	public void removeDependent(TaskData taskToRemove) {
		this.removeFromList(taskToRemove, this.getDependents());
	}

	private void removeFromList(TaskData taskToRemove, Collection<TaskNetNode> list) {
		Integer index = 0;
		Boolean found = false;
		for (TaskNetNode node : list) {
			if (node.getTask().equals(taskToRemove)) {
				found = true;
				break;
			}
			index++;
		}
		if (found) {
			list.remove(index);
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (!obj.getClass().equals(this.getClass())) {
			return false;
		}
		TaskNetNode theOther = (TaskNetNode) obj;
		if (theOther.getTask() == null && this.getTask() == null) {
			return true;
		}
		if (theOther.getTask() == null || this.getTask() == null) {
			return false;
		}
		return theOther.getTask().equals(this.getTask());
	}

	public TaskData getTask() {
		return task;
	}

	public void setTask(TaskData task) {
		this.task = task;
	}

	public Collection<TaskNetNode> getDependencies() {
		return dependencies;
	}

	public void setDependencies(Collection<TaskNetNode> dependencies) {
		this.dependencies = dependencies;
	}

	public Collection<TaskNetNode> getDependents() {
		return dependents;
	}

	public void setDependents(Collection<TaskNetNode> dependents) {
		this.dependents = dependents;
	}

}
