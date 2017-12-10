package edu.wesimulated.firstapp.view;

import java.util.List;
import java.util.stream.Collectors;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.stage.Stage;
import edu.wesimulated.firstapp.model.TaskNeedType;
import edu.wesimulated.firstapp.model.TaskNeedTypeSelectionRow;

public class TaskNeedSelectionDialogController {
	@FXML
	private TableView<TaskNeedTypeSelectionRow> taskNeedTable;
	@FXML
	private TableColumn<TaskNeedTypeSelectionRow, String> taskNeedNameColumn;
	@FXML
	private TableColumn<TaskNeedTypeSelectionRow, Boolean> isSelectedColumn;

	private Stage dialogStage;
	private TaskNeedHolder taskNeedHolder;

	@FXML
	private void initialize() {
		taskNeedNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTaskNeedType().toString()));
		isSelectedColumn.setCellFactory(CheckBoxTableCell.forTableColumn(isSelectedColumn));
		isSelectedColumn.setEditable(true);
		isSelectedColumn.setCellValueFactory(cellData -> cellData.getValue().isSelectedProperty());
	}

	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}

	@FXML
	private void handleOK() {
		if (validateInput()) {
			this.taskNeedHolder.addSelectedTaskNeeds(this.taskNeedTable.getSelectionModel().getSelectedItems().stream()
					.map((taskNeedSelectionRow) -> taskNeedSelectionRow.getTaskNeedType())
					.collect(Collectors.toList()));
			this.dialogStage.close();
		}
	}

	@FXML
	private void handleCancel() {
		dialogStage.close();
	}

	private boolean validateInput() {
		String errorMessage = "";
		if (this.taskNeedTable.getSelectionModel().getSelectedItem() == null) {
			errorMessage = "No task need selected! \n";
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

	public void setTaskNeedsToSelect(List<TaskNeedType> allTaskNeedsWithoutAlreadySelected) {
		ObservableList<TaskNeedTypeSelectionRow> newSelectionRows = FXCollections.observableArrayList();
		allTaskNeedsWithoutAlreadySelected.forEach((taskNeedData) -> {
			TaskNeedTypeSelectionRow newRow = new TaskNeedTypeSelectionRow();
			newRow.setTaskNeedType(taskNeedData);
			newSelectionRows.add(newRow);
		});
		this.taskNeedTable.setItems(newSelectionRows);
	}

	public void setTaskNeedHolder(TaskNeedHolder taskNeedHolder) {
		this.taskNeedHolder = taskNeedHolder;
	}
}
