package edu.wesimulated.firstapp;

import java.io.File;
import java.io.IOException;
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
import edu.wesimulated.firstapp.model.TaskData;
import edu.wesimulated.firstapp.view.PersonEditController;
import edu.wesimulated.firstapp.view.PersonOverviewController;
import edu.wesimulated.firstapp.view.RootLayoutController;
import edu.wesimulated.firstapp.view.SimulationOverviewController;
import edu.wesimulated.firstapp.view.TaskEditController;
import edu.wesimulated.firstapp.view.TaskOverviewController;

public class MainApp extends Application {

	private Stage primaryStage;
	private BorderPane rootLayout;
	private ObservableList<PersonData> personData = FXCollections.observableArrayList();
	private ObservableList<TaskData> taskData = FXCollections.observableArrayList();

	public MainApp() {
		personData.add(new PersonData("Juan", "Perez"));
		personData.add(new PersonData("Ricardo", "Rojas"));
		taskData.add(new TaskData("Person ABM", 16));
		taskData.add(new TaskData("Login", 8));
	}

	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("We Simulated, First App!!");
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

	public boolean showPersonEditDialog(PersonData person) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/PersonEditDialog.fxml"));
			AnchorPane page = (AnchorPane) loader.load();
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Edit Person");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);
			PersonEditController controller = loader.getController();
			controller.setDialogStage(dialogStage);
			controller.setPerson(person);
			dialogStage.showAndWait();
			return controller.isOkClicked();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
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
			dialogStage.initOwner(primaryStage);
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
			loadProgramDataFromFile(file);
		}
	}

	public ObservableList<TaskData> getTaskData() {
		return this.taskData;
	}
	
	public ObservableList<PersonData> getPersonData() {
		return this.personData;
	}
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

	public void loadProgramDataFromFile(File file) {
		try {
			JAXBContext context = JAXBContext.newInstance(ProgramData.class);
			Unmarshaller um = context.createUnmarshaller();
			ProgramData programData = (ProgramData) um.unmarshal(file);
			personData.clear();
			personData.addAll(programData.getPersons());
			taskData.clear();
			taskData.addAll(programData.getTasks());
			setStorageFilePath(file);
		} catch (Exception e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("Could not load data");
			alert.setContentText("Could not load data from file: \n" + file.getPath());
			alert.showAndWait();
		}
	}
	
	public void savePersonDataToFile(File file) {
		try {
			JAXBContext context = JAXBContext.newInstance(ProgramData.class);
			Marshaller m = context.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			ProgramData programData = new ProgramData();
			programData.setPersons(this.personData);
			programData.setTasks(this.taskData);
			m.marshal(programData, file);
			setStorageFilePath(file);
		} catch (Exception e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("Could not save data");
			alert.setContentText("Could not save data to file: \n" + file.getPath());
			alert.showAndWait();
		}
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
