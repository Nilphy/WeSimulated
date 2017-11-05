package edu.wesimulated.firstapp.simulation.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import edu.wesimulated.firstapp.simulation.domain.Identifiable.IdentifiableType;

public class PopulatablesPool {

	private Collection<Populatable> populatables;
	private Map<IdentifiableType, Map<String, Populatable>> populatablesById;

	public PopulatablesPool() {
		this.populatablesById = new HashMap<>();
		this.populatables = new ArrayList<>();
	}

	public Populatable getPopulatable(IdentifiableType type, String id) {
		if (this.getPopulatableById().containsKey(type)) {
			Map<String, Populatable> objects = this.getPopulatableById().get(type);
			if (objects.containsKey(id)) {
				return objects.get(id);
			}
		}

		return null;
	}

	public void addPopulatable(Populatable populatable) {
		if (!this.getPopulatableById().containsKey(populatable.getClass())) {
			this.getPopulatableById().put(populatable.getType(), new HashMap<>());
		}
		Map<String, Populatable> objects = this.getPopulatableById().get(populatable.getClass());
		if (!objects.containsKey(populatable.getIdentifier())) {
			objects.put(populatable.getIdentifier(), populatable);
			this.getPopulatables().add(populatable);
		}

	}

	private Collection<Populatable> getPopulatables() {
		return populatables;
	}

	private Map<IdentifiableType, Map<String, Populatable>> getPopulatableById() {
		return populatablesById;
	}
}
