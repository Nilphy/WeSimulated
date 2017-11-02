package edu.wesimulated.firstapp.simulation.domain;

import java.util.Map;

import edu.wesimulated.firstapp.model.SimulatedEntity;
import edu.wesimulated.firstapp.simulation.RoleSimulatorBuilder.RoleSimulatorType;
import edu.wesimulated.firstapp.simulation.SimulatorType;
import edu.wesimulated.firstapp.simulation.TaskSimulatorBuilder.TaskSimulatorType;
import edu.wesimulated.firstapp.simulation.domain.mywork.highlyinterruptiblerole.HighlyInterruptibleRoleSimulatorFactory;
import edu.wesimulated.firstapp.simulation.domain.mywork.role.RoleSimulatorFactory;
import edu.wesimulated.firstapp.simulation.domain.mywork.task.SystemDynamicsSimulatorFactory;

public abstract class SimulatorFactory {

	private static Map<SimulatorType, SimulatorFactory> instancesByType;

	public abstract Person makePerson();

	public abstract Task makeTask();

	public static SimulatorFactory getInstance(SimulatedEntity entity) {
		SimulatorType simulatorType = entity.calculateSimulatorType();
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
		} else if (simulatorType == RoleSimulatorType.MY_WORK) {
			return new RoleSimulatorFactory();
		} else if (simulatorType == RoleSimulatorType.HIGHLY_INTERRUPTIBLE) {
			return new HighlyInterruptibleRoleSimulatorFactory();
		} else {
			throw new IllegalStateException();
		}
	}
}
