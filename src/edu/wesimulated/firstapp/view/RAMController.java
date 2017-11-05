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

/**
 * 
 * Este es el controllador de la UI de la matriz de asignación de
 * responsabilidades.
 * 
 * La UI es una matriz cruzada, en la línea vertical están las tareas, mientras
 * que en la línea horizontal están los roles. Para cada rol existen cuatro
 * checkbox (RACI) para seleccionar si dicho rol tiene alguna relación con la
 * tarea correspondiente a la fila.
 * 
 * El la tabla RAM o RACI se dice cuales son las asignaciones de las tareas a
 * roles, pero no se especifica quien es la persona que va a realizar una tarea.
 * Las tareas se asignan a roles... pero dependiendo de la fase de evolución del
 * proyecto se puede llegar a saber cual es la/las personas específica que va a
 * realizarla.
 * 
 * En la UI de esta aplicación se puede ir a la edición de las tareas para
 * elegir a la persona específica que estará asignada a una tarea. Se podrá
 * seleccionar a aquellas que ejerzan los roles indicados en la tabla RACI para
 * dicha tarea.
 * 
 * Idea: Para ejecutar la simulación no hace falta que esté completa la
 * información sobre las personas específicas... pero si los roles. Luego
 * durante la simulación se puede determinar que persona ejecerá el rol.
 * 
 * @author Carolina
 *
 */
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
