package edu.wesimulated.firstapp.view;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Collection;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import com.javacommon.utils.IntegerUtils;
import com.wesimulated.simulationmotor.DateUtils;

import edu.wesimulated.firstapp.MainApp;
import edu.wesimulated.firstapp.model.InvalidRaciTypeException;
import edu.wesimulated.firstapp.model.PersonData;
import edu.wesimulated.firstapp.model.RaciType;
import edu.wesimulated.firstapp.model.RoleData;
import edu.wesimulated.firstapp.model.TaskData;
import edu.wesimulated.firstapp.model.TaskPeopleAssignmentRow;

/**
 * 
 * Una tarea se caracteriza por:
 * - tipo y cantidad de trabajo que implica
 * - personas asignadas
 * - fecha de comienzo y fin
 * 
 * Y luego por las relaciones que tiene con el proyecto y con otras tareas.
 * En esta pantalla solamente se pueden ingresar los dos items mencionados.
 * 
 * Las personas que se podrán relacionar con esta tarea serán aquellas que tengan los roles
 * asignados a la tarea en la tabla RACI.
 * 
 * @author Carolina
 *
 */
public class TaskEditController {

	@FXML
	private TextField nameField;
	@FXML
	private TextField unitsOfWorkField;
	@FXML
	private DatePicker startDatePicker;
	@FXML
	private DatePicker endDatePicker;
	@FXML
	private TableView<TaskPeopleAssignmentRow> taskPeopleAssignmentTable;
	@FXML
	private TableColumn<TaskPeopleAssignmentRow, String> raciColumn;
	@FXML
	private TableColumn<TaskPeopleAssignmentRow, String> personColumn;
	@FXML
	private TableColumn<TaskPeopleAssignmentRow, Boolean> isSelectedColumn;

	private Stage dialogStage;
	private TaskData task;
	private boolean okClicked;
	private MainApp mainApp;

	@FXML
	private void initialize() {
		raciColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getRaciType().toString()));
		personColumn.setCellValueFactory(cellData -> cellData.getValue().getPerson().firstNameProperty());
		isSelectedColumn.setCellFactory(CheckBoxTableCell.forTableColumn(isSelectedColumn));
		isSelectedColumn.setEditable(true);
		isSelectedColumn.setCellValueFactory(cellData -> cellData.getValue().isSelectedProperty());
	}

	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}

	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}

	public void setTask(TaskData task) {
		this.task = task;
		this.nameField.setText(task.getName());
		this.unitsOfWorkField.setText(task.getUnitsOfWork().toString());
		this.startDatePicker.setValue(DateUtils.asLocalDate(task.getStartDate()));
		this.endDatePicker.setValue(DateUtils.asLocalDate(task.getEndDate()));
		this.populateTaskPeopleAssignmentsTable();
	}

	public boolean isOkClicked() {
		return this.okClicked;
	}

	private void populateTaskPeopleAssignmentsTable() {
		this.taskPeopleAssignmentTable.getItems().clear();
		populateTaskPeopleAssignmentsTableOfRaciType(RaciType.Responsible, this.task.getResponsiblePeople());
		populateTaskPeopleAssignmentsTableOfRaciType(RaciType.Accountable, this.task.getAccountablePeople());
		populateTaskPeopleAssignmentsTableOfRaciType(RaciType.Consulted, this.task.getConsultedPeople());
		populateTaskPeopleAssignmentsTableOfRaciType(RaciType.Informed, this.task.getInformedPeople());
	}

	private void populateTaskPeopleAssignmentsTableOfRaciType(RaciType raciType, ObservableList<PersonData> people) {
		for (PersonData person : people) {
			TaskPeopleAssignmentRow newRow = new TaskPeopleAssignmentRow();
			newRow.setPerson(person);
			newRow.setRaciType(raciType);
			this.taskPeopleAssignmentTable.getItems().add(newRow);
		}
	}

	public void addPersonOfRaciType(PersonData person, RaciType raciType) {
		switch (raciType) {
		case Responsible:
			this.task.getResponsiblePeople().add(person);
			break;
		case Accountable:
			this.task.getAccountablePeople().add(person);
			break;
		case Consulted:
			this.task.getConsultedPeople().add(person);
			break;
		case Informed:
			this.task.getInformedPeople().add(person);
			break;
		default:
			throw new InvalidRaciTypeException();
		}
		this.populateTaskPeopleAssignmentsTable();
	}

	@FXML
	private void handleOk() {
		if (this.validateInput()) {
			this.task.setName(this.nameField.getText());
			this.task.setUnitsOfWork(Integer.parseInt(unitsOfWorkField.getText()));
			this.task.setStartDate(DateUtils.asDate(this.startDatePicker.getValue()));
			this.task.setEndDate(DateUtils.asDate(this.endDatePicker.getValue()));
			this.task.resetAllPeopleAssignations();
			for (TaskPeopleAssignmentRow peopleAssignationRow : this.taskPeopleAssignmentTable.getItems()) {
				this.addPersonOfRaciType(peopleAssignationRow.getPerson(), peopleAssignationRow.getRaciType());
			}
			this.okClicked = true;
			dialogStage.close();
		}
	}

	@FXML
	private void handleRemovePeopleAssignmentRows() {
		this.taskPeopleAssignmentTable.getItems().removeIf(item -> item.isSelectedProperty().get());
	}

	@FXML
	private void handleCancel() {
		dialogStage.close();
	}

	@FXML
	private void handleAddNewPeopleAssignations() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/PeopleSelectionDialog.fxml"));
			AnchorPane page = (AnchorPane) loader.load();
			Stage dialogStage = new Stage();
			dialogStage.setTitle("People selection");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(this.mainApp.getPrimaryStage());
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);
			PeopleSelectionDialogController controller = loader.getController();
			controller.setDialogStage(dialogStage);
			controller.setTaskEditController(this);
			for (RaciType raciType : RaciType.values()) {
				controller.setCandidatesOfRaciType(this.findCandidatesOfRaciType(raciType), raciType);
			}
			dialogStage.showAndWait();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private Collection<PersonData> findCandidatesOfRaciType(RaciType raciType) {
		Collection<RoleData> roles = this.mainApp.findRolesOfTaskAndRaciType(this.task, raciType);
		return this.mainApp.findPeopleWithRoles(roles);
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

	public void setDateFormatter(StringConverter<LocalDate> converter) {
		startDatePicker.setValue(LocalDate.now());
		startDatePicker.setConverter(converter);
		startDatePicker.setPromptText(MainApp.DATE_PATTERN.toLowerCase());
		endDatePicker.setValue(LocalDate.now());
		endDatePicker.setConverter(converter);
		endDatePicker.setPromptText(MainApp.DATE_PATTERN.toLowerCase());
	}

}
