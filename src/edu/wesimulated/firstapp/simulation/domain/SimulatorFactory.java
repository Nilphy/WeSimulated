package edu.wesimulated.firstapp.simulation.domain;

import java.util.Map;

import edu.wesimulated.firstapp.model.RoleData;
import edu.wesimulated.firstapp.model.TaskData;
import edu.wesimulated.firstapp.simulation.SimulatorType;
import edu.wesimulated.firstapp.simulation.TaskSimulatorBuilder.TaskSimulatorType;
import edu.wesimulated.firstapp.simulation.avature.task.SystemDynamicsSimulatorFactory;
import edu.wesimulated.firstapp.simulation.domain.avature.highlyinterruptiblerole.HighlyInterruptibleRoleSimulatorFactory;
import edu.wesimulated.firstapp.view.ThingsWithoutAUi;

public abstract class SimulatorFactory {
	
	private static Map<SimulatorType, SimulatorFactory> instancesByType;
	
	public abstract Person makePerson();

	public abstract Task makeTask();

	public static SimulatorFactory getInstance(RoleData role) {
		if (ThingsWithoutAUi.roleIsModeledAsHighlyInterruptible(role)) {
			return new HighlyInterruptibleRoleSimulatorFactory();
		}
		return null;
	}

	public static SimulatorFactory getInstance(TaskData task) {
		SimulatorType simulatorType = task.calculateTaskSimulatorType();
		SimulatorFactory simulatorFactory = instancesByType.get(simulatorType);
		if (simulatorFactory == null) {
			simulatorFactory = constructFactory(simulatorType);
			instancesByType.put(simulatorType, simulatorFactory);
			return simulatorFactory;
		}
		return simulatorFactory;
	}

	private static SimulatorFactory constructFactory(SimulatorType simulatorType) {
		if (simulatorType == TaskSimulatorType.SystemDynamics) {
			return new SystemDynamicsSimulatorFactory();
		}
		return null;
	}
}
