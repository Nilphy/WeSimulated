package edu.wesimulated.firstapp.simulation.domain.worktype;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import edu.wesimulated.firstapp.simulation.domain.Characteristic;
import edu.wesimulated.firstapp.simulation.domain.TaskCharacteristic;
import edu.wesimulated.firstapp.simulation.domain.mywork.role.RoleSimulator;
import edu.wesimulated.firstapp.simulation.stochastic.EntryValue;
import edu.wesimulated.firstapp.simulation.stochastic.EntryValue.Type;

public class SetupWorkbench extends WorkType {

	public SetupWorkbench(RoleSimulator roleSimulator) {
		super(roleSimulator);
	}
	
	public void applyEffects(Date start, Date end) {
		long timeSpent = end.getTime()- start.getTime();
		this.getRoleSimulator().getPerson().increaseExperienceWithWorkbenchTools(this.getRoleSimulator().getCurrentTask(), timeSpent);
	}

	@Override
	public Map<Characteristic, EntryValue> extractValues() {
		Map<Characteristic, EntryValue> values = new HashMap<>();
		values.put(TaskCharacteristic.WORK_TYPE, new EntryValue(Type.String, this.getName()));
		return values;
	}

	@Override
	public String getName() {
		return "SetupWorkbench";
	}
}
