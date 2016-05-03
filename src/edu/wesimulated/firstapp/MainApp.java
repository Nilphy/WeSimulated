package edu.wesimulated.firstapp;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.prefs.Preferences;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import edu.wesimulated.firstapp.model.PersonData;
import edu.wesimulated.firstapp.model.ProjectData;
import edu.wesimulated.firstapp.model.RoleData;
import edu.wesimulated.firstapp.model.TaskData;
import edu.wesimulated.firstapp.model.WbsInnerNode;
import edu.wesimulated.firstapp.persistence.UiModelToXml;
import edu.wesimulated.firstapp.view.PersonOverviewController;
import edu.wesimulated.firstapp.view.RAMController;
import edu.wesimulated.firstapp.view.RoleOverviewController;
import edu.wesimulated.firstapp.view.RootLayoutController;
import edu.wesimulated.firstapp.view.SimulationOverviewController;
import edu.wesimulated.firstapp.view.TaskEditController;
import edu.wesimulated.firstapp.view.TaskOverviewController;
import edu.wesimulated.firstapp.view.WBSController;

public class MainApp extends Application {

	private Stage primaryStage;
	private BorderPane rootLayout;
	private ObservableList<PersonData> personData = FXCollections.observableArrayList();
	private ObservableList<TaskData> taskData = FXCollections.observableArrayList();
	private ObservableList<RoleData> roleData = FXCollections.observableArrayList();
	private WbsInnerNode wbs = new WbsInnerNode();

	public MainApp() {
	}

	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("We Simulated");
		this.primaryStage.getIcons().add(new Image("file:resources/images/lollipop.png"));
		this.initRootLayout();
		this.showPersonOverview();
	}

	public void showTaskOverview() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/TaskOverview.fxml"));
			AnchorPane taskOverview = (AnchorPane) loader.load();
			this.rootLayout.setCenter(taskOverview);
			TaskOverviewController controller = loader.getController();
			controller.setMainApp(this);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void showRoleOverview() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/RoleOverview.fxml"));
			AnchorPane rolOverview = (AnchorPane) loader.load();
			this.rootLayout.setCenter(rolOverview);
			RoleOverviewController controller = loader.getController();
			controller.setMainApp(this);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void showWbs() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/WBS.fxml"));
			AnchorPane wbs = (AnchorPane) loader.load();
			this.rootLayout.setCenter(wbs);
			WBSController controller = loader.getController();
			controller.setMainApp(this);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void showPersonOverview() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/PersonOverview.fxml"));
			AnchorPane personOverview = (AnchorPane) loader.load();
			this.rootLayout.setCenter(personOverview);
			PersonOverviewController controller = loader.getController();
			controller.setMainApp(this);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void showSimulationOverview() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/SimulationOverview.fxml"));
			AnchorPane simulationOverview = (AnchorPane) loader.load();
			this.rootLayout.setCenter(simulationOverview);
			SimulationOverviewController controller = loader.getController();
			controller.setMainApp(this);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void initRootLayout() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/RootLayout.fxml"));
			rootLayout = (BorderPane) loader.load();
			Scene scene = new Scene(rootLayout);
			primaryStage.setScene(scene);
			RootLayoutController controller = loader.getController();
			controller.setMainApp(this);
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
		File file = getStorageFilePath();
		if (file != null) {
			loadProjectDataFromFile(file);
		}
	}

	public ObservableList<TaskData> getTaskData() {
		return this.taskData;
	}

	public ObservableList<PersonData> getPersonData() {
		return this.personData;
	}

	public ObservableList<RoleData> getRoleData() {
		return this.roleData;
	}

	public WbsInnerNode getWbs() {
		return this.wbs;
	}

	public Stage getPrimaryStage() {
		return this.primaryStage;
	}

	public static void main(String[] args) {
		launch(args);
	}

	public File getStorageFilePath() {
		Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
		String filePath = prefs.get("filePath", null);
		if (filePath != null) {
			return new File(filePath);
		} else {
			return null;
		}
	}

	public void setStorageFilePath(File file) {
		Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
		if (file != null) {
			prefs.put("filePath", file.getPath());
			primaryStage.setTitle("FirstApp - " + file.getName());
		} else {
			prefs.remove("filePath");
			primaryStage.setTitle("FirstApp");
		}
	}

	public void loadProjectDataFromFile(File file) {
		try {
			JAXBContext context = JAXBContext.newInstance(ProjectData.class);
			Unmarshaller um = context.createUnmarshaller();
			ProjectData projectData = (ProjectData) um.unmarshal(file);
			this.fillRoleInfo(projectData);
			this.fillPeopleInfo(projectData);
			this.fillTaskInfo(projectData);
			this.fillWbsInfo(projectData);
			setStorageFilePath(file);
		} catch (Exception e) {
			showAlert(file, "Could not load data", "Could not load data from file");
			e.printStackTrace();
		}
	}

	private void showAlert(File file, String headerText, String contentText) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Error");
		alert.setHeaderText(headerText);
		alert.setContentText(contentText + ": \n" + file.getPath());
		alert.showAndWait();
	}

	private void fillWbsInfo(ProjectData projectData) {
		WbsInnerNode newWbs = UiModelToXml.convertToUiModel(projectData.getWbsRootNode(), this);
		getWbs().getChildrenWbsNodes().clear();
		getWbs().setChildrenWbsNodes(newWbs.getChildrenWbsNodes());
		getWbs().setName(newWbs.getName());
	}

	private void fillTaskInfo(ProjectData projectData) {
		this.taskData.clear();
		this.taskData.addAll(projectData.getTasks());
		projectData.registerMaxId();
	}

	private void fillPeopleInfo(ProjectData projectData) {
		this.personData.clear();
		UiModelToXml.changeRolesFromMainAppOnes(projectData.getPersons(), this);
		this.personData.addAll(projectData.getPersons());
	}

	private void fillRoleInfo(ProjectData projectData) {
		this.roleData.clear();
		this.roleData.addAll(projectData.getRoles());
	}

	public void saveProjectDataToFile(File file) {
		try {
			JAXBContext context = JAXBContext.newInstance(ProjectData.class);
			Marshaller m = context.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			ProjectData projectData = new ProjectData();
			projectData.setPersons(this.personData);
			projectData.setTasks(this.taskData);
			projectData.setRoles(this.roleData);
			projectData.setWbsRootNode(UiModelToXml.convertToXml(getWbs()));
			m.marshal(projectData, file);
			setStorageFilePath(file);
		} catch (Exception e) {
			e.printStackTrace();
			this.showAlert(file, "Could not save data", "Could not save data to file");
		}
	}

	public TaskData findTaskById(Integer taskId) {
		Iterator<TaskData> taskDataIterator = this.taskData.iterator();
		TaskData found = null;
		TaskData iterationTask = null;
		while (taskDataIterator.hasNext() && found == null) {
			iterationTask = taskDataIterator.next();
			if (iterationTask.getId().equals(taskId)) {
				found = iterationTask;
			}
		}
		return found;
	}

	public RoleData findRoleByName(String roleName) {
		Iterator<RoleData> roleDataIterator = this.roleData.iterator();
		RoleData found = null;
		RoleData iterationRole = null;
		while (roleDataIterator.hasNext() && found == null) {
			iterationRole = roleDataIterator.next();
			if (iterationRole.getName().equals(roleName)) {
				found = iterationRole;
			}
		}
		return found;
	}

	public void clearWbs() {
		this.wbs = new WbsInnerNode();
	}

	public boolean mustSimulateProject() {
		// TODO Auto-generated method stub
		return false;
	}

	public Object getProjectData() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean mustStartLogger() {
		// TODO Auto-generated method stub
		return false;
	}
}
