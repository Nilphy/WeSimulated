package edu.wesimulated.firstapp.model;

import javafx.scene.Node;
import javafx.scene.control.TreeItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class TaskDependency {

	private TaskData task;
	private PrecedenceType precedence;

	public TaskDependency() {
	}

	public TaskDependency(TaskData taskData, PrecedenceType precedenceType) {
		this.task = taskData;
		this.precedence = precedenceType;
	}

	public TreeItem<TaskDependency> buildTreeItem() {
		return new TreeItem<>(this, buildNewIcon());
	}

	public TreeItem<TaskDependency> buildDummyTreeItem() {
		return new TreeItem<TaskDependency>(this);
	}

	public Node buildNewIcon() {
		Image image = new Image("file:resources/images/" + this.getPrecedence().getImageName() + ".png", 15, 15, true, true);
		ImageView imageView = new ImageView(image);
		imageView.setAccessibleHelp(this.getPrecedence().getImageName());
		return imageView;
	}

	public TaskData getTask() {
		return task;
	}

	public void setTask(TaskData task) {
		this.task = task;
	}

	public PrecedenceType getPrecedence() {
		return precedence;
	}

	public void setPrecedence(PrecedenceType precedence) {
		this.precedence = precedence;
	}

	@Override
	public String toString() {
		return getTask().getName();
	}
}
