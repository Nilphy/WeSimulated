package edu.wesimulated.firstapp.simulation.domain.worktype;

import java.util.Date;

import edu.wesimulated.firstapp.simulation.RoleSimulator;

public class SetupWorkbench extends TypeOfWork {

	public SetupWorkbench(RoleSimulator roleSimulator) {
		super(roleSimulator);
	}
	
	public void applyEffects(Date start, Date end) {
		long timeSpent = end.getTime()- start.getTime();
		this.getRoleSimulator().getPerson().increaseExperienceWithWorkbenchTools(this.getRoleSimulator().getCurrentTask(), timeSpent);
	}

}
