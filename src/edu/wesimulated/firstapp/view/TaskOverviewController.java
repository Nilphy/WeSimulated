package edu.wesimulated.firstapp.view;

import java.io.IOException;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
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

import com.wesimulated.simulationmotor.DateUtils;

import edu.wesimulated.firstapp.MainApp;
import edu.wesimulated.firstapp.model.PersonData;
import edu.wesimulated.firstapp.model.RaciType;
import edu.wesimulated.firstapp.model.TaskData;
import edu.wesimulated.firstapp.model.TaskPeopleAssignmentRow;

public class TaskOverviewController {
	@FXML
	private TableView<TaskData> taskTable;
	@FXML
	private TableColumn<TaskData, String> nameColumn;
	@FXML
	private TableColumn<TaskData, Integer> unitsOfWorkColumn;
	@FXML
	private Label nameLabel;
	@FXML
	private Label unitsOfWorkLabel;
	@FXML
	private Label startDate;
	@FXML
	private Label endDate;
	@FXML
	private TableView<TaskPeopleAssignmentRow> taskPeopleAssignmentTable;
	@FXML
	private TableColumn<TaskPeopleAssignmentRow, String> raciColumn;
	@FXML
	private TableColumn<TaskPeopleAssignmentRow, String> personColumn;

	private MainApp mainApp;

	public TaskOverviewController() {
	}

	private void showTaskDetails(TaskData task) {
		if (task != null) {
			nameLabel.setText(task.getName());
			unitsOfWorkLabel.setText(task.getUnitsOfWork().toString());
			startDate.setText(this.mainApp.getConverter().toString(DateUtils.asLocalDate(task.getStartDate())));
			endDate.setText(this.mainApp.getConverter().toString(DateUtils.asLocalDate(task.getEndDate())));
			populateTaskPeopleAssignmentsTable(task);
		} else {
			nameLabel.setText("");
			unitsOfWorkLabel.setText("");
			startDate.setText("");
			endDate.setText("");
		}
	}

	@FXML
	private void handleDeleteTask() {
		int selectedIndex = taskTable.getSelectionModel().getSelectedIndex();
		if (selectedIndex >= 0) {
			TaskData itemToDelete = this.taskTable.getItems().get(selectedIndex);
			if (mainApp.getWbs().containsTask(itemToDelete)) {
				alertError("Delete forbidden", "Task in WBS", "Plase unlink from WBS first");
			} else if (!mainApp.getTaskNet().validateIfTaskCouldBeDeleted(itemToDelete)) {
				alertError("Delete forbidden", "Task has dependencies in task net", "Please remove dependencies first from task net");
			} else {
				this.taskTable.getItems().remove(selectedIndex);
			}
		} else {
			alertError("No Selection", "No task selected", "Please select a task in the table");
		}
	}

	private void alertError(String title, String header, String text) {
		Alert alert = new Alert(AlertType.WARNING);
		alert.initOwner(mainApp.getPrimaryStage());
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(text);
		alert.showAndWait();
	}

	@FXML
	private void handleNewTask() {
		TaskData tempTask = new TaskData();
		boolean okClicked = this.showTaskEditDialog(tempTask);
		if (okClicked) {
			tempTask.assingId();
			this.mainApp.getTaskData().add(tempTask);
			this.mainApp.getTaskNet().addNetTaskToNet(tempTask);
		}
	}

	@FXML
	private void handleEditTask() {
		TaskData selectedTask = taskTable.getSelectionModel().getSelectedItem();
		if (selectedTask != null) {
			boolean okClicked = this.showTaskEditDialog(selectedTask);
			if (okClicked) {
				showTaskDetails(selectedTask);
			}
		} else {
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(mainApp.getPrimaryStage());
			alert.setTitle("No selection");
			alert.setHeaderText("No task selected");
			alert.setContentText("Please select a person in the table");
			alert.showAndWait();
		}
	}

	public boolean showTaskEditDialog(TaskData task) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/TaskEditDialog.fxml"));
			AnchorPane page = (AnchorPane) loader.load();
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Edit Task");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(this.mainApp.getPrimaryStage());
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);
			TaskEditController controller = loader.getController();
			controller.setDialogStage(dialogStage);
			controller.setDateFormatter(this.mainApp.getConverter());
			controller.setTask(task);
			controller.setMainApp(this.mainApp);
			dialogStage.showAndWait();
			return controller.isOkClicked();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	private void populateTaskPeopleAssignmentsTable(TaskData taskData) {
		this.taskPeopleAssignmentTable.getItems().clear();
		populateTaskPeopleAssignmentsTableOfRaciType(RaciType.Responsible, taskData.getResponsiblePeople());
		populateTaskPeopleAssignmentsTableOfRaciType(RaciType.Accountable, taskData.getAccountablePeople());
		populateTaskPeopleAssignmentsTableOfRaciType(RaciType.Consulted, taskData.getConsultedPeople());
		populateTaskPeopleAssignmentsTableOfRaciType(RaciType.Informed, taskData.getInformedPeople());
	}

	private void populateTaskPeopleAssignmentsTableOfRaciType(RaciType raciType, ObservableList<PersonData> people) {
		for (PersonData person : people) {
			TaskPeopleAssignmentRow newRow = new TaskPeopleAssignmentRow();
			newRow.setPerson(person);
			newRow.setRaciType(raciType);
			this.taskPeopleAssignmentTable.getItems().add(newRow);
		}
	}

	public void initialize() {
		nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
		unitsOfWorkColumn.setCellValueFactory(cellData -> cellData.getValue().unitsOfWorkProperty().asObject());
		raciColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getRaciType().toString()));
		personColumn.setCellValueFactory(cellData -> cellData.getValue().getPerson().firstNameProperty());
		showTaskDetails(null);
		taskTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> showTaskDetails(newValue));
	}

	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
		this.taskTable.setItems(mainApp.getTaskData());
	}
}
