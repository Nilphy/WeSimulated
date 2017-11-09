package edu.wesimulated.firstapp.view;

import java.util.Date;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

import com.wesimulated.simulationmotor.DateUtils;

import edu.wesimulated.firstapp.MainApp;
import edu.wesimulated.firstapp.model.PersonData;
import edu.wesimulated.firstapp.model.RoleData;
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

	public void log(String message, Date date) {
		this.textDisplay.appendText(DateUtils.asLog(date) + " " + message + "\n");
	}

	public void log(Work workDone, Date date) {
		this.textDisplay.appendText(DateUtils.asLog(date) + " " + workDone + "\n");
	}

	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}

	@FXML
	private void initialize() {
		this.textDisplay.setEditable(false);
		this.textDisplay.setText("Log: \n");
	}

	@FXML
	private void loadPeopleAndTasks() {
		for (RoleData role : this.mainApp.getRoleData()) {
			for (PersonData person : this.mainApp.getPersonData()) {
				if (person.getRoles().contains(role)) {
					Simulation.getInstance().registerRole(role, person);
				}
			}
		}
		for (TaskData task : this.mainApp.getTaskData()) {
			Simulation.getInstance().registerTask(task);
		}
		if (this.mainApp.mustStartLogger()) {
			Simulation.getInstance().registerLogger();
		}
		if (this.mainApp.mustSimulateProject()) {
			Simulation.getInstance().registerProject(this.mainApp.buildProjectData());
		}
	}

	@FXML
	private void stopAndDestroySimulation() {
		Simulation.getInstance().destroyFederation();
	}

	@FXML
	private void startSimulation() {
		Simulation.getInstance().startFederation();
	}
}
