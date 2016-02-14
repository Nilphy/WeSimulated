package edu.wesimulated.firstapp.view;

import java.text.SimpleDateFormat;
import java.util.Date;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import edu.wesimulated.firstapp.MainApp;
import edu.wesimulated.firstapp.model.PersonData;
import edu.wesimulated.firstapp.model.TaskData;
import edu.wesimulated.firstapp.simulation.Simulation;
import edu.wesimulated.firstapp.simulation.domain.Work;

public class SimulationOverviewController {

	@FXML
	private TextArea textDisplay;
	private MainApp mainApp;

	public SimulationOverviewController() {
		Simulation.getInstance().setSimulationOverviewController(this);
	}

	@FXML
	private void initialize() {
		this.textDisplay.setEditable(false);
		this.textDisplay.setText("Log: \n");
	}

	@FXML
	private void loadPeopleAndTasks() {
		for (PersonData person : this.mainApp.getPersonData()) {
			Simulation.getInstance().addPerson(person);
		}
		for (TaskData task : this.mainApp.getTaskData()) {
			Simulation.getInstance().addTask(task);
		}
		Simulation.getInstance().registerLogger();
	}

	@FXML
	private void stopAndDestroySimulation() {
		Simulation.getInstance().destroyFederation();
	}

	@FXML
	private void startSimulation() {
		Simulation.getInstance().startFederation();
	}

	public void log(String message, Date date) {
		this.textDisplay.appendText(this.getTimeToLog(date) + " " + message + "\n");
	}

	public void log(Work workDone, Date date) {
		this.textDisplay.appendText(this.getTimeToLog(date) + " " + workDone + "\n");
	}

	private String getTimeToLog(Date date) {
		Date logDate = date;
		if (date == null) {
			logDate = new Date();
		}
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd  hh:mm");
		return formatter.format(logDate);
	}

	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}
}
