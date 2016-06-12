package edu.wesimulated.firstapp.simulation;

import com.wesimulated.simulation.runparameters.TaskCompletedEndCondition;
import com.wesimulated.simulationmotor.des.threefaseaproach.ThreePhaseExecutor;

import edu.wesimulated.firstapp.simulation.domain.Person;
import edu.wesimulated.firstapp.simulation.domain.Project;

public class PersonRolSimulatorBuilder {

	public static PersonRolSimulator build(Person person, Project project) {
		ThreePhaseExecutor executor = new ThreePhaseExecutor(new TaskCompletedEndCondition(project));
		PersonRolSimulator personRolSimulator = new PersonRolSimulator(executor, person);
		return personRolSimulator;
	}

}
