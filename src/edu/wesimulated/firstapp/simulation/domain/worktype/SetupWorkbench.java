package edu.wesimulated.firstapp.simulation.domain.worktype;

import java.util.Date;
import java.util.Map;

import edu.wesimulated.firstapp.simulation.RoleSimulator;
import edu.wesimulated.firstapp.simulation.stochastic.EntryValue;
import edu.wesimulated.firstapp.simulation.stochastic.StochasticVariableName;

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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getName() {
		return "TypeOfWork";
	}
}
