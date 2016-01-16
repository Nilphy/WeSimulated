package edu.wesimulated.firstapp.simulation;

import java.util.Date;

import com.wesimulated.simulation.runparameters.TaskCompletedEndCondition;
import com.wesimulated.simulationmotor.DateUtils;
import com.wesimulated.simulationmotor.operationbased.threefaseaproach.ThreePhaseExecutor;

import edu.wesimulated.firstapp.model.Person;

public class PersonSimulatorBuilder {

	public static PersonSimulator build(Person person, Date startDate) {
		ProjectSimulator projectSimulator = ProjectSimulator.getInstance();
		ThreePhaseExecutor executor = new ThreePhaseExecutor(new TaskCompletedEndCondition(projectSimulator));
		PersonSimulator personSimulator = new PersonSimulator(executor, person);
		personSimulator.addBOperation(new Work(personSimulator, DateUtils.convertToStartOfNextLabDay(startDate)));
		return personSimulator;
	}

}
