package edu.wesimulated.firstapp.simulation.domain;

import edu.wesimulated.firstapp.model.RoleData;
import edu.wesimulated.firstapp.model.TaskData;
import edu.wesimulated.firstapp.simulation.domain.highlyinterruptiblerole.HighlyInterruptibleRoleSimulatorFactory;
import edu.wesimulated.firstapp.view.ThingsWithoutAUi;

public abstract class SimulatorFactory {
	
	public abstract Person makePerson();

	public abstract Task makeTask();

	public static SimulatorFactory getInstance(RoleData role) {
		if (ThingsWithoutAUi.roleIsModeledAsHighlyInterruptible(role)) {
			return new HighlyInterruptibleRoleSimulatorFactory();
		}
		return null;
	}

	public static SimulatorFactory getInstance(TaskData task) {
		// TODO Auto-generated method stub
		return null;
	}
}
