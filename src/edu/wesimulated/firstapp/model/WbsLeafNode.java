package edu.wesimulated.firstapp.model;

import javafx.scene.Node;
import javafx.scene.control.TreeItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import edu.wesimulated.firstapp.MainApp;
import edu.wesimulated.firstapp.persistence.XmlWbsNode;

public class WbsLeafNode extends WbsNode {

	private TaskData task;

	public WbsLeafNode(TaskData task) {
		super();
		this.task = task;
	}
	
	public WbsLeafNode() {
		this(null);
	}

	public void populateFromXmlNode(XmlWbsNode xmlWbs, MainApp mainApp) {
		this.setTask(mainApp.findTaskById(xmlWbs.getTaskId()));
	}

	@Override
	public void fillXmlNode(XmlWbsNode xmlWbsNode) {
		xmlWbsNode.setTaskId(this.getTask().getId());
	}

	public TaskData getTask() {
		return task;
	}

	public void setTask(TaskData task) {
		this.task = task;
	}
	
	public Node buildNewIcon() {
		return new ImageView(new Image("file:resources/images/task.png"));
	}

	@Override
	public TreeItem<WbsNode> buildTreeItem() {
		return new TreeItem<>(this, buildNewIcon());
	}

	@Override
	public String getName() {
		return this.getTask().getName();
	}

	@Override
	public void setName(String name) {
		this.getTask().setName(name);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {return false;}
		if (!obj.getClass().equals(this.getClass())) { return false;}
		WbsLeafNode theOther = (WbsLeafNode) obj;
		if (theOther.getTask() == null && this.getTask() == null) {
			return true;
		}
		if (theOther.getTask() == null || this.getTask() == null) {
			return false;
		}
		return theOther.getTask().equals(this.getTask());
	}

	@Override
	public boolean containsTask(TaskData task) {
		return this.getTask().equals(task);
	}
}
