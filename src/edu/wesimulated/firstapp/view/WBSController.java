package edu.wesimulated.firstapp.view;

import java.io.IOException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import edu.wesimulated.firstapp.MainApp;
import edu.wesimulated.firstapp.model.TaskData;
import edu.wesimulated.firstapp.model.WbsInnerNode;
import edu.wesimulated.firstapp.model.WbsLeafNode;
import edu.wesimulated.firstapp.model.WbsNode;

public class WBSController {
	@FXML
	private TableView<TaskData> taskTable;
	@FXML
	private TableColumn<TaskData, String> nameColumn;
	@FXML
	private TableColumn<TaskData, Integer> unitsOfWorkColumn;
	@FXML
	private TreeView<WbsNode> wbs;
	private TaskData selectedTask;
	private MainApp mainApp;

	public WBSController() {
	}

	public void initialize() {
		nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
		unitsOfWorkColumn.setCellValueFactory(cellData -> cellData.getValue().unitsOfWorkProperty().asObject());
		taskTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> this.selectedTask = newValue);
	}

	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
		this.taskTable.setItems(this.buildNewListOfItems(this.mainApp.getTaskData()));
		this.fillWbsTreeWithWbsData(this.mainApp.getWbs());
	}

	private ObservableList<TaskData> buildNewListOfItems(ObservableList<TaskData> taskData) {
		ObservableList<TaskData> newObservableList = FXCollections.observableArrayList();
		for (TaskData task : taskData) {
			if (!this.mainApp.getWbs().containsTask(task)) {
				newObservableList.add(task);
			}
		}
		return newObservableList;
	}

	private void fillWbsTreeWithWbsData(WbsInnerNode wbsData) {
		if (wbsData.getName() == null) {
			if (!showWbsNodeNameEditorDialog(this.mainApp.getWbs())) {
				wbsData.setName("NN project");
			}
		}
		TreeItem<WbsNode> rootItem = wbsData.buildTreeItem();
		rootItem.setExpanded(true);
		fillWbsChildren(wbsData, rootItem);
		wbs.setRoot(rootItem);
		wbs.setEditable(true);
		wbs.setCellFactory(new Callback<TreeView<WbsNode>, TreeCell<WbsNode>>() {
			@Override
			public TreeCell<WbsNode> call(TreeView<WbsNode> param) {
				return new TextFieldTreeCellWbs();
			}
		});
	}

	private void fillWbsChildren(WbsInnerNode wbsNode, TreeItem<WbsNode> treeNode) {
		for (WbsNode childNode : wbsNode.getChildrenWbsNodes()) {
			TreeItem<WbsNode> treeItem = childNode.buildTreeItem();
			if (childNode instanceof WbsInnerNode) {
				this.fillWbsChildren((WbsInnerNode) childNode, treeItem);
			}
			treeNode.getChildren().add(treeItem);
		}
	}

	private boolean showWbsNodeNameEditorDialog(WbsInnerNode wbsInnerNode) {
		try {
			FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("view/WbsNameEditorDialog.fxml"));
			AnchorPane page = (AnchorPane) loader.load();
			Stage dialogStage = new Stage();
			dialogStage.setTitle("WBS Name Editor");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(this.mainApp.getPrimaryStage());
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);
			WbsNameEditorDialogController controller = loader.getController();
			controller.setDialogStage(dialogStage);
			controller.setWbsRootNode(wbsInnerNode);
			dialogStage.showAndWait();
			return controller.isOkClicked();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	private void removeTaskFromTableView(TaskData selectedTask) {
		int index = findTaskInTable(selectedTask);
		this.taskTable.getItems().remove(index);
	}

	private void addTaskToTableView(TaskData removedTaskFromWbs) {
		this.taskTable.getItems().add(removedTaskFromWbs);
	}

	private int findTaskInTable(TaskData selectedTask) {
		int index = 0;
		for (TaskData element : this.taskTable.getItems()) {
			if (element.equals(selectedTask)) {
				return index;
			}
			index++;
		}
		return -1;
	}

	public class TextFieldTreeCellWbs extends TreeCell<WbsNode> {

		private TextField textField;
		private ContextMenu operationsMenu;

		public TextFieldTreeCellWbs() {
		}

		public ContextMenu getOperationsMenu() {
			if (this.operationsMenu == null) {
				this.operationsMenu = new ContextMenu();
				if (getTreeItem().getValue() instanceof WbsInnerNode) {
					MenuItem addWbsLeafNodeMenuItem = new MenuItem("Add task child");
					operationsMenu.getItems().add(addWbsLeafNodeMenuItem);
					addWbsLeafNodeMenuItem.setOnAction(new EventHandler<ActionEvent>() {
						@Override
						public void handle(ActionEvent event) {
							if (selectedTask != null) {
								WbsLeafNode wbsLeafNode = new WbsLeafNode(selectedTask);
								((WbsInnerNode) getTreeItem().getValue()).addChild(wbsLeafNode);
								removeTaskFromTableView(selectedTask);
								getTreeItem().getChildren().add(wbsLeafNode.buildTreeItem());
							} else {
								Alert alert = new Alert(AlertType.ERROR);
								alert.initOwner(mainApp.getPrimaryStage());
								alert.setTitle("Error creating new leaf");
								alert.setHeaderText("No task selected");
								alert.setContentText("Select a task to asign as a leaf! \n");
								alert.showAndWait();
							}
						}
					});
					MenuItem addWbsInnerNodeMenuItem = new MenuItem("Add WBS node child");
					operationsMenu.getItems().add(addWbsInnerNodeMenuItem);
					addWbsInnerNodeMenuItem.setOnAction(new EventHandler<ActionEvent>() {
						@Override
						public void handle(ActionEvent event) {
							WbsInnerNode newNode = new WbsInnerNode();
							if (!showWbsNodeNameEditorDialog(newNode)) {
								newNode.setName("NN node");
							}
							((WbsInnerNode) getTreeItem().getValue()).addChild(newNode);
							TreeItem<WbsNode> newWbsInnerNode = newNode.buildTreeItem();
							getTreeItem().getChildren().add(newWbsInnerNode);
						}
					});
				}
				MenuItem removeMenuItem = new MenuItem("remove element");
				operationsMenu.getItems().add(removeMenuItem);
				removeMenuItem.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						((WbsInnerNode) getTreeItem().getParent().getValue()).removeChild(getTreeItem().getValue());
						int indexToRemove = 0;
						for (TreeItem<WbsNode> child : getTreeItem().getParent().getChildren()) {
							if (child.getValue().equals(getTreeItem().getValue())) {
								getTreeItem().getParent().getChildren().remove(indexToRemove);
								break;
							}
							indexToRemove++;
						}
						addTaskToTableView(((WbsLeafNode) getTreeItem().getValue()).getTask());
					}
				});
			}
			return this.operationsMenu;
		}

		@Override
		public void startEdit() {
			super.startEdit();
			if (textField == null) {
				createTextField();
			}
			setText(null);
			setGraphic(textField);
			textField.selectAll();
		}

		@Override
		public void cancelEdit() {
			super.cancelEdit();
			setText(getString());
			setGraphic(getTreeItem().getGraphic());
		}

		@Override
		public void updateItem(WbsNode item, boolean empty) {
			super.updateItem(item, empty);
			if (empty) {
				setText(null);
				setGraphic(null);
			} else {
				if (isEditing()) {
					if (textField != null) {
						textField.setText(getString());
					}
					setText(null);
					setGraphic(textField);
				} else {
					getTreeItem().getValue().setName(getString());
					setText(getString());
					setGraphic(getTreeItem().getGraphic());
					setContextMenu(this.getOperationsMenu());
				}
			}
		}

		private void createTextField() {
			textField = new TextField(getString());
			textField.setOnKeyReleased(new EventHandler<KeyEvent>() {
				@Override
				public void handle(KeyEvent t) {
					if (t.getCode() == KeyCode.ENTER) {
						getTreeItem().getValue().setName(textField.getText());
						commitEdit(getTreeItem().getValue());
					} else if (t.getCode() == KeyCode.ESCAPE) {
						cancelEdit();
					}
				}
			});
		}

		private String getString() {
			return getItem() == null ? "" : getItem().toString();
		}
	}
}
