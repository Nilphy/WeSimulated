package edu.wesimulated.firstapp.model;

import javafx.scene.control.TreeItem;
import edu.wesimulated.firstapp.MainApp;
import edu.wesimulated.firstapp.persistence.XmlWbsNode;

public abstract class WbsNode {

	public abstract void populateFromXmlNode(XmlWbsNode xmlWbs, MainApp mainApp);

	public abstract void fillXmlNode(XmlWbsNode xmlWbsNode);

	public abstract TreeItem<WbsNode> buildTreeItem();

	public abstract String getName();

	public abstract void setName(String name);

	public abstract boolean containsTask(TaskData task);
	
	@Override
	public abstract boolean equals(Object obj);

	public String toString() {
		return this.getName();
	}
}
