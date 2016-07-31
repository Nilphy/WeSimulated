package edu.wesimulated.firstapp.model;

import edu.wesimulated.firstapp.simulation.domain.PrecedenceType;
import javafx.scene.Node;
import javafx.scene.control.TreeItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class TaskDependencyData {

	private TaskData task;
	private PrecedenceType precedence;

	public TaskDependencyData() {
	}

	public TaskDependencyData(TaskData taskData, PrecedenceType precedenceType) {
		this.task = taskData;
		this.precedence = precedenceType;
	}

	public TreeItem<TaskDependencyData> buildTreeItem() {
		return new TreeItem<>(this, buildNewIcon());
	}

	public TreeItem<TaskDependencyData> buildDummyTreeItem() {
		return new TreeItem<TaskDependencyData>(this);
	}

	public Node buildNewIcon() {
		Image image = new Image("file:resources/images/" + this.getPrecedence().getImageName() + ".png", 15, 15, true, true);
		ImageView imageView = new ImageView(image);
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
