package edu.wesimulated.firstapp.view;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import edu.wesimulated.firstapp.MainApp;
import edu.wesimulated.firstapp.model.RoleData;

public class RoleOverviewController {
	@FXML
	private TableView<RoleData> roleTable;
	@FXML
	private TableColumn<RoleData, String> nameColumn;
	@FXML
	private Label nameLabel;
	@FXML
	private Label typeLabel;

	private MainApp mainApp;

	public RoleOverviewController() {
	}

	private void showRoleDetails(RoleData role) {
		if (role != null) {
			nameLabel.setText(role.getName());
			typeLabel.setText(role.isHighlyInterruptible() ? "Highly Interruptible" : "Normal");
		} else {
			nameLabel.setText("");
			typeLabel.setText("");
		}
	}

	@FXML
	private void handleDeleteRole() {
		int selectedIndex = roleTable.getSelectionModel().getSelectedIndex();
		if (selectedIndex >= 0) {
			roleTable.getItems().remove(selectedIndex);
		} else {
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(mainApp.getPrimaryStage());
			alert.setTitle("No selection");
			alert.setHeaderText("No role selected");
			alert.setContentText("Please select a role in the table");
			alert.showAndWait();
		}
	}

	@FXML
	private void handleNewRole() {
		RoleData tempRole = new RoleData();
		boolean okClicked = this.showRolEditDialog(tempRole);
		if (okClicked) {
			this.mainApp.getRoleData().add(tempRole);
		}
	}

	@FXML
	private void handleEditRole() {
		RoleData selectedRole = roleTable.getSelectionModel().getSelectedItem();
		if (selectedRole != null) {
			boolean okClicked = this.showRolEditDialog(selectedRole);
			if (okClicked) {
				showRoleDetails(selectedRole);
			}
		} else {
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(mainApp.getPrimaryStage());
			alert.setTitle("No Selection");
			alert.setHeaderText("No Role Selected");
			alert.setContentText("Please select a role in the table");
			alert.showAndWait();
		}
	}

	public boolean showRolEditDialog(RoleData role) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/RoleEditDialog.fxml"));
			AnchorPane page = (AnchorPane) loader.load();
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Edit Role");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(this.mainApp.getPrimaryStage());
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);
			RoleEditController controller = loader.getController();
			controller.setDialogStage(dialogStage);
			controller.setRole(role);
			dialogStage.showAndWait();
			return controller.isOkClicked();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	@FXML
	private void initialize() {
		nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
		showRoleDetails(null);
		roleTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> showRoleDetails(newValue));
	}

	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
		roleTable.setItems(mainApp.getRoleData());
	}
}
