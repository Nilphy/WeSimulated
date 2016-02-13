package edu.wesimulated.firstapp.simulation;

import java.util.Date;

import com.wesimulated.simulation.runparameters.TaskCompletedEndCondition;
import com.wesimulated.simulationmotor.DateUtils;
import com.wesimulated.simulationmotor.des.threefaseaproach.ThreePhaseExecutor;

import edu.wesimulated.firstapp.model.Person;

public class PersonSimulatorBuilder {

	public static PersonRolSimulator build(Person person, HLAPerson hlaPerson, Date startDate) {
		ProjectSimulator projectSimulator = ProjectSimulator.getInstance();
		ThreePhaseExecutor executor = new ThreePhaseExecutor(new TaskCompletedEndCondition(projectSimulator));
		PersonRolSimulator personRolSimulator = new PersonRolSimulator(executor, person, hlaPerson);
		personRolSimulator.addBOperation(new Work(personRolSimulator, DateUtils.convertToStartOfNextLabDay(startDate)));
		return personRolSimulator;
	}

}
