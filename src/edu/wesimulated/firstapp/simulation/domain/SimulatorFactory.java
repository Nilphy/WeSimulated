package edu.wesimulated.firstapp.simulation.domain;

import java.util.Map;

import edu.wesimulated.firstapp.model.SimulationEntity;
import edu.wesimulated.firstapp.simulation.RoleSimulatorBuilder.RoleSimulatorType;
import edu.wesimulated.firstapp.simulation.SimulatorType;
import edu.wesimulated.firstapp.simulation.TaskSimulatorBuilder.TaskSimulatorType;
import edu.wesimulated.firstapp.simulation.domain.Identifiable.IdentifiableType;
import edu.wesimulated.firstapp.simulation.domain.mywork.highlyinterruptiblerole.HighlyInterruptibleRoleSimulatorFactory;
import edu.wesimulated.firstapp.simulation.domain.mywork.role.RoleSimulatorFactory;
import edu.wesimulated.firstapp.simulation.domain.mywork.task.SystemDynamicsSimulatorFactory;

public abstract class SimulatorFactory {

	private static Map<SimulatorType, SimulatorFactory> instancesByType;
	private PopulatablesPool populatablesPool;

	public abstract Person makePerson();

	public abstract Task makeTask();

	public abstract Project makeProject();

	public abstract Role makeRole();

	public static SimulatorFactory getInstance(SimulationEntity entity) {
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

	public Populatable registerSimulationEntity(SimulationEntity simulationEntity) {
		Populatable populatable = this.getPopulatablesPool().getPopulatable(simulationEntity.getType(), simulationEntity.getIdentifier());
		if (populatable == null) {
			populatable = this.makePopulatable(simulationEntity.getType());
			populatable.populateFrom(simulationEntity, this);
			this.getPopulatablesPool().addPopulatable(populatable);
		}
		return populatable;
	}

	private Populatable makePopulatable(IdentifiableType type) {
		switch (type) {
		case PERSON:
			return this.makePerson();
		case TASK:
			return this.makeTask();
		case PROJECT:
			return this.makeProject();
		case ROLE:
			return this.makeRole();
		default:
			break;
		} 
		return null;
	}

	public PopulatablesPool getPopulatablesPool() {
		if (this.populatablesPool == null) {
			this.populatablesPool = new PopulatablesPool();
		}
		return this.populatablesPool;
	}
}
