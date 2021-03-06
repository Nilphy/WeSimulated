package edu.wesimulated.firstapp;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
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
import javafx.util.StringConverter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import edu.wesimulated.firstapp.model.PersonData;
import edu.wesimulated.firstapp.model.ProjectData;
import edu.wesimulated.firstapp.model.RaciType;
import edu.wesimulated.firstapp.model.ResponsibilityAssignmentData;
import edu.wesimulated.firstapp.model.RoleData;
import edu.wesimulated.firstapp.model.StochasticMethodConfigData;
import edu.wesimulated.firstapp.model.StochasticRegistryData;
import edu.wesimulated.firstapp.model.StochasticVarData;
import edu.wesimulated.firstapp.model.TaskData;
import edu.wesimulated.firstapp.model.TaskNet;
import edu.wesimulated.firstapp.model.WbsInnerNode;
import edu.wesimulated.firstapp.persistence.UiModelToXml;
import edu.wesimulated.firstapp.simulation.stochastic.StochasticMethod;
import edu.wesimulated.firstapp.simulation.stochastic.StochasticRegistry;
import edu.wesimulated.firstapp.simulation.stochastic.StochasticVar;
import edu.wesimulated.firstapp.view.PersonOverviewController;
import edu.wesimulated.firstapp.view.ProjectEditController;
import edu.wesimulated.firstapp.view.RAMController;
import edu.wesimulated.firstapp.view.RoleOverviewController;
import edu.wesimulated.firstapp.view.RootLayoutController;
import edu.wesimulated.firstapp.view.SimulationOverviewController;
import edu.wesimulated.firstapp.view.TaskNetController;
import edu.wesimulated.firstapp.view.TaskOverviewController;
import edu.wesimulated.firstapp.view.WBSController;

public class MainApp extends Application {
	static public final String DATE_PATTERN = "yyyy-MM-dd";

	private Stage primaryStage;
	private BorderPane rootLayout;
	private ProjectData projectData;
	private ObservableList<PersonData> personData;
	private ObservableList<TaskData> taskData;
	private ObservableList<RoleData> roleData;
	private WbsInnerNode wbs;
	private TaskNet taskNet;
	private ObservableList<ResponsibilityAssignmentData> responsibilityAssignmentData;
	private StringConverter<LocalDate> converter;

	public MainApp() {
		this.personData = FXCollections.observableArrayList();
		this.taskData = FXCollections.observableArrayList();
		this.roleData = FXCollections.observableArrayList();
		this.wbs = new WbsInnerNode();
		this.taskNet = new TaskNet();
		this.responsibilityAssignmentData = FXCollections.observableArrayList();
		this.converter = new StringConverter<LocalDate>() {
			DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(DATE_PATTERN);

			@Override
			public String toString(LocalDate date) {
				if (date != null) {
					return dateFormatter.format(date);
				} else {
					return "";
				}
			}

			@Override
			public LocalDate fromString(String string) {
				if (string != null && !string.isEmpty()) {
					return LocalDate.parse(string, dateFormatter);
				} else {
					return null;
				}
			}
		};
	}

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("We Simulated");
		this.primaryStage.getIcons().add(new Image("file:resources/images/lollipop.png"));
		this.initRootLayout();
		// FIXME move all xml load logic to a separate class
		this.loadDataFromFile();
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

	public void showTaskNet() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/TaskNet.fxml"));
			AnchorPane taskNet = (AnchorPane) loader.load();
			this.rootLayout.setCenter(taskNet);
			TaskNetController controller = loader.getController();
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

	public void showRam() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/RAM.fxml"));
			AnchorPane ram = (AnchorPane) loader.load();
			this.rootLayout.setCenter(ram);
			RAMController controller = loader.getController();
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

	public boolean showProjectEdit() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/ProjectEditDialog.fxml"));
			AnchorPane page = (AnchorPane) loader.load();
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Edit Project");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(this.getPrimaryStage());
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);
			ProjectEditController controller = loader.getController();
			controller.setDialogStage(dialogStage);
			controller.setDateFormatter(this.getConverter());
			controller.setProject(this.projectData);
			dialogStage.showAndWait();
			return controller.isOkClicked();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	public ObservableList<ResponsibilityAssignmentData> buildResponsibilityAssignmentData() {
		boolean found = false;
		for (RoleData role : this.getRoleData()) {
			for (TaskData task : this.getTaskData()) {
				found = false;
				for (ResponsibilityAssignmentData responsibilityAssignment : this.responsibilityAssignmentData) {
					if (responsibilityAssignment.getTask().equals(task) && responsibilityAssignment.getRole().equals(role)) {
						found = true;
						break;
					}
				}
				if (!found) {
					ResponsibilityAssignmentData newResponsibilityAssignment = new ResponsibilityAssignmentData();
					newResponsibilityAssignment.setTask(task);
					newResponsibilityAssignment.setRole(role);
					responsibilityAssignmentData.add(newResponsibilityAssignment);
				}
			}
		}
		return this.responsibilityAssignmentData;
	}

	public ProjectData buildProjectData() {
		ProjectData projectData = new ProjectData();
		projectData.setPeople(this.personData);
		projectData.setTasks(this.taskData);
		projectData.setRoles(this.roleData);
		projectData.setWbsRootNode(UiModelToXml.convertToXml(getWbs()));
		projectData.setResponsibilityAssignments(UiModelToXml.convertToXml(buildResponsibilityAssignmentData()));
		return projectData;
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
			if (iterationRole.getName().equalsIgnoreCase(roleName)) {
				found = iterationRole;
			}
		}
		return found;
	}

	public Collection<RoleData> findRolesOfTaskAndRaciType(TaskData taskToFind, RaciType raciType) {
		ObservableList<ResponsibilityAssignmentData> responsibilityAssignmentData = this.buildResponsibilityAssignmentData();
		Collection<RoleData> rolesOfTask = new ArrayList<RoleData>();
		for (ResponsibilityAssignmentData raData : responsibilityAssignmentData) {
			if (raData.getTask().equals(taskToFind) && raData.isOfRaciType(raciType)) {
				rolesOfTask.add(raData.getRole());
			}
		}
		return rolesOfTask;
	}

	public Collection<PersonData> findPeopleWithRoles(Collection<RoleData> roles) {
		Collection<PersonData> personsWithRoles = new ArrayList<>();
		for (PersonData person : this.personData) {
			for (RoleData role : person.getRoles()) {
				if (roles.contains(role)) {
					personsWithRoles.add(person);
				}
				break;
			}
		}
		return personsWithRoles;
	}

	public void clearWbs() {
		this.wbs = new WbsInnerNode();
	}

	public boolean mustSimulateProject() {
		// TODO Still need to define how to configure the application to run
		// distributed
		return false;
	}

	public boolean mustStartLogger() {
		// TODO Still need to define how to configure the application to run
		// distributed
		return false;
	}

	public void saveDataToFile(File file, FileType fileType) {
		try {
			JAXBContext context = JAXBContext.newInstance(FileType.project.equals(fileType) ? ProjectData.class : StochasticRegistryData.class);
			Marshaller m = context.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			if (FileType.project.equals(fileType)) {
				ProjectData projectData = buildProjectData();
				m.marshal(projectData, file);
			} else {
				StochasticRegistryData stochasticRegistryData = buildStochasticRegistryData();
				m.marshal(stochasticRegistryData, file);
			}
			setStorageFilePath(file, fileType);
		} catch (Exception e) {
			e.printStackTrace();
			this.showAlert("Could not save data", "Could not save data to file");
		}
	}

	public void loadProjectDataFromFile(File projectFile) {
		try {
			JAXBContext context = JAXBContext.newInstance(ProjectData.class);
			Unmarshaller um = context.createUnmarshaller();
			this.projectData = (ProjectData) um.unmarshal(projectFile);
			this.fillRoleInfo();
			this.fillPeopleInfo();
			this.fillTaskInfo();
			this.fillWbsInfo();
			this.fillRamInfo();
			this.fillTaskNet();
			setStorageFilePath(projectFile, FileType.project);
		} catch (Exception e) {
			showAlert("Could not load data", "Could not load data from file");
			e.printStackTrace();
		}
	}

	public void loadStochasticDataFromFile(File file) {
		try {
			JAXBContext context = JAXBContext.newInstance(StochasticRegistryData.class);
			Unmarshaller um = context.createUnmarshaller();
			StochasticRegistryData stochasticRegistryData = (StochasticRegistryData) um.unmarshal(file);
			StochasticRegistry.getInstance().loadData(stochasticRegistryData);
			this.setStorageFilePath(file, FileType.stochasticData);
		} catch (Exception e) {
			showAlert("Could not load data", "Could not load data from file");
			e.printStackTrace();
		}
	}

	public void clearStorageFilePaths() {
		for (FileType fileType : FileType.values()) {
			this.setStorageFilePath(null, fileType);
		}
	}

	public Stage getPrimaryStage() {
		return this.primaryStage;
	}

	public File getStorageFilePath(FileType fileType) {
		Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
		String filePath = prefs.get(fileType.name(), null);
		if (filePath != null) {
			return new File(filePath);
		} else {
			return null;
		}
	}

	public void setStorageFilePath(File file, FileType fileType) {
		Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
		if (file != null) {
			prefs.put(fileType.name(), file.getPath());
		} else {
			prefs.remove(fileType.name());
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

	public TaskNet getTaskNet() {
		return taskNet;
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

	}

	private void loadDataFromFile() {
		File projectFile = getStorageFilePath(FileType.project);
		if (projectFile != null) {
			this.loadProjectDataFromFile(projectFile);
		}
		File stochasticDataFile = getStorageFilePath(FileType.stochasticData);
		if (stochasticDataFile != null) {
			this.loadStochasticDataFromFile(stochasticDataFile);
		}
	}

	private void fillTaskNet() {
		this.taskNet.initFromTasks(this.getTaskData());
	}

	private void showAlert(String headerText, String contentText) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Error");
		alert.setHeaderText(headerText);
		alert.setContentText(contentText);
		alert.showAndWait();
	}

	private void fillWbsInfo() {
		WbsInnerNode newWbs = UiModelToXml.convertToUiModel(this.projectData.getWbsRootNode(), this);
		getWbs().getChildrenWbsNodes().clear();
		getWbs().setChildrenWbsNodes(newWbs.getChildrenWbsNodes());
		getWbs().setName(newWbs.getName());
	}

	private void fillTaskInfo() {
		this.taskData.clear();
		this.taskData.addAll(this.projectData.getTasks());
		projectData.registerMaxId();
	}

	private void fillPeopleInfo() {
		this.personData.clear();
		UiModelToXml.changeRolesFromMainAppOnes(this.projectData.getPeople(), this);
		this.personData.addAll(this.projectData.getPeople());
	}

	private void fillRamInfo() {
		this.responsibilityAssignmentData.clear();
		this.responsibilityAssignmentData.addAll(UiModelToXml.convertToUiModel(this.projectData.getResponsibilityAssignments(), this));
	}

	private void fillRoleInfo() {
		this.roleData.clear();
		this.roleData.addAll(this.projectData.getRoles());
	}

	private StochasticRegistryData buildStochasticRegistryData() {
		StochasticRegistryData stochasticRegistryData = new StochasticRegistryData();
		List<StochasticVarData> vars = new ArrayList<>();
		for (StochasticVar stochasticVar : StochasticVar.values()) {
			StochasticMethod stochasticMethod = StochasticRegistry.getInstance().getStochasticMethod(stochasticVar);
			StochasticVarData varData = new StochasticVarData();
			if (stochasticMethod != null) {
				varData.setType(stochasticMethod.getType().name());
				varData.setName(stochasticVar.name());
				stochasticMethod.getConfig().getEntries().forEach(config -> varData.addConfig(StochasticMethodConfigData.fromEntry(config)));
			}
			vars.add(varData);
		}
		stochasticRegistryData.setStochasticVars(vars);
		return stochasticRegistryData;
	}

	public StringConverter<LocalDate> getConverter() {
		return this.converter;
	}

	/**
	 * Is better to call build first to update the data
	 * @return
	 */
	public ProjectData getProjectData() {
		return this.projectData;
	}
}
