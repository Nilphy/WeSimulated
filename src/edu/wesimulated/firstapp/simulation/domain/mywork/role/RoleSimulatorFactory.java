package edu.wesimulated.firstapp.simulation.domain.mywork.role;

import edu.wesimulated.firstapp.simulation.domain.Role;
import edu.wesimulated.firstapp.simulation.domain.SimulatorFactory;

public class RoleSimulatorFactory extends SimulatorFactory {

	@Override
	public Person makePerson() {
		return new Person();
	}

	@Override
	public Task makeTask() {
		return new Task();
	}

	@Override
	public Project makeProject() {
		return new Project();
	}

	@Override
	public Role makeRole() {
		return new Role();
	}
}
