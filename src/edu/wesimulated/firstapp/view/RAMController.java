package edu.wesimulated.firstapp.view;

import java.util.HashMap;
import java.util.Map;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import edu.wesimulated.firstapp.MainApp;
import edu.wesimulated.firstapp.model.RaciType;
import edu.wesimulated.firstapp.model.RamRow;
import edu.wesimulated.firstapp.model.ResponsibilityAssignmentData;
import edu.wesimulated.firstapp.model.RoleData;

public class RAMController {

	@FXML
	private TableView<RamRow> responsibilityAssignmentTable;
	private MainApp mainApp;

	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
		this.fillRamWithProjectData();
	}

	private void fillRamWithProjectData() {
		TableColumn<RamRow, String> taskNameColumn = new TableColumn<>("RAM");
		taskNameColumn.setEditable(false);
		taskNameColumn.setCellValueFactory(row -> row.getValue().getTask().nameProperty());
		this.responsibilityAssignmentTable.getColumns().add(taskNameColumn);
		for (RoleData role : this.mainApp.getRoleData()) {
			TableColumn<RamRow, Boolean> roleColumn = new TableColumn<>(role.getName());
			roleColumn.setEditable(true);
			TableColumn<RamRow, Boolean> responsibleColumn = new TableColumn<>(RaciType.Responsible.toString());
			responsibleColumn.setCellFactory(CheckBoxTableCell.forTableColumn(responsibleColumn));
			responsibleColumn.setCellValueFactory(row -> row.getValue().findByRole(role).responsibleProperty());
			responsibleColumn.setEditable(true);
			roleColumn.getColumns().add(responsibleColumn);
			TableColumn<RamRow, Boolean> accountableColumn = new TableColumn<>(RaciType.Accountable.toString());
			accountableColumn.setCellFactory(CheckBoxTableCell.forTableColumn(accountableColumn));
			accountableColumn.setCellValueFactory(row -> row.getValue().findByRole(role).accountableProperty());
			accountableColumn.setEditable(true);
			roleColumn.getColumns().add(accountableColumn);
			TableColumn<RamRow, Boolean> consultedColumn = new TableColumn<>(RaciType.Consulted.toString());
			consultedColumn.setCellFactory(CheckBoxTableCell.forTableColumn(consultedColumn));
			consultedColumn.setCellValueFactory(row -> row.getValue().findByRole(role).consultedProperty());
			consultedColumn.setEditable(true);
			roleColumn.getColumns().add(consultedColumn);
			TableColumn<RamRow, Boolean> informedColumn = new TableColumn<>(RaciType.Informed.toString());
			informedColumn.setCellFactory(CheckBoxTableCell.forTableColumn(informedColumn));
			informedColumn.setCellValueFactory(row -> row.getValue().findByRole(role).informedProperty());
			informedColumn.setEditable(true);
			roleColumn.getColumns().add(informedColumn);
			this.responsibilityAssignmentTable.getColumns().add(roleColumn);
		}
		this.responsibilityAssignmentTable.setItems(this.toRows(this.mainApp.buildResponsibilityAssignmentData()));
	}

	private ObservableList<RamRow> toRows(ObservableList<ResponsibilityAssignmentData> responsibilityAssignmentData) {
		Map<Integer, RamRow> map = new HashMap<>();
		ObservableList<RamRow> convertedList = FXCollections.observableArrayList();
		for (ResponsibilityAssignmentData column : responsibilityAssignmentData) {
			if (!map.containsKey(column.getTask().getId())) {
				RamRow newRow = new RamRow();
				newRow.setTask(column.getTask());
				ObservableList<ResponsibilityAssignmentData> columns = FXCollections.observableArrayList(); 
				newRow.setColumns(columns);
				map.put(column.getTask().getId(), newRow);
				convertedList.add(newRow);
			}
			map.get(column.getTask().getId()).getColumns().add(column);
		}
		return convertedList;
	}
}
