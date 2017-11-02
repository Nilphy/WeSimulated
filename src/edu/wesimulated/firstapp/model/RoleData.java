package edu.wesimulated.firstapp.model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import edu.wesimulated.firstapp.simulation.RoleSimulatorBuilder.RoleSimulatorType;
import edu.wesimulated.firstapp.simulation.SimulatorType;

public class RoleData implements SimulatedEntity {

	private StringProperty name;
	private BooleanProperty highlyInterruptible;

	public RoleData() {
		this(null);
	}

	public RoleData(String name) {
		this.name = new SimpleStringProperty(name);
	}

	public String getName() {
		return name.get();
	}

	public StringProperty nameProperty() {
		return name;
	}

	public void setName(String name) {
		this.name.set(name);
	}

	public boolean isHighlyInterruptible() {
		return highlyInterruptible.get();
	}
	
	public void setHighlyInterruptible(boolean highlyInterruptible) {
		this.highlyInterruptible.set(highlyInterruptible);
	}

	public BooleanProperty highlyInterruptibleProperty() {
		return this.highlyInterruptible;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		RoleData other = (RoleData) obj;
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}
		return true;
	}

	@Override
	public SimulatorType calculateSimulatorType() {
		if (this.isHighlyInterruptible()) {
			return RoleSimulatorType.HIGHLY_INTERRUPTIBLE;
		} else {
			return RoleSimulatorType.MY_WORK;
		}
	}

}
