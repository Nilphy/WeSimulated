package edu.wesimulated.firstapp.view;

import java.time.LocalDate;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import com.wesimulated.simulationmotor.DateUtils;

import edu.wesimulated.firstapp.MainApp;
import edu.wesimulated.firstapp.model.ProjectData;

public class ProjectEditController {

	@FXML
	private TextField nameField;
	@FXML
	private DatePicker startDatePicker;
	@FXML
	private DatePicker endDatePicker;

	private Stage dialogStage;
	private ProjectData project;
	private boolean okClicked;

	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}

	public void setProject(ProjectData projectData) {
		this.project = projectData;
		this.nameField.setText(projectData.getName());
		this.startDatePicker.setValue(DateUtils.asLocalDate(projectData.getStartDate()));
		this.endDatePicker.setValue(DateUtils.asLocalDate(projectData.getEndDate()));
	}

	public void setDateFormatter(StringConverter<LocalDate> converter) {
		startDatePicker.setValue(LocalDate.now());
		startDatePicker.setConverter(converter);
		startDatePicker.setPromptText(MainApp.DATE_PATTERN.toLowerCase());
		endDatePicker.setValue(LocalDate.now());
		endDatePicker.setConverter(converter);
		endDatePicker.setPromptText(MainApp.DATE_PATTERN.toLowerCase());
	}

	public boolean isOkClicked() {
		return this.okClicked;
	}

	@FXML
	private void handleOk() {
		if (this.validateInput()) {
			this.project.setName(this.nameField.getText());
			this.project.setStartDate(DateUtils.asDate(this.startDatePicker.getValue()));
			this.project.setEndDate(DateUtils.asDate(this.endDatePicker.getValue()));
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
