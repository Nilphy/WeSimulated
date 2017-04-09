package edu.wesimulated.firstapp.simulation.domain;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import edu.wesimulated.firstapp.simulation.stochastic.EntryValue;
import edu.wesimulated.firstapp.simulation.stochastic.EntryValue.Type;
import edu.wesimulated.firstapp.simulation.stochastic.NumericallyModeledEntity;
import edu.wesimulated.firstapp.simulation.stochastic.StochasticVariableName;

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

	public Map<StochasticVariableName, EntryValue> extractValues() {
		Map<StochasticVariableName, EntryValue> returnValue = new HashMap<StochasticVariableName, EntryValue>();
		for (Entry<Characteristic, EntryValue> entry : this.characteristics.entrySet()) {
			returnValue.put(entry.getKey(), entry.getValue());
		}
		return returnValue;
	}

}
