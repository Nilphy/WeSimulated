package edu.wesimulated.firstapp.view;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import edu.wesimulated.firstapp.MainApp;
import edu.wesimulated.firstapp.model.LogEvent;
import edu.wesimulated.firstapp.model.Person;
import edu.wesimulated.firstapp.model.Task;
import edu.wesimulated.firstapp.simulation.Simulation;

public class SimulationOverviewController {

	@FXML
	private TextArea textDisplay;
	private MainApp mainApp;
	
	public SimulationOverviewController () {
		Simulation.getInstance().setSimulationOverviewController(this);
	}
	
	@FXML
	private void initialize() {
		this.textDisplay.setEditable(false);
		this.textDisplay.setText("Log: \n");
	}
	
	@FXML
	private void loadPeopleAndTasks() {
		for (Person person : this.mainApp.getPersonData()) {
			Simulation.getInstance().addPerson(person);
		}
		for (Task task : this.mainApp.getTaskData()) {
			Simulation.getInstance().addTask(task);
		}
		Simulation.getInstance().registerLogger();
	}
	
	@FXML
	private void stopAndDestroySimulation() {
		Simulation.getInstance().destroyFederation();
	}
	
	public void log(String message) {
		this.textDisplay.appendText(this.getTimeToLog() + " " + message + "\n");
	}

	public void log(float workDone) {
		this.textDisplay.appendText(this.getTimeToLog() + " " + new LogEvent(workDone) + "\n");
	}
	
	private String getTimeToLog() {
		LocalDateTime now = LocalDateTime.now();
		return now.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.FULL));
	}

	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}
}
