package edu.wesimulated.firstapp.view;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.util.Callback;
import edu.wesimulated.firstapp.MainApp;
import edu.wesimulated.firstapp.model.PrecedenceType;
import edu.wesimulated.firstapp.model.TaskData;
import edu.wesimulated.firstapp.model.TaskDependency;
import edu.wesimulated.firstapp.model.TaskNetNode;

public class TaskNetController {
	@FXML
	private TableView<TaskData> taskTable;
	@FXML
	private TableColumn<TaskData, String> nameColumn;
	@FXML
	private TreeView<TaskDependency> taskDependenciesTree;

	private TaskNetNode taskNetRoot;
	private TaskData selectedTask;
	private MainApp mainApp;

	public TaskNetController() {
	}

	public void initialize() {
		nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
		taskTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> this.selectedTask = newValue);
	}

	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
		this.taskTable.setItems(this.mainApp.getTaskData());
		this.fillTaskNetTree(this.mainApp.getTaskData());
	}

	private void fillTaskNetTree(ObservableList<TaskData> tasks) {
		TaskDependency dummyDependency = new TaskDependency();
		TreeItem<TaskDependency> rootItem = dummyDependency.buildDummyTreeItem();
		rootItem.setExpanded(true);
		for (TaskData task : tasks) {
			TaskDependency taskAsRoot = new TaskDependency(task, PrecedenceType.IndependentTask);
			TreeItem<TaskDependency> taskNode = taskAsRoot.buildTreeItem();
			fillTaskNetChildren(task, taskNode);
			rootItem.getChildren().add(taskNode);
		}
		taskDependenciesTree.setRoot(rootItem);
		taskDependenciesTree.setEditable(false);
		taskDependenciesTree.setShowRoot(false);
		taskDependenciesTree.setCellFactory(new Callback<TreeView<TaskDependency>, TreeCell<TaskDependency>>() {
			@Override
			public TreeCell<TaskDependency> call(TreeView<TaskDependency> param) {
				return new EditableTreeCellTaskNet();
			}
		});
	}

	private void fillTaskNetChildren(TaskData task, TreeItem<TaskDependency> treeNode) {
		for (TaskDependency childNode : task.getTaskDependencies()) {
			TreeItem<TaskDependency> treeChildNode = childNode.buildTreeItem();
			treeNode.getChildren().add(treeChildNode);
		}
	}

	public TaskNetNode getTaskNetRoot() {
		return taskNetRoot;
	}

	public void setTaskNetRoot(TaskNetNode taskNetRoot) {
		this.taskNetRoot = taskNetRoot;
	}

	public class EditableTreeCellTaskNet extends TreeCell<TaskDependency> {

		private ContextMenu operationsMenu;

		public EditableTreeCellTaskNet() {
		}

		@Override
		public void updateItem(TaskDependency item, boolean empty) {
			super.updateItem(item, empty);
			if (empty) {
				setText(null);
				setGraphic(null);
			} else {
				setText(getTreeItem().getValue().toString());
				setGraphic(getTreeItem().getGraphic());
				setContextMenu(this.getOperationsMenu());
			}
		}

		private ContextMenu getOperationsMenu() {
			if (this.operationsMenu == null) {
				this.operationsMenu = new ContextMenu();
				addNewTaskDependencyOptionToMenu(PrecedenceType.FinishedToFinish);
				addNewTaskDependencyOptionToMenu(PrecedenceType.FinishedToStart);
				addNewTaskDependencyOptionToMenu(PrecedenceType.StartedToFinish);
				addNewTaskDependencyOptionToMenu(PrecedenceType.StartedToStart);
				addChangeTaskDependencyOptionToMenu(PrecedenceType.FinishedToFinish);
				addChangeTaskDependencyOptionToMenu(PrecedenceType.FinishedToStart);
				addChangeTaskDependencyOptionToMenu(PrecedenceType.StartedToFinish);
				addChangeTaskDependencyOptionToMenu(PrecedenceType.StartedToStart);
				addRemoveDependencyToMenu();
			}
			return this.operationsMenu;
		}

		private void addNewTaskDependencyOptionToMenu(PrecedenceType precedenceType) {
			MenuItem menuItem = new MenuItem("Add " + precedenceType.getImageName() + " dependency to task");
			operationsMenu.getItems().add(menuItem);
			menuItem.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					TaskDependency newDependencyNode = new TaskDependency(selectedTask, precedenceType);
					if (this.validateIfSelectedTaskCouldBeAddedToDependencyOfNode(newDependencyNode)) {
						getTreeItem().getValue().getTask().getTaskDependencies().add(newDependencyNode);
						mainApp.getTaskNet().addNewDependencyToTask(getTreeItem().getValue().getTask(), newDependencyNode);
						getTreeItem().getChildren().add(newDependencyNode.buildTreeItem());
					}
				}

				private boolean validateIfSelectedTaskCouldBeAddedToDependencyOfNode(TaskDependency value) {
					String errorMessage = null;
					String errorSolution = null;
					if (selectedTask == null) {
						errorMessage = "No task selected";
						errorSolution = "Select a task to asign as a dependency";
					}
					if (mainApp.getTaskNet().validateIfDependencyCouldBeAddedToTask(getTreeItem().getValue().getTask(), value)) {
						errorMessage = "This task already is already a dependency or a dependent of this task";
						errorSolution = "Free the task first if you whant to add it as a dependency";
					}
					if (errorMessage != null) {
						Alert alert = new Alert(AlertType.ERROR);
						alert.initOwner(mainApp.getPrimaryStage());
						alert.setTitle("Error creating dependency");
						alert.setHeaderText(errorMessage);
						alert.setContentText(errorSolution + "\n");
						alert.showAndWait();
						return false;
					}
					return true;
				}

			});
		}

		private void addChangeTaskDependencyOptionToMenu(PrecedenceType precedenceType) {
			MenuItem menuItem = new MenuItem("Change to " + precedenceType + " dependency");
			operationsMenu.getItems().add(menuItem);
			menuItem.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					getTreeItem().getValue().setPrecedence(precedenceType);
					getTreeItem().setGraphic(getTreeItem().getValue().buildNewIcon());
				}
			});
		}

		private void addRemoveDependencyToMenu() {
			MenuItem menuItem = new MenuItem("Remove dependency");
			operationsMenu.getItems().add(menuItem);
			menuItem.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					mainApp.getTaskNet().removeDependency(getTreeItem().getParent().getValue().getTask(), getTreeItem().getValue());
					getTreeItem().getParent().getValue().getTask().getTaskDependencies().remove(getTreeItem().getValue());
					getTreeItem().getParent().getChildren().remove(getTreeItem());
				}
			});
		}
	}
}
