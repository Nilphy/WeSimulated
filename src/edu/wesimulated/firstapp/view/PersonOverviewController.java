package edu.wesimulated.firstapp.view;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import edu.wesimulated.firstapp.MainApp;
import edu.wesimulated.firstapp.model.PersonData;

public class PersonOverviewController {
	@FXML
	private TableView<PersonData> personTable;
	@FXML
	private TableColumn<PersonData, String> firstNameColumn;
	@FXML
	private TableColumn<PersonData, String> lastNameColumn;
	
	@FXML
	private Label firstNameLabel;
	@FXML
	private Label lastNameLabel;
	@FXML
	private Label hoursPerDayLabel;
	@FXML
	private Label efficencyLabel;

	private MainApp mainApp;

	public PersonOverviewController() {
	}

	private void showPersonDetails(PersonData person) {
		if (person != null) {
			firstNameLabel.setText(person.getFirstName());
			lastNameLabel.setText(person.getLastName());
			hoursPerDayLabel.setText(person.getHoursPerDay().toString());
			efficencyLabel.setText(person.getEfficiency().toString());
		} else {
			firstNameLabel.setText("");
			lastNameLabel.setText("");
			hoursPerDayLabel.setText("");
			efficencyLabel.setText("");
		}
	}

	@FXML
	private void handleDeletePerson() {
		int selectedIndex = personTable.getSelectionModel().getSelectedIndex();
		if (selectedIndex >= 0) {
			personTable.getItems().remove(selectedIndex);
		} else {
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(mainApp.getPrimaryStage());
			alert.setTitle("No selection");
			alert.setHeaderText("No person selected");
			alert.setContentText("Please select a person in the table");
			alert.showAndWait();
		}
	}

	@FXML
	private void handleNewPerson() {
		PersonData tempPerson = new PersonData();
		boolean okClicked = mainApp.showPersonEditDialog(tempPerson);
		if (okClicked) {
			this.mainApp.getPersonData().add(tempPerson);
		}
	}

	@FXML
	private void handleEditPerson() {
		PersonData selectedPerson = personTable.getSelectionModel().getSelectedItem();
		if (selectedPerson != null) {
			boolean okClicked = mainApp.showPersonEditDialog(selectedPerson);
			if (okClicked) {
				showPersonDetails(selectedPerson);
			}
		} else {
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(mainApp.getPrimaryStage());
			alert.setTitle("No Selection");
			alert.setHeaderText("No Person Selected");
			alert.setContentText("Please select a person in the table");
			alert.showAndWait();
		}
	}
	
	@FXML
	private void initialize() {
		firstNameColumn.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
		lastNameColumn.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());
		showPersonDetails(null);
		personTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> showPersonDetails(newValue));
	}

	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
		personTable.setItems(mainApp.getPersonData());
	}
}
