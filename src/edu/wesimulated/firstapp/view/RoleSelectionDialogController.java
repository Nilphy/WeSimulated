package edu.wesimulated.firstapp.view;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import edu.wesimulated.firstapp.model.RoleData;

public class RoleSelectionDialogController {
	@FXML
	private TableView<RoleData> roleTable;
	@FXML
	private TableColumn<RoleData, String> roleNameColumn;	

	private boolean okClicked = false;
	private RoleData selectedRole = null;
	private Stage dialogStage;

	@FXML
	private void initialize() {
		roleNameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
	}

	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}

	public boolean isOkClicked() {
		return okClicked;
	}
	
	@FXML
	private void handleOK() {
		if (validateInput()) {
			this.selectedRole = this.roleTable.getSelectionModel().getSelectedItem();
			this.okClicked = true;
			this.dialogStage.close();
		}
	}

	@FXML
	private void handleCancel() {
		dialogStage.close();
	}

	private boolean validateInput() {
		String errorMessage = "";
		if (this.roleTable.getSelectionModel().getSelectedItem() == null) {
			errorMessage = "No role selected! \n";
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

	public void setAvailableRolesToSelect(ObservableList<RoleData> availableRolesToSelect) {
		this.roleTable.setItems(availableRolesToSelect);
	}

	public RoleData getSelectedRole() {
		return selectedRole;
	}
}
