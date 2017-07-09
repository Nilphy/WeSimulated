package edu.wesimulated.firstapp.simulation.domain.avature.developer;

import edu.wesimulated.firstapp.simulation.RoleSimulator;
import edu.wesimulated.firstapp.simulation.domain.Task;
import edu.wesimulated.firstapp.simulation.stochastic.ParametricAlgorithm;
import edu.wesimulated.firstapp.simulation.stochastic.StochasticVar;

public class AvatureDeveloperTask extends Task {

	public long findDurationOfRecoverFocus(RoleSimulator roleSimulator) {
		// TODO Auto-generated method stub
		return 0;
	}

	public long findDurationOfWorkSlab(RoleSimulator roleSimulator) {
		ParametricAlgorithm timeOfWorkSlab = ParametricAlgorithm.buildParametricAlgorithmForVar(StochasticVar.TimeOfWorkSlab);
		// TODO The person will have a list of all interruptions it has had
		// given the priority of this task and the ones that has interrupted it
		// The duration of this task could be calculated
		return timeOfWorkSlab.findSample().getPrediction().getValue().longValue();
	}

}
