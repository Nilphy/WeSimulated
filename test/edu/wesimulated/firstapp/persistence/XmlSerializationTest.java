package edu.wesimulated.firstapp.persistence;

import org.junit.Assert;
import org.junit.Test;

import edu.wesimulated.firstapp.MainApp;
import edu.wesimulated.firstapp.model.TaskData;
import edu.wesimulated.firstapp.model.WbsInnerNode;
import edu.wesimulated.firstapp.model.WbsLeafNode;
import edu.wesimulated.firstapp.model.WbsNode;

public class XmlSerializationTest {

	@Test
	public void test() {
		MainApp taskHolder = new MainApp();
		WbsNode wbs = buildTestWbs(taskHolder, 10, 5);
		XmlWbsNode serialized = UiModelToXml.buildWbsToXml(wbs);
		WbsNode wbsAfterSerializationAndUnserialization = UiModelToXml.buildWbsFromXmlRoot(serialized, taskHolder);
		Assert.assertEquals(wbs, wbsAfterSerializationAndUnserialization);
	}

	private WbsInnerNode buildTestWbs(MainApp taskHolder, int amountOfFirstLevelWbsInnerNodeChilds, int amountOfSecondLevelWbsInnerNodeChilds) {
		WbsInnerNode out = new WbsInnerNode();
		out.setName("test wbs");
		WbsNode childNode = null;
		WbsNode grandChildNode = null;
		TaskData task = null;
		for (int i = 0; i < amountOfFirstLevelWbsInnerNodeChilds; i++) {
			if (i % 2 == 0) {
				childNode = new WbsInnerNode();
				childNode.setName("node" + i);
				for (int j = 0; j < amountOfSecondLevelWbsInnerNodeChilds; j++) {
					grandChildNode = new WbsInnerNode();
					grandChildNode.setName("grandson" + i + " - " + j);
					((WbsInnerNode) childNode).addChild(grandChildNode);
				}
			} else {
				childNode = new WbsLeafNode();
				task = new TaskData("node" + i, 8, i);
				taskHolder.getTaskData().add(task);
				((WbsLeafNode) childNode).setTask(task);
			}

			out.addChild(childNode);
		}
		return out;
	}

}
