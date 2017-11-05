package edu.wesimulated.firstapp.model;

import edu.wesimulated.firstapp.simulation.SimulatorType;
import edu.wesimulated.firstapp.simulation.domain.Identifiable;

public interface SimulationEntity extends Identifiable {

	public SimulatorType calculateSimulatorType();
}
