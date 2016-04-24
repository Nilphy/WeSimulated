package edu.wesimulated.firstapp.persistence;

import edu.wesimulated.firstapp.MainApp;
import edu.wesimulated.firstapp.model.WbsInnerNode;
import edu.wesimulated.firstapp.model.WbsNode;

public class UiModelToXml {

	public static XmlWbsNode buildWbsToXml(WbsNode wbs) {
		XmlWbsNode wbsRootNode = new XmlWbsNode();
		wbsRootNode.populateFromWbsNodeData(wbs);
		return wbsRootNode;
	}
	
	public static WbsInnerNode buildWbsFromXmlRoot(XmlWbsNode xmlWbs, MainApp mainApp) {
		WbsInnerNode wbsNodeData = new WbsInnerNode();
		wbsNodeData.populateFromXmlNode(xmlWbs, mainApp);
		return wbsNodeData;
	}
}
