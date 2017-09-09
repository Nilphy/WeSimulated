package edu.wesimulated.firstapp.simulation.stochastic;

import java.util.HashMap;
import java.util.Map;

import edu.wesimulated.firstapp.simulation.domain.Characteristic;
import edu.wesimulated.firstapp.simulation.domain.PersonCharacteristic;



public interface NumericallyModeledEntity {

	public Map<Characteristic, EntryValue> extractValues();
	
	public static Map<Characteristic, EntryValue> embedPersonCharacteristic(NumericallyModeledEntity otherEntity, String prefix) {
		Map<Characteristic, EntryValue> embeddedMap = new HashMap<>();
		otherEntity.extractValues().forEach((key, value) -> {
			embeddedMap.put(PersonCharacteristic.valueOf(prefix + key.toString()), value);
		});
		return embeddedMap;
	}
}
