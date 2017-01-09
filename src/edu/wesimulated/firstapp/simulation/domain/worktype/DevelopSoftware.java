package edu.wesimulated.firstapp.simulation.domain.worktype;

import java.util.Date;

import com.wesimulated.simulationmotor.des.State;

import edu.wesimulated.firstapp.simulation.RoleSimulator;

public abstract class DevelopSoftware extends TypeOfWork implements State {

	public DevelopSoftware(RoleSimulator roleSimulator) {
		super(roleSimulator);
	}
	
	public void applyEffects(Date start, Date end) {
		
		this.getRoleSimulator().getPerson().increaseExperience();
		// TODO se mandan horas o se manda trabajo ya procesado? sería mejor mandar horas para que
		// los simuladores sean más independientes, porque si no la tarea no sabe como está el 
		// simulador del trabajador calculando el work done
		// this.simulator.getCurrentTask().increaseWorkDone(this.duration, this.simulator.getRole(), this.simulator.getExecutor().getClock().getCurrentDate());
	}
}
