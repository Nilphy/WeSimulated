package edu.wesimulated.firstapp.view;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import edu.wesimulated.firstapp.model.WbsInnerNode;

public class WbsNameEditorDialogController {

	@FXML
	private TextField nameField;

	private Stage dialogStage;
	private WbsInnerNode wbs;
	private boolean okClicked = false;

	@FXML
	private void initialize() {
	}

	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}

	public void setWbsRootNode(WbsInnerNode wbs) {
		this.wbs = wbs;
		this.nameField.setText(wbs.getName());
	}

	public boolean isOkClicked() {
		return this.okClicked;
	}

	@FXML
	private void handleOK() {
		if (validateInput()) {
			wbs.setName(nameField.getText());
			okClicked = true;
			dialogStage.close();
		}
	}

	@FXML
	private void handleCancel() {
		dialogStage.close();
	}

	private boolean validateInput() {
		if (nameField.getText() == null || nameField.getText().trim().length() == 0) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.initOwner(dialogStage);
			alert.setTitle("Invalid Fields");
			alert.setHeaderText("Please correct invalid fields");
			alert.setContentText("No valid name! \n");
			alert.showAndWait();
			return false;
		} else {
			return true;
		}
	}
}
