package edu.wesimulated.firstapp.simulation.domain.worktype;

import java.util.Date;

import edu.wesimulated.firstapp.simulation.RoleSimulator;

public abstract class DevelopSoftware extends TypeOfWork {

	public DevelopSoftware(RoleSimulator roleSimulator) {
		super(roleSimulator);
	}
	
	public void applyEffects(Date start, Date end) {
		this.getRoleSimulator().getPerson().increaseExperience();
		// TODO define which params has to be sent to the increaseWorkDone method
		// this.simulator.getCurrentTask().increaseWorkDone(this.duration, this.simulator.getRole(), this.simulator.getExecutor().getClock().getCurrentDate());
	}
}
