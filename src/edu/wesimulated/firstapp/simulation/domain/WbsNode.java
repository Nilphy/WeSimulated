package edu.wesimulated.firstapp.simulation.domain;

import java.util.ArrayList;
import java.util.Collection;

public class WbsNode {

	private String name;
	private Task task;
	private Collection<WbsNode> children;

	public WbsNode(String name, Task task) {
		this.setName(name);
		this.setTask(task);
	}

	public String getName() {
		return name;
	}

	private void setName(String name) {
		this.name = name;
	}

	private Task getTask() {
		return task;
	}

	private void setTask(Task task) {
		this.task = task;
	}

	public Collection<WbsNode> getChildren() {
		if (this.children == null) {
			this.children = new ArrayList<>();
		}
		return children;
	}

	private void setChildren(Collection<WbsNode> children) {
		this.children = children;
	}

	public void addChild(WbsNode wbsNode) {
		this.getChildren().add(wbsNode);
	}
}
