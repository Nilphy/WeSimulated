package edu.wesimulated.firstapp.view;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import edu.wesimulated.firstapp.model.RoleData;

/**
 * 
 * Jefe de proyecto
 * Jefe de producto
 * Jefe funcional
 * Sponsor
 * Cliente
 * Especialistas: 
 * - Desarrollador
 * - Diseñador
 * - Analista
 * - QA
 * - QC
 * - Operaciones
 * 
 * @author Carolina
 *
 */
public class RoleEditController {

	@FXML
	private TextField nameField;
	@FXML
	private CheckBox highlyInterruptibleField;

	private Stage dialogStage;
	private RoleData rol;
	private boolean okClicked = false;

	@FXML
	private void initialize() {
		this.highlyInterruptibleField.setText("Highly interruptible role");
	}

	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}

	public void setRol(RoleData rol) {
		this.rol = rol;
		this.nameField.setText(rol.getName());
		this.highlyInterruptibleField.setSelected(role.highlyInterruptibleProperty().get());
	}

	public boolean isOkClicked() {
		return okClicked;
	}

	@FXML
	private void handleOK() {
		if (validateInput()) {
			rol.setName(nameField.getText());
			rol.setHighlyInterruptible(highlyInterruptibleField.isSelected());
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
		}
		return true;
	}
}
