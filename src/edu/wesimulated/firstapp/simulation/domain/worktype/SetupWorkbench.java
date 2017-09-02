package edu.wesimulated.firstapp.simulation.domain.worktype;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import edu.wesimulated.firstapp.simulation.domain.avature.role.RoleSimulator;
import edu.wesimulated.firstapp.simulation.stochastic.EntryValue;
import edu.wesimulated.firstapp.simulation.stochastic.EntryValue.Type;
import edu.wesimulated.firstapp.simulation.stochastic.StochasticAttribute;
import edu.wesimulated.firstapp.simulation.stochastic.Attribute;

public class SetupWorkbench extends WorkType {

	public SetupWorkbench(RoleSimulator roleSimulator) {
		super(roleSimulator);
	}
	
	public void applyEffects(Date start, Date end) {
		long timeSpent = end.getTime()- start.getTime();
		this.getRoleSimulator().getPerson().increaseExperienceWithWorkbenchTools(this.getRoleSimulator().getCurrentTask(), timeSpent);
	}

	@Override
	public Map<StochasticAttribute, EntryValue> extractValues() {
		Map<StochasticAttribute, EntryValue> values = new HashMap<>();
		values.put(Attribute.WorkTypeName, new EntryValue(Type.String, this.getName()));
		return values;
	}

	@Override
	public String getName() {
		return "SetupWorkbench";
	}
}
