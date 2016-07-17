package edu.wesimulated.firstapp.simulation.domain;

import edu.wesimulated.firstapp.model.TaskData;
import edu.wesimulated.firstapp.simulation.hla.HlaTask;
import edu.wesimulated.firstapp.simulation.stochastic.NumericallyModeledEntity;

public class Task implements NumericallyModeledEntity {

	private HlaTask hlaTask;

	public Task(TaskData task) {
		// TODO Auto-generated constructor stub
	}

	public boolean isCompleted() {
		// TODO Auto-generated method stub
		return false;
	}
}
