package edu.wesimulated.firstapp.simulation.domain.worktype;

import java.util.Date;
import java.util.Map;

import com.wesimulated.simulationmotor.DateUtils;
import com.wesimulated.simulationmotor.des.State;

import edu.wesimulated.firstapp.simulation.domain.Characteristic;
import edu.wesimulated.firstapp.simulation.domain.mywork.role.RoleSimulator;
import edu.wesimulated.firstapp.simulation.stochastic.EntryValue;

public class DevelopSoftware extends WorkType implements State {

	public DevelopSoftware(RoleSimulator roleSimulator) {
		super(roleSimulator);
	}
	
	public void applyEffects(Date start, Date end) {
		int minutesWorked = DateUtils.calculateDifferenceInMinutes(end, start);
		RoleSimulator sim = this.getRoleSimulator();
		sim.getPerson().increaseExperience(minutesWorked, this);
		sim.getCurrentTask().registerWorkDone(minutesWorked * 60l * 1000l, 
				sim.getCurrentWorkType(),
				sim.getRole(),
				sim.getPerson(),
				sim.getCurrentDate());
	}

	@Override
	public Map<Characteristic, EntryValue> extractValues() {
		// TODO Implement
		return null;
	}

	@Override
	public String getName() {
		return "DEVELOPT_SOFTWARE";
	}
}
