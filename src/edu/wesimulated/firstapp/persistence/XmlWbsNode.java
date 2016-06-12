package edu.wesimulated.firstapp.persistence;

import java.util.Collection;

import edu.wesimulated.firstapp.model.WbsNode;

public class XmlWbsNode {

	private Integer taskId;
	private String name;
	private Collection<XmlWbsNode> children;

	public void populateFromWbsNodeData(WbsNode wbsNode) {
		wbsNode.fillXmlNode(this);
	}

	public boolean isLeaf() {
		return this.taskId != null;
	}

	public Integer getTaskId() {
		return taskId;
	}

	public void setTaskId(Integer taskId) {
		this.taskId = taskId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Collection<XmlWbsNode> getChildren() {
		return children;
	}

	public void setChildren(Collection<XmlWbsNode> children) {
		this.children = children;
	}
}
