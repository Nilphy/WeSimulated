package edu.wesimulated.firstapp.simulation.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import edu.wesimulated.firstapp.simulation.domain.Identifiable.IdentifiableType;

public class IdentifiablesPool {

	private Collection<Identifiable> identifiables;
	private Map<IdentifiableType, Map<String, Identifiable>> identifiablesById;

	public IdentifiablesPool() {
		this.identifiablesById = new HashMap<>();
		this.identifiables = new ArrayList<>();
	}

	public Identifiable getIdentifiable(IdentifiableType type, String id) {
		if (this.getIdentifiableById().containsKey(type)) {
			Map<String, Identifiable> objects = this.getIdentifiableById().get(type);
			if (objects.containsKey(id)) {
				return objects.get(id);
			}
		}

		return null;
	}

	public void addIdentifiable(Identifiable indentifiable) {
		if (!this.getIdentifiableById().containsKey(indentifiable.getClass())) {
			this.getIdentifiableById().put(indentifiable.getType(), new HashMap<>());
		}
		Map<String, Identifiable> objects = this.getIdentifiableById().get(indentifiable.getClass());
		if (!objects.containsKey(indentifiable.getIdentifier())) {
			objects.put(indentifiable.getIdentifier(), indentifiable);
			this.getIdentifiables().add(indentifiable);
		}

	}

	private Collection<Identifiable> getIdentifiables() {
		return identifiables;
	}

	private Map<IdentifiableType, Map<String, Identifiable>> getIdentifiableById() {
		return identifiablesById;
	}
}
