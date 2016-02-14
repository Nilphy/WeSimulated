package edu.wesimulated.firstapp.simulation;

import com.wesimulated.simulationmotor.des.OperationBasedExecutor;

public class Simulator {
	private OperationBasedExecutor executor;

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