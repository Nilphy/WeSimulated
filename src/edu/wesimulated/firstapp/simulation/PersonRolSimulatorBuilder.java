package edu.wesimulated.firstapp.simulation;

import java.util.Date;

import com.wesimulated.simulation.runparameters.TaskCompletedEndCondition;
import com.wesimulated.simulationmotor.DateUtils;
import com.wesimulated.simulationmotor.des.threefaseaproach.ThreePhaseExecutor;

import edu.wesimulated.firstapp.model.PersonData;
import edu.wesimulated.firstapp.simulation.hla.HlaPerson;

public class PersonRolSimulatorBuilder {

	public static PersonRolSimulator build(PersonData person, HlaPerson hlaPerson, Date startDate) {
		ProjectSimulator projectSimulator = ProjectSimulator.getInstance();
		ThreePhaseExecutor executor = new ThreePhaseExecutor(new TaskCompletedEndCondition(projectSimulator));
		PersonRolSimulator personRolSimulator = new PersonRolSimulator(executor, person, hlaPerson);
		return personRolSimulator;
	}

}
