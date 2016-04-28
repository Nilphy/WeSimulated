package edu.wesimulated.firstapp.view;

import java.io.IOException;

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
import edu.wesimulated.firstapp.MainApp;
import edu.wesimulated.firstapp.model.TaskData;

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

	private MainApp mainApp;

	public TaskOverviewController() {
	}

	private void showTaskDetails(TaskData task) {
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
			TaskData itemToDelete = this.taskTable.getItems().get(selectedIndex);
			if (mainApp.getWbs().containsTask(itemToDelete)) {
				alertError("Delete forbidden", "Task in WBS", "Plase unlink from WBS first");
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
			controller.setTask(task);
			dialogStage.showAndWait();
			return controller.isOkClicked();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
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
