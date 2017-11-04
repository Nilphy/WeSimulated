package edu.wesimulated.firstapp.simulation.domain.mywork.highlyinterruptiblerole;

import edu.wesimulated.firstapp.simulation.domain.Project;
import edu.wesimulated.firstapp.simulation.domain.Role;
import edu.wesimulated.firstapp.simulation.domain.Task;

public class HighlyInterruptibleRoleSimulatorFactory extends edu.wesimulated.firstapp.simulation.domain.SimulatorFactory {

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
