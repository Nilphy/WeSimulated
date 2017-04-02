package edu.wesimulated.firstapp.simulation.domain;

import java.util.Map;

import edu.wesimulated.firstapp.simulation.stochastic.EntryValue;
import edu.wesimulated.firstapp.simulation.stochastic.EntryValue.Type;

/**
 * Most of the entities can be modeled by characteristics... to give more
 * flexibility to the code and avoid defining a getter and a setter for each of
 * it, I'me defining this map container.
 * 
 * @author Carolina
 *
 */
public class Profile {

	Map<Characteristic, EntryValue> characteristics;

	public Profile(Map<Characteristic, EntryValue> characteristics) {
		this.characteristics = characteristics;
	}

	public EntryValue get(Characteristic characteristic) {
		return this.characteristics.get(characteristic);
	}

	public void scaleDown(Characteristic characteristic, double deterioration) {
		Number originalValue = this.characteristics.get(characteristic).getNumber();
		EntryValue newValue = new EntryValue(Type.Float, originalValue.doubleValue() * deterioration);
		this.characteristics.put(characteristic, newValue);
	}

	public void scaleUp(Characteristic characteristic, double scale) {
		Number originalValue = this.characteristics.get(characteristic).getNumber();
		EntryValue newValue = new EntryValue(Type.Float, originalValue.doubleValue() * scale + originalValue.doubleValue());
		this.characteristics.put(characteristic, newValue);
	}

	public void set(Characteristic characteristic, EntryValue value) {
		this.characteristics.put(characteristic, value);
	}

}
