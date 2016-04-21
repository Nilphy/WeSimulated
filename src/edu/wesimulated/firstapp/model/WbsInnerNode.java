package edu.wesimulated.firstapp.model;

import java.util.ArrayList;
import java.util.Collection;

import javafx.beans.InvalidationListener;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.TreeItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import edu.wesimulated.firstapp.MainApp;
import edu.wesimulated.firstapp.persistence.XmlWbsNode;

public class WbsInnerNode extends WbsNode {

	private StringProperty name;
	private ObservableList<WbsNode> childrenWbsNodes;

	public WbsInnerNode(String name, ObservableList<WbsNode> childrenWbsNodes) {
		super();
		this.name = new SimpleStringProperty(name);
		this.childrenWbsNodes = childrenWbsNodes;
	}

	public WbsInnerNode() {
		this(null, FXCollections.observableArrayList());
	}

	public void addChild(WbsNode childNode) {
		this.getChildrenWbsNodes().add(childNode);
	}

	public void removeChild(WbsNode childNode) {
		this.getChildrenWbsNodes().remove(findInChildren(childNode));
	}

	private int findInChildren(WbsNode childNode) {
		int index = 0;
		for (WbsNode wbsNode : childrenWbsNodes) {
			if (wbsNode.equals(childNode)) {
				return index;
			}
			index++;
		}
		return -1;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (obj.getClass() != this.getClass()) {
			return false;
		}
		WbsInnerNode theOther = (WbsInnerNode) obj;
		return this.getName().equals(theOther.getName()) && this.getChildrenWbsNodes().equals(theOther.getChildrenWbsNodes());
	}

	@Override
	public void addListener(InvalidationListener listener) {
		this.name.addListener(listener);
		this.childrenWbsNodes.addListener(listener);
	}

	@Override
	public void removeListener(InvalidationListener listener) {
		this.name.removeListener(listener);
		this.childrenWbsNodes.removeListener(listener);
	}

	public StringProperty nameProperty() {
		return name;
	}

	@Override
	public String getName() {
		return this.name.get();
	}

	@Override
	public void setName(String name) {
		this.name.set(name);
	}

	public ObservableList<WbsNode> getChildrenWbsNodes() {
		if (this.childrenWbsNodes == null) {
			this.childrenWbsNodes = FXCollections.observableArrayList();
		}
		return this.childrenWbsNodes;
	}

	public void setChildrenWbsNodes(ObservableList<WbsNode> childrenWbsNodes) {
		this.childrenWbsNodes = childrenWbsNodes;
	}

	@Override
	public void populateFromXmlNode(XmlWbsNode xmlWbs, MainApp mainApp) {
		if (xmlWbs != null) {
			this.setName(xmlWbs.getName());
			if (xmlWbs.getChildren() != null) {
				for (XmlWbsNode childNode : xmlWbs.getChildren()) {
					WbsNode newChildNode = null;
					if (childNode.isLeaf()) {
						newChildNode = new WbsLeafNode();
					} else {
						newChildNode = new WbsInnerNode();
					}
					newChildNode.populateFromXmlNode(childNode, mainApp);
					this.addChild(newChildNode);
				}
			}
		}
	}

	@Override
	public void fillXmlNode(XmlWbsNode xmlWbsNode) {
		xmlWbsNode.setName(this.getName());
		Collection<XmlWbsNode> children = new ArrayList<>();
		for (WbsNode childNode : this.getChildrenWbsNodes()) {
			XmlWbsNode newChild = new XmlWbsNode();
			newChild.populateFromWbsNodeData(childNode);
			children.add(newChild);
		}
		if (children.size() > 0) {
			xmlWbsNode.setChildren(children);
		}
	}

	private Node buildNewIcon() {
		return new ImageView(new Image("file:resources/images/box.png"));
	}

	@Override
	public TreeItem<WbsNode> buildTreeItem() {
		return new TreeItem<>(this, buildNewIcon());
	}

	public boolean containsTask(TaskData task) {
		for (WbsNode wbsNode : childrenWbsNodes) {
			if (wbsNode.containsTask(task)) {
				return true;
			}
		}
		return false;
	}
}
