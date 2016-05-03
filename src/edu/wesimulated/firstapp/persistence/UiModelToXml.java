package edu.wesimulated.firstapp.persistence;

import edu.wesimulated.firstapp.MainApp;
import edu.wesimulated.firstapp.model.WbsInnerNode;
import edu.wesimulated.firstapp.model.WbsNode;

public class UiModelToXml {

	public static XmlWbsNode convertToXml(WbsNode wbs) {
		XmlWbsNode wbsRootNode = new XmlWbsNode();
		wbsRootNode.populateFromWbsNodeData(wbs);
		return wbsRootNode;
	}
	
	public static WbsInnerNode convertToUiModel(XmlWbsNode xmlWbs, MainApp mainApp) {
		WbsInnerNode wbsNodeData = new WbsInnerNode();
		wbsNodeData.populateFromXmlNode(xmlWbs, mainApp);
		return wbsNodeData;
	}

	public static void changeRolesFromMainAppOnes(List<PersonData> persons, MainApp mainApp) {
		for (PersonData person : persons) {
			List<RoleData> xmlRoles = person.getRoles();
			person.setRoles(FXCollections.observableArrayList());
			for (RoleData role : xmlRoles) {
				person.addRole(mainApp.findRoleByName(role.getName()));
			}
		}
	}
}
