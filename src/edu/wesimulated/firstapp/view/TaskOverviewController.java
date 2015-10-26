package edu.wesimulated.firstapp.view;

import edu.wesimulated.firstapp.MainApp;
import edu.wesimulated.firstapp.model.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;

public class TaskOverviewController {
	@FXML
	private TableView<Task> taskTable;
	@FXML
	private TableColumn<Task, String> nameColumn;
	@FXML
	private TableColumn<Task, Integer> unitsOfWorkColumn;

	@FXML
	private Label nameLabel;
	@FXML
	private Label unitsOfWorkLabel;
	
	private MainApp mainApp;
	
	public TaskOverviewController() {
	}

	private void showTaskDetails(Task task) {
		if (task != null) {
			nameLabel.setText(task.getName());
			unitsOfWorkLabel.setText(task.getUnitsOfWork().toString());
		} else {
			nameLabel.setText("");
			unitsOfWorkLabel.setText("");
		}
	}

	@FXML
	private void handleDeleteTask() {
		int selectedIndex = taskTable.getSelectionModel().getSelectedIndex();
		if (selectedIndex >= 0) {
			this.taskTable.getItems().remove(selectedIndex);
		} else {
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(mainApp.getPrimaryStage());
			alert.setTitle("No Selection");
			alert.setHeaderText("No task selected");
			alert.setContentText("Please select a task in the table");
			alert.showAndWait();
		}
	}

	@FXML
	private void handleNewTask() {
		Task tempTask = new Task();
		boolean okClicked = mainApp.showTaskEditDialog(tempTask);
		if (okClicked) {
			this.mainApp.getTaskData().add(tempTask);
		}
	}

	@FXML
	private void handleEditTask() {
		Task selectedTask = taskTable.getSelectionModel().getSelectedItem();
		if (selectedTask != null) {
			boolean okClicked = mainApp.showTaskEditDialog(selectedTask);
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
	
	public void initialize() {
		nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
		unitsOfWorkColumn.setCellValueFactory(cellData -> cellData.getValue().unitsOfWorkProperty().asObject());
		showTaskDetails(null);
		taskTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> showTaskDetails(newValue));
	}
	
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
		this.taskTable.setItems(mainApp.getTaskData());
	}

}
