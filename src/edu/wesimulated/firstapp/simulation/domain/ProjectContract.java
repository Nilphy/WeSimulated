package edu.wesimulated.firstapp.simulation.domain;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.wesimulated.simulationmotor.DateUtils;

import edu.wesimulated.firstapp.simulation.stochastic.EntryValue;
import edu.wesimulated.firstapp.simulation.stochastic.EntryValue.Type;
import edu.wesimulated.firstapp.simulation.stochastic.NumericallyModeledEntity;

public class ProjectContract implements NumericallyModeledEntity {

	private Collection<Technology> tecnologies;
	private Quality quality;
	private Date initialDate;
	private Date finalDate;

	@Override
	public Map<Characteristic, EntryValue> extractValues() {
		long length = DateUtils.calculateDifferenceInDays(this.initialDate, this.finalDate);
		Map<Characteristic, EntryValue> values = new HashMap<>();
		values.putAll(this.quality.extractValues());
		values.put(ProjectCharacteristic.LENGTH, new EntryValue(Type.Long, length));
		values.put(ProjectCharacteristic.AMOUNT_TECHNOLOGIES, new EntryValue(Type.Long, this.tecnologies.size()));
		return values;
	}

	public Collection<Technology> getTecnologies() {
		return tecnologies;
	}

	public void setTecnologies(Collection<Technology> tecnologies) {
		this.tecnologies = tecnologies;
	}

	public Quality getQuality() {
		return quality;
	}

	public void setQuality(Quality quality) {
		this.quality = quality;
	}

	public Date getInitialDate() {
		return initialDate;
	}

	public void setInitialDate(Date initialDate) {
		this.initialDate = initialDate;
	}

	public Date getFinalDate() {
		return finalDate;
	}

	public void setFinalDate(Date finalDate) {
		this.finalDate = finalDate;
	}
}
