package edu.wesimulated.firstapp.view;

import java.io.IOException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import org.apache.commons.validator.routines.FloatValidator;

import com.javacommon.utils.IntegerUtils;

import edu.wesimulated.firstapp.MainApp;
import edu.wesimulated.firstapp.model.PersonData;
import edu.wesimulated.firstapp.model.RoleData;

public class PersonEditController {
	@FXML
	private TextField firstNameField;
	@FXML
	private TextField lastNameField;
	@FXML
	private TextField hoursPerDayField;
	@FXML
	private TextField efficencyField;
	@FXML
	private TableView<RoleData> roleTable;
	@FXML
	private TableColumn<RoleData, String> roleNameColumn;

	private Stage dialogStage;
	private PersonData person;
	private boolean okClicked = false;
	private MainApp mainApp;

	@FXML
	private void initialize() {
		roleNameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
	}

	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}

	public void setPerson(PersonData person) {
		this.person = person;
		this.firstNameField.setText(person.getFirstName());
		this.lastNameField.setText(person.getLastName());
		this.hoursPerDayField.setText(person.getHoursPerDay().toString());
		this.efficencyField.setText(person.getEfficiency().toString());
		this.roleTable.setItems(person.getRoles());
	}
	
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}

	public boolean isOkClicked() {
		return okClicked;
	}
	
	@FXML
	private void handleRemoveRole() {
		int selectedIndex = roleTable.getSelectionModel().getSelectedIndex();
		if (selectedIndex >= 0) {
			roleTable.getItems().remove(selectedIndex);
		} else {
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(this.dialogStage);
			alert.setTitle("No selection");
			alert.setHeaderText("No role selected");
			alert.setContentText("Please select a role in the table");
			alert.showAndWait();
		}
	}
	
	@FXML
	private void handleNewRole() {
		RoleData selectedRole = this.showRoleSelectionDialog();
		if (selectedRole != null) {
			this.roleTable.getItems().add(selectedRole);
		}
	}

	private RoleData showRoleSelectionDialog() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/RoleSelectionDialog.fxml"));
			AnchorPane page = (AnchorPane) loader.load();
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Select Role");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(this.mainApp.getPrimaryStage());
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);
			RoleSelectionDialogController controller = loader.getController();
			controller.setDialogStage(dialogStage);
			controller.setAvailableRolesToSelect(this.findAvailableRolesToSelect());
			dialogStage.showAndWait();
			return controller.getSelectedRole();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	private ObservableList<RoleData> findAvailableRolesToSelect() {
		ObservableList<RoleData> availableRoles = FXCollections.observableArrayList();
		for (RoleData role : this.mainApp.getRoleData()) {
			if (!roleIsInRoleTable(role)) {
				availableRoles.add(role);
			}
		}
		return availableRoles;
	}

	private boolean roleIsInRoleTable(RoleData role) {
		for (RoleData asignedRole :  this.roleTable.getItems()) {
			if (role.equals(asignedRole)) {
				return true;
			}
		}
		return false;
	}

	@FXML
	private void handleOK() {
		if (validateInput()) {
			person.setFirstName(firstNameField.getText());
			person.setLastName(lastNameField.getText());
			person.setHoursPerDay(Integer.parseInt(hoursPerDayField.getText()));
			person.setEfficiency(Float.parseFloat(this.efficencyField.getText()));
			person.setRoles(roleTable.getItems());
			okClicked = true;
			dialogStage.close();
		}
	}

	@FXML
	private void handleCancel() {
		dialogStage.close();
	}

	private boolean validateInput() {
		String errorMessage = "";
		if (firstNameField.getText() == null || firstNameField.getText().trim().length() == 0) {
			errorMessage = "No valid first name! \n";
		}
		if (lastNameField.getText() == null || lastNameField.getText().trim().length() == 0) {
			errorMessage = "No valid last name! \n";
		}
		if (!IntegerUtils.isInt(hoursPerDayField.getText())) {
			errorMessage = "No valid hours per day";
		}
		if (FloatValidator.getInstance().validate(efficencyField.getText()) == null) {
			errorMessage = "No valid efficiency";
		}
		if (errorMessage.length() == 0) {
			return true;
		} else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.initOwner(dialogStage);
			alert.setTitle("Invalid Fields");
			alert.setHeaderText("Please correct invalid fields");
			alert.setContentText(errorMessage);
			alert.showAndWait();
			return false;
		}
	}
}
