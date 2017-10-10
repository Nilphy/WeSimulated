package edu.wesimulated.firstapp.simulation.domain.worktype;

import java.util.Date;
import java.util.Map;

import com.wesimulated.simulationmotor.DateUtils;
import com.wesimulated.simulationmotor.des.State;

import edu.wesimulated.firstapp.simulation.domain.Characteristic;
import edu.wesimulated.firstapp.simulation.domain.avature.role.RoleSimulator;
import edu.wesimulated.firstapp.simulation.stochastic.EntryValue;

public class DevelopSoftware extends WorkType implements State {

	public DevelopSoftware(RoleSimulator roleSimulator) {
		super(roleSimulator);
	}
	
	public void applyEffects(Date start, Date end) {
		int minutesWorked = DateUtils.calculateDifferenceInMinutes(end, start);
		this.getRoleSimulator().getPerson().increaseExperience(minutesWorked, this);
		/* TODO se mandan horas o se manda trabajo ya procesado? sería mejor mandar horas para que
		los simuladores sean más independientes, porque si no la tarea no sabe como está el 
		simulador del trabajador calculando el work done
		this.simulator.getCurrentTask().increaseWorkDone(this.duration, this.simulator.getRole(), this.simulator.getExecutor().getClock().getCurrentDate());*/
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
