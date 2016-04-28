package edu.wesimulated.firstapp.view;

import java.io.File;

import javafx.fxml.FXML;
import javafx.stage.FileChooser;
import edu.wesimulated.firstapp.MainApp;

public class RootLayoutController {

	private MainApp mainApp;
	
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}
	
	@FXML
	private void handleNew() {
		mainApp.getPersonData().clear();
		mainApp.getTaskData().clear();
		mainApp.clearWbs();
		mainApp.setStorageFilePath(null);
	}
	
	@FXML
	private void handleOpen() {
		FileChooser fileChooser = new FileChooser();
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml");
		fileChooser.getExtensionFilters().add(extFilter);
		File file = fileChooser.showOpenDialog(mainApp.getPrimaryStage());
		if (file != null) {
			mainApp.loadProjectDataFromFile(file);
		}
	}
	
	@FXML
	private void handleSave() {
		File programDataFile = mainApp.getStorageFilePath();
		if (programDataFile != null) {
			mainApp.saveProjectDataToFile(programDataFile);
		} else {
			handleSaveAs();
		}
	}

	@FXML
	private void handleSaveAs() {
		FileChooser fileChooser = new FileChooser();
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml");
		fileChooser.getExtensionFilters().add(extFilter);
		File file = fileChooser.showSaveDialog(mainApp.getPrimaryStage());
		if (file != null) {
			if (!file.getPath().endsWith(".xml")) {
				file = new File(file.getPath() + ".xml");
			}
			mainApp.saveProjectDataToFile(file);
		}
	}
	
	@FXML
	private void handleExit() {
		System.exit(0);
	}
	
	@FXML
	private void handleTasks() {
		mainApp.showTaskOverview();
	}
	
	@FXML
	private void handlePersons() {
		mainApp.showPersonOverview();
	}
	
	@FXML
	private void handleRoles() {
		mainApp.showRoleOverview();
	}
	
	@FXML
	private void handleSimulationRun() {
		mainApp.showSimulationOverview();
	}
	
	@FXML
	private void handleWBS() {
		mainApp.showWbs();
	}
}
