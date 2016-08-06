package edu.wesimulated.firstapp.simulation;

import com.wesimulated.simulation.BaseSimulator;
import com.wesimulated.simulationmotor.des.OperationBasedExecutor;

public class OperationBasedSimulator extends BaseSimulator {

	public void startExecution() {
		this.executor.run();
	}

	public OperationBasedExecutor getExecutor() {
		return executor;
	}

	public void setExecutor(OperationBasedExecutor executor) {
		this.executor = executor;
	}

	public boolean isRunning() {
		return !this.getExecutor().isSimulationEnd();
	}

}