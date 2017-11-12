package edu.wesimulated.firstapp.simulation.domain;

import java.util.Collection;
import java.util.Map;

import edu.wesimulated.firstapp.persistence.XmlWbsNode;
import edu.wesimulated.firstapp.simulation.domain.Identifiable.IdentifiableType;
import edu.wesimulated.firstapp.simulation.stochastic.EntryValue;
import edu.wesimulated.firstapp.simulation.stochastic.NumericallyModeledEntity;

public class ProjectWbs implements NumericallyModeledEntity {

	private WbsNode rootNode;

	@Override
	public Map<Characteristic, EntryValue> extractValues() {
		// TODO complete ProjectWbs.extractValues
		return null;
	}

	public String getFirstNodeName() {
		return this.getRootNode().getName();
	}

	public void populateFrom(XmlWbsNode wbsRootNode, SimulatorFactory factory) {
		WbsNode newNode = new WbsNode(
				wbsRootNode.getName(), 
				(Task) factory.getPopulatablesPool().getPopulatable(IdentifiableType.TASK, wbsRootNode.getTaskId().toString()));
		this.addChildren(newNode, wbsRootNode.getChildren(), factory);
	}

	private void addChildren(WbsNode parentNode, Collection<XmlWbsNode> children, SimulatorFactory factory) {
		children.forEach((childNode) -> {
			WbsNode newNode = new WbsNode(
					childNode.getName(),
					(Task) factory.getPopulatablesPool().getPopulatable(IdentifiableType.TASK, childNode.getTaskId().toString()));
			parentNode.addChild(newNode);
			if (childNode.getChildren().size() > 0) {
				this.addChildren(newNode, childNode.getChildren(), factory);
			}
		});
	}
}
