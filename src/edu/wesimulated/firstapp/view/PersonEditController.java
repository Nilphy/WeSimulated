package edu.wesimulated.firstapp.view;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import org.apache.commons.validator.routines.FloatValidator;

import com.javacommon.utils.IntegerUtils;

import edu.wesimulated.firstapp.model.Person;

public class PersonEditController {

	@FXML
	private TextField firstNameField;
	@FXML
	private TextField lastNameField;
	@FXML
	private TextField hoursPerDayField;
	@FXML
	private TextField efficencyField;
	
	private Stage dialogStage;
	private Person person;
	private boolean okClicked = false;
	
	@FXML
	private void initialize() {
	}
	
	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}
	
	public void setPerson(Person person) {
		this.person = person;
		this.firstNameField.setText(person.getFirstName());
		this.lastNameField.setText(person.getLastName());
		this.hoursPerDayField.setText(person.getHoursPerDay().toString());
		this.efficencyField.setText(person.getEfficiency().toString());
	}
	
	public boolean isOkClicked() {
		return okClicked;
	}
	
	@FXML
	private void handleOK() {
		if (validateInput()) {
			person.setFirstName(firstNameField.getText());
			person.setLastName(lastNameField.getText());
			person.setHoursPerDay(Integer.parseInt(hoursPerDayField.getText()));
			person.setEfficiency(Float.parseFloat(this.efficencyField.getText()));
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
