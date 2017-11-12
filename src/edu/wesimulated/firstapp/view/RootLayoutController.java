package edu.wesimulated.firstapp.view;

import java.io.File;

import javafx.fxml.FXML;
import javafx.stage.FileChooser;
import edu.wesimulated.firstapp.FileType;
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
		mainApp.clearStorageFilePaths();
	}

	@FXML
	private void handleOpenProjectFile() {
		FileChooser fileChooser = new FileChooser();
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml");
		fileChooser.getExtensionFilters().add(extFilter);
		File file = fileChooser.showOpenDialog(mainApp.getPrimaryStage());
		if (file != null) {
			mainApp.loadProjectDataFromFile(file);
		}
	}
	
	@FXML
	private void handleOpenStochasticDataFromFile() {
		FileChooser fileChooser = new FileChooser();
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml");
		fileChooser.getExtensionFilters().add(extFilter);
		File file = fileChooser.showOpenDialog(mainApp.getPrimaryStage());
		if (file != null) {
			mainApp.loadStochasticDataFromFile(file);
		}
	}

	@FXML
	private void handleOpenStochasticRegistry() {
		FileChooser fileChooser = new FileChooser();
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml");
		fileChooser.getExtensionFilters().add(extFilter);
		File file = fileChooser.showOpenDialog(mainApp.getPrimaryStage());
		if (file != null) {
			mainApp.loadStochasticDataFromFile(file);
		}
	}

	@FXML
	private void handleSave() {
		File programDataFile = mainApp.getStorageFilePath(FileType.project);
		if (programDataFile != null) {
			mainApp.saveDataToFile(programDataFile, FileType.project);
		} else {
			saveAs(FileType.project);
		}
	}

	@FXML
	private void handleSaveStochasticRegistry() {
		File stochasticDataFile = mainApp.getStorageFilePath(FileType.stochasticData);
		if (stochasticDataFile != null) {
			mainApp.saveDataToFile(stochasticDataFile, FileType.stochasticData);
		} else {
			saveAs(FileType.stochasticData);
		}
	}

	@FXML
	private void handleSaveAs() {
		saveAs(FileType.project);
	}

	@FXML
	private void handleSaveAsStochasticRegistry() {
		this.saveAs(FileType.stochasticData);
	}

	@FXML
	private void handleExit() {
		System.exit(0);
	}

	@FXML
	private void handleProject() {
		mainApp.showProjectEdit();
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

	@FXML
	private void handleRAM() {
		mainApp.showRam();
	}

	@FXML
	private void handleTaskNet() {
		mainApp.showTaskNet();
	}
	
	private void saveAs(FileType fileType) {
		FileChooser fileChooser = new FileChooser();
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml");
		fileChooser.getExtensionFilters().add(extFilter);
		File file = fileChooser.showSaveDialog(mainApp.getPrimaryStage());
		if (file != null) {
			if (!file.getPath().endsWith(".xml")) {
				file = new File(file.getPath() + ".xml");
			}
			mainApp.saveDataToFile(file, fileType);
		}
	}
}
