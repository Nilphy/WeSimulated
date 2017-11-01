package edu.wesimulated.firstapp.simulation.domain.mywork.highlyinterruptiblerole;

import edu.wesimulated.firstapp.simulation.domain.Task;

public class HighlyInterruptibleRoleSimulatorFactory extends edu.wesimulated.firstapp.simulation.domain.SimulatorFactory {

	@Override
	public HighlyInterruptibleRolePerson makePerson() {
		return new HighlyInterruptibleRolePerson();
	}

	@Override
	public Task makeTask() {
		throw new IllegalStateException();
	}
}
