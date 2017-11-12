package edu.wesimulated.firstapp.simulation.domain;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import edu.wesimulated.firstapp.simulation.stochastic.EntryValue;
import edu.wesimulated.firstapp.simulation.stochastic.EntryValue.Type;
import edu.wesimulated.firstapp.simulation.stochastic.NumericallyModeledEntity;

/**
 * Most of the entities can be modeled by characteristics... to give more
 * flexibility to the code and avoid defining a getter and a setter for each of
 * it, I'me defining this map container.
 * 
 * @author Carolina
 *
 */
public class Profile implements NumericallyModeledEntity {

	Map<Characteristic, EntryValue> characteristics;

	public Profile() {
	}

	public Profile(Map<Characteristic, EntryValue> characteristics) {
		this.characteristics = characteristics;
	}

	public EntryValue get(Characteristic characteristic) {
		return this.characteristics.get(characteristic);
	}

	public void scaleDown(Characteristic characteristic, double deterioration) {
		Number originalValue = this.characteristics.get(characteristic).getNumber();
		EntryValue newValue = new EntryValue(Type.FLOAT, originalValue.doubleValue() * deterioration);
		this.characteristics.put(characteristic, newValue);
	}

	public void scaleUp(Characteristic characteristic, double scale) {
		Number originalValue = this.characteristics.get(characteristic).getNumber();
		EntryValue newValue = new EntryValue(Type.FLOAT, originalValue.doubleValue() * scale + originalValue.doubleValue());
		this.characteristics.put(characteristic, newValue);
	}

	public void increase(Characteristic characteristic, double amount) {
		Number originalValue = this.characteristics.get(characteristic).getNumber();
		EntryValue newValue = new EntryValue(Type.FLOAT, originalValue.doubleValue() + amount);
		this.characteristics.put(characteristic, newValue);
	}

	public void set(Characteristic characteristic, EntryValue value) {
		this.characteristics.put(characteristic, value);
	}

	public Map<Characteristic, EntryValue> extractValues() {
		Map<Characteristic, EntryValue> returnValue = new HashMap<Characteristic, EntryValue>();
		for (Entry<Characteristic, EntryValue> entry : this.characteristics.entrySet()) {
			returnValue.put(entry.getKey(), entry.getValue());
		}
		return returnValue;
	}

}
