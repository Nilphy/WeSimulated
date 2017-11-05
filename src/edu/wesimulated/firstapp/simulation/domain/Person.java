package edu.wesimulated.firstapp.simulation.domain;

import hla.rti1516e.AttributeHandleValueMap;
import hla.rti1516e.ObjectInstanceHandle;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.wesimulated.simulationmotor.des.Resource;

import edu.wesimulated.firstapp.model.SimulationEntity;
import edu.wesimulated.firstapp.simulation.hla.HlaPerson;
import edu.wesimulated.firstapp.simulation.stochastic.EntryValue;
import edu.wesimulated.firstapp.simulation.stochastic.EntryValue.Type;
import edu.wesimulated.firstapp.simulation.stochastic.NumericallyModeledEntity;

public class Person implements Resource, NumericallyModeledEntity, Populatable {

	private String id;
	private boolean available;
	private HlaPerson hlaPerson;
	private Task currentTask;
	private Profile profile;
	private Project project;
	private Collection<Team> teams;
	private Date dateLastUpdate;

	public static Person searchPersonById(Collection<Person> people, String id) {
		List<Person> peopleWithTheSameId = people.stream()
				.filter((person) -> person.getIdentifier().compareTo(id) == 0)
				.collect(Collectors.toList());
		if (peopleWithTheSameId.size() == 1) {
			return peopleWithTheSameId.get(0);
		}
		return null;
	}
	
	public ObjectInstanceHandle getHlaObjectInstanceHandle() {
		return this.getHlaPerson().getObjectInstanceHandle();
	}

	public String getName() {
		return this.getHlaPerson().getObjectInstanceName();
	}

	public void reflectAttributeValues(AttributeHandleValueMap attributeValues, Date when) {
		this.hlaPerson.reflectAttributeValues(attributeValues);
		this.setDateLastUpdate(when);
	}

	@Override
	public void setAvailable(boolean available) {
		this.available = available;
	}

	@Override
	public boolean isAvailable() {
		return available;
	}

	public void setHlaPerson(HlaPerson hlaPerson) {
		this.hlaPerson = hlaPerson;
	}

	public HlaPerson getHlaPerson() {
		return hlaPerson;
	}

	public Date findNextAvailableDate() {
		// TODO find hour to start working today
		// TODO find current hour
		// TODO if not reached the end of the day return now
		// TODO if reached return first laboral day tomorrow
		return null;
	}

	@Override
	public Map<Characteristic, EntryValue> extractValues() {
		Map<Characteristic, EntryValue> values = new HashMap<>();
		values.putAll(this.profile.extractValues());
		values.put(PersonCharacteristic.IS_AVAILABLE, new EntryValue(available));
		values.put(PersonCharacteristic.AMOUNT_OF_TEAMS, new EntryValue(Type.Long, this.teams.size()));
		// FIXME ¿? consider task and project
		return values;
	}

	protected boolean isWorginWithMe(Person person) {
		return this.currentTask.isPersonAssigned(person);
	}

	protected Project getProject() {
		return this.project;
	}

	public Profile getProfile() {
		return this.profile;
	}

	public Date getDateLastUpdate() {
		return this.dateLastUpdate;
	}

	private void setDateLastUpdate(Date dateLastUpdate) {
		this.dateLastUpdate = dateLastUpdate;
	}

	public Collection<Team> getTeams() {
		return teams;
	}

	public void setTeams(Collection<Team> teams) {
		this.teams = teams;
	}

	public String getIdentifier() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Person other = (Person) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public IdentifiableType getType() {
		return IdentifiableType.PERSON;
	}

	@Override
	public void populateFrom(SimulationEntity simulationEntity, SimulatorFactory factory) {
		// TODO Auto-generated method stub
		
	}
}
