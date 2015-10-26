package edu.wesimulated.firstapp.view;

import com.javacommon.utils.IntegerUtils;

import edu.wesimulated.firstapp.model.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class TaskEditController {

	@FXML
	private TextField nameField;
	@FXML
	private TextField unitsOfWorkField;
	private Stage dialogStage;
	private Task task;
	private boolean okClicked;

	@FXML
	private void initialize() {
	}

	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}
	
	public void setTask(Task task) {
		this.task = task;
	}

	public boolean isOkClicked() {
		return this.okClicked;
	}

	@FXML
	private void handleOk() {
		if (this.validateInput()) {
			this.task.setName(this.nameField.getText());
			this.task.setUnitsOfWork(Integer.parseInt(unitsOfWorkField.getText()));
			this.okClicked = true;
			dialogStage.close();
		}
	}

	@FXML
	private void handleCancel() {
		dialogStage.close();
	}

	private boolean validateInput() {
		String errorMessage = "";
		if (nameField.getText() == null || nameField.getText().trim().length() == 0) {
			errorMessage = "No valid name! \n";
		}
		if (!IntegerUtils.isInt(unitsOfWorkField.getText())) {
			errorMessage = "No valid units of work!";
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
