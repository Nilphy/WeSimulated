package edu.wesimulated.firstapp.view;

import java.util.Collection;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import edu.wesimulated.firstapp.model.InvalidRaciTypeException;
import edu.wesimulated.firstapp.model.PersonData;
import edu.wesimulated.firstapp.model.RaciType;

public class PeopleSelectionDialogController {
	@FXML
	private ComboBox<PersonData> responsibleCombo;
	@FXML
	private ComboBox<PersonData> accountableCombo;
	@FXML
	private ComboBox<PersonData> consultedCombo;
	@FXML
	private ComboBox<PersonData> informedCombo;

	private Stage dialogStage;
	private TaskEditController taskEditController;

	@FXML
	private void initialize() {
	}

	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}

	@FXML
	private void handleClose() {
		this.dialogStage.close();
	}

	@FXML
	private void handleResponsiblePeopleAdd() {
		this.taskEditController.addPersonOfRaciType(this.responsibleCombo.getValue(), RaciType.Responsible);
	}

	@FXML
	private void handleAccountablePeopleAdd() {
		this.taskEditController.addPersonOfRaciType(this.accountableCombo.getValue(), RaciType.Accountable);
	}

	@FXML
	private void handleConsultedPeopleAdd() {
		this.taskEditController.addPersonOfRaciType(this.consultedCombo.getValue(), RaciType.Consulted);
	}

	@FXML
	private void handleInformedPeopleAdd() {
		this.taskEditController.addPersonOfRaciType(this.informedCombo.getValue(), RaciType.Informed);
	}

	public void setTaskEditController(TaskEditController taskEditController) {
		this.taskEditController = taskEditController;
	}

	public void setCandidatesOfRaciType(Collection<PersonData> candidates, RaciType raciType) {
		ObservableList<PersonData> observableCandidates = FXCollections.observableArrayList(candidates);
		switch (raciType) {
		case Responsible:
			this.responsibleCombo.setItems(observableCandidates);
			break;
		case Accountable:
			this.accountableCombo.setItems(observableCandidates);
			break;
		case Consulted:
			this.consultedCombo.setItems(observableCandidates);
			break;
		case Informed:
			this.informedCombo.setItems(observableCandidates);
			break;
		default:
			throw new InvalidRaciTypeException();
		}
	}
}
