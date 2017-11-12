package edu.wesimulated.firstapp.simulation;

import com.wesimulated.simulation.BaseExecutor;
import com.wesimulated.simulation.BaseSimulator;

import edu.wesimulated.firstapp.simulation.hla.AbstractFederate;

public abstract class SimulationEvent<T extends BaseExecutor> {

	public final static <U extends BaseExecutor> SimulationEvent<U> buildStartEvent() {
		return new StartEvent<U>();
	}

	public final static <V extends BaseExecutor> SimulationEvent<V> buildEndEvent() {
		return new EndEvent<V>();
	}

	public abstract void updateSimulation(BaseSimulator<T> simulator, AbstractFederate personFederate);
}
