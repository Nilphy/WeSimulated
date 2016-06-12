package edu.wesimulated.firstapp.view;

import com.javacommon.utils.IntegerUtils;

import edu.wesimulated.firstapp.model.TaskData;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;

public class TaskEditController {

	@FXML
	private TextField nameField;
	@FXML
	private TextField unitsOfWorkField;
	@FXML
	private DatePicker startDatePicker;
	@FXML
	private DatePicker endDatePicker;
	private Stage dialogStage;
	private TaskData task;
	private boolean okClicked;

	@FXML
	private void initialize() {
	}

	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}
	
	public void setTask(TaskData task) {
		this.task = task;
		this.startDatePicker.setValue(task.getStartDate());
		this.endDatePicker.setValue(task.getEndDate());
	}

	public boolean isOkClicked() {
		return this.okClicked;
	}

	@FXML
	private void handleOk() {
		if (this.validateInput()) {
			this.task.setName(this.nameField.getText());
			this.task.setUnitsOfWork(Integer.parseInt(unitsOfWorkField.getText()));
			this.task.setStartDate(this.startDatePicker.getValue());
			this.task.setEndDate(this.endDatePicker.getValue());
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

	public void setDateFormatter(StringConverter<LocalDate> converter, String pattern) {
		startDatePicker.setValue(LocalDate.now());
		startDatePicker.setConverter(converter);
		startDatePicker.setPromptText(pattern.toLowerCase());
		endDatePicker.setValue(LocalDate.now());
		endDatePicker.setConverter(converter);
		endDatePicker.setPromptText(pattern.toLowerCase());
	}

}
