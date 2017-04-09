package edu.wesimulated.firstapp.simulation.domain.worktype;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import edu.wesimulated.firstapp.simulation.RoleSimulator;
import edu.wesimulated.firstapp.simulation.stochastic.EntryValue;
import edu.wesimulated.firstapp.simulation.stochastic.EntryValue.Type;
import edu.wesimulated.firstapp.simulation.stochastic.StochasticVariableName;
import edu.wesimulated.firstapp.simulation.stochastic.VariableName;

public class SetupWorkbench extends TypeOfWork {

	public SetupWorkbench(RoleSimulator roleSimulator) {
		super(roleSimulator);
	}
	
	public void applyEffects(Date start, Date end) {
		long timeSpent = end.getTime()- start.getTime();
		this.getRoleSimulator().getPerson().increaseExperienceWithWorkbenchTools(this.getRoleSimulator().getCurrentTask(), timeSpent);
	}

	@Override
	public Map<StochasticVariableName, EntryValue> extractValues() {
		Map<StochasticVariableName, EntryValue> values = new HashMap<>();
		values.put(VariableName.WorkTypeName, new EntryValue(Type.String, this.getName()));
		return values;
	}

	@Override
	public String getName() {
		return "TypeOfWork";
	}
}
