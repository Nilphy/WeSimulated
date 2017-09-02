package edu.wesimulated.firstapp.simulation.domain.avature.role;

import javafx.util.Pair;
import edu.wesimulated.firstapp.simulation.domain.Task;
import edu.wesimulated.firstapp.simulation.stochastic.EntryValue;
import edu.wesimulated.firstapp.simulation.stochastic.EntryValue.Type;
import edu.wesimulated.firstapp.simulation.stochastic.ParametricAlgorithm;
import edu.wesimulated.firstapp.simulation.stochastic.StochasticVar;
import edu.wesimulated.firstapp.simulation.stochastic.Attribute;

public class AvatureDeveloperTask extends Task {

	public long findDurationOfRecoverFocus(RoleSimulator roleSimulator) {
		ParametricAlgorithm durationOfRecoverFocus = ParametricAlgorithm.buildParametricAlgorithmForVar(StochasticVar.TimeToFocus);
		durationOfRecoverFocus.considerSingleValue(new Pair<>(Attribute.TimeSinceLastTimeTask, new EntryValue(Type.Long, roleSimulator.findTimeSinceLastTimeTask())));
		durationOfRecoverFocus.considerSingleValue(new Pair<>(Attribute.TimeInThisMonthTask, new EntryValue(Type.Long, roleSimulator.findTimeInThisMonthTask())));
		durationOfRecoverFocus.consider(roleSimulator.getCurrentTask());
		durationOfRecoverFocus.consider(roleSimulator.getCurrentWorkType());
		durationOfRecoverFocus.considerAll(roleSimulator.getCurrentTask().getAllRelatedNumericallyModeledEntities());
		return durationOfRecoverFocus.findSample().getPrediction().getValue().longValue();
	}

	public long findDurationOfWorkSlab(RoleSimulator roleSimulator) {
		ParametricAlgorithm timeOfWorkSlab = ParametricAlgorithm.buildParametricAlgorithmForVar(StochasticVar.TimeOfWorkSlab);
		// TODO The person will have a list of all interruptions it has had
		// given the priority of this task and the ones that has interrupted it
		// The duration of this task could be calculated
		return timeOfWorkSlab.findSample().getPrediction().getValue().longValue();
	}

}
