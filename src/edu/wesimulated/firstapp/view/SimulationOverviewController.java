package edu.wesimulated.firstapp.view;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import edu.wesimulated.firstapp.MainApp;
import edu.wesimulated.firstapp.model.LogEvent;
import edu.wesimulated.firstapp.simulation.Simulation;

public class SimulationOverviewController {

	@FXML
	private TextArea textDisplay;
	private Simulation simulation;
	private MainApp mainApp;
	
	public SimulationOverviewController () {
		Simulation simulation = new Simulation();
		
	}
	
	@FXML
	private void initialize() {
		this.textDisplay.setEditable(false);
		this.textDisplay.setText("Log: \n");
	}
	
	public void log(String message) {
		this.textDisplay.appendText(message + "\n");
	}
	
	public void log(float workDone) {
		this.textDisplay.appendText(new LogEvent(workDone) + "\n");
	}

	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}
}
