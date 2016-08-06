package edu.wesimulated.firstapp.simulation;

import com.wesimulated.simulation.BaseSimulator;
import com.wesimulated.simulationmotor.des.OperationBasedExecutor;

public class OperationBasedSimulator extends BaseSimulator {

	public OperationBasedExecutor getOperationBasedExecutor() {
		return (OperationBasedExecutor) this.getExecutor();
	}
}