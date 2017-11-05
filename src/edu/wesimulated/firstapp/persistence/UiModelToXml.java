package edu.wesimulated.firstapp.persistence;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import edu.wesimulated.firstapp.MainApp;
import edu.wesimulated.firstapp.model.PersonData;
import edu.wesimulated.firstapp.model.ResponsibilityAssignmentData;
import edu.wesimulated.firstapp.model.RoleData;
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

	public static List<XmlResponsibilityAssignment> convertToXml(ObservableList<ResponsibilityAssignmentData> responsibilityAssignmentData) {
		List<XmlResponsibilityAssignment> convertedList = new ArrayList<>();
		for (ResponsibilityAssignmentData ramData : responsibilityAssignmentData) {
			XmlResponsibilityAssignment convertedRamData = convertToXml(ramData);
			convertedList.add(convertedRamData);
		}
		return convertedList;
	}

	public static XmlResponsibilityAssignment convertToXml(ResponsibilityAssignmentData ramData) {
		XmlResponsibilityAssignment convertedRamData = new XmlResponsibilityAssignment();
		convertedRamData.setAccountable(ramData.isAccountable());
		convertedRamData.setConsulted(ramData.isConsulted());
		convertedRamData.setInformed(ramData.isInformed());
		convertedRamData.setResponsible(ramData.isResponsible());
		convertedRamData.setRoleName(ramData.getRole().getName());
		convertedRamData.setTaskId(ramData.getTask().getId());
		return convertedRamData;
	}

	public static ObservableList<ResponsibilityAssignmentData> convertToUiModel(List<XmlResponsibilityAssignment> xmlRam, MainApp mainApp) {
		ObservableList<ResponsibilityAssignmentData> convertedList = FXCollections.observableArrayList();
		for (XmlResponsibilityAssignment xmlResponsibilityAssignment : xmlRam) {
			ResponsibilityAssignmentData responsibilityAssignmentData = new ResponsibilityAssignmentData();
			responsibilityAssignmentData.setAccountable(xmlResponsibilityAssignment.getAccountable());
			responsibilityAssignmentData.setConsulted(xmlResponsibilityAssignment.getConsulted());
			responsibilityAssignmentData.setInformed(xmlResponsibilityAssignment.getInformed());
			responsibilityAssignmentData.setResponsible(xmlResponsibilityAssignment.getResponsible());
			responsibilityAssignmentData.setRole(mainApp.findRoleByName(xmlResponsibilityAssignment.getRoleName()));
			responsibilityAssignmentData.setTask(mainApp.findTaskById(xmlResponsibilityAssignment.getTaskId()));
			convertedList.add(responsibilityAssignmentData);
		}
		return convertedList;
	}
}
