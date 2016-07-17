package edu.wesimulated.firstapp.simulation.domain;

import java.util.Date;

import edu.wesimulated.firstapp.model.TaskData;
import edu.wesimulated.firstapp.simulation.hla.HlaTask;
import edu.wesimulated.firstapp.simulation.stochastic.NumericallyModeledEntity;
import edu.wesimulated.firstapp.simulation.stochastic.TaskStochasticVariableFactory;

public class Task implements NumericallyModeledEntity {

	private HlaTask hlaTask;

	public Task(TaskData task) {
		// TODO Auto-generated constructor stub
	}

	public boolean isCompleted() {
		// TODO Auto-generated method stub
		return false;
	}

	public long findTimeToConfigureWorkbench() {
		return TaskStochasticVariableFactory.buildFactory().buildTimeToConfigureWorkbench().findRandomSample();
	}

	public long findTimeToFocus() {
		return TaskStochasticVariableFactory.buildFactory().buildTimeToFocus().findRandomSample();
	}

	public boolean isCompleted(Role role) {
		// TODO Auto-generated method stub
		return false;
	}
}
