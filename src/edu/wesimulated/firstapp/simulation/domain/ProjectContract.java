package edu.wesimulated.firstapp.simulation.domain;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.wesimulated.simulationmotor.DateUtils;

import edu.wesimulated.firstapp.simulation.stochastic.EntryValue;
import edu.wesimulated.firstapp.simulation.stochastic.EntryValue.Type;
import edu.wesimulated.firstapp.simulation.stochastic.NumericallyModeledEntity;

public class ProjectContract implements NumericallyModeledEntity {
	
	private Map<Integer, Tecnology> tecnologies;
	private Quality quality;
	private Cost budget;
	private Date initialDate;
	private Date finalDate;

	@Override
	public Map<Characteristic, EntryValue> extractValues() {
		Map<Characteristic, EntryValue> values = new HashMap<>();
		values.putAll(this.quality.extractValues());
		values.putAll(this.budget.extractValues());
		values.put(ProjectCharacteristic.LENGTH, new EntryValue(Type.Long, DateUtils.calculateDifferenceInDays(this.initialDate, this.finalDate)));
		values.put(ProjectCharacteristic.AMOUNT_TECHNOLOGIES, new EntryValue(Type.Long, this.tecnologies.size()));
		return values;
	}
}
