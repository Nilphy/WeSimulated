package edu.wesimulated.firstapp.simulation.domain.mywork.role;

import edu.wesimulated.firstapp.simulation.domain.Person;
import edu.wesimulated.firstapp.simulation.domain.SimulatorFactory;
import edu.wesimulated.firstapp.simulation.domain.Task;

public class RoleSimulatorFactory extends SimulatorFactory {

	@Override
	public Person makePerson() {
		return new RolePerson();
	}

	@Override
	public Task makeTask() {
		return new AvatureDeveloperTask();
	}

}
