package edu.wesimulated.firstapp.simulation.domain.mywork.task;

import edu.wesimulated.firstapp.simulation.domain.Person;
import edu.wesimulated.firstapp.simulation.domain.SimulatorFactory;

public class SystemDynamicsSimulatorFactory extends SimulatorFactory {

	@Override
	public Person makePerson() {
		return new Person();
	}

	@Override
	public Task makeTask() {
		return new Task();
	}

	}

}
