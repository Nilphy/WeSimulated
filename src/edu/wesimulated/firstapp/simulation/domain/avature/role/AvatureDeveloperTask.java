package edu.wesimulated.firstapp.simulation.domain.avature.role;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javafx.util.Pair;
import edu.wesimulated.firstapp.simulation.domain.Role;
import edu.wesimulated.firstapp.simulation.domain.Task;
import edu.wesimulated.firstapp.simulation.domain.TaskCharacteristic;
import edu.wesimulated.firstapp.simulation.domain.worktype.WorkType;
import edu.wesimulated.firstapp.simulation.stochastic.EntryValue;
import edu.wesimulated.firstapp.simulation.stochastic.EntryValue.Type;
import edu.wesimulated.firstapp.simulation.stochastic.ParametricAlgorithm;
import edu.wesimulated.firstapp.simulation.stochastic.StochasticVar;

public class AvatureDeveloperTask extends Task {

	public List<WorkDone> allWorkDone;

	public long findDurationOfRecoverFocus(RoleSimulator roleSimulator) {
		ParametricAlgorithm durationOfRecoverFocus = ParametricAlgorithm.buildParametricAlgorithmForVar(StochasticVar.TimeToFocus);
		durationOfRecoverFocus.considerSingleValue(new Pair<>(TaskCharacteristic.TimeSinceLastTimeTask, new EntryValue(Type.Long, roleSimulator.findTimeSinceLastTimeTask())));
		durationOfRecoverFocus.considerSingleValue(new Pair<>(TaskCharacteristic.TimeInTask, new EntryValue(Type.Long, roleSimulator.findTimeInTask())));
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

	public long getWorkDone(WorkType currentWorkType, Role role, RolePerson person) {
		Collection<WorkDone> workDone = this.findWorkDoneBy(currentWorkType, role, person);
		return workDone.stream().mapToLong((workDoneOfStream) -> {
			return workDoneOfStream.getAmount();
		}).sum();
	}

	public void registerWorkDone(long l, WorkType workType, Role role, RolePerson person, Date date) {
		this.allWorkDone.add(new WorkDone(l, role, person, workType));
	}

	private Collection<WorkDone> findWorkDoneBy(WorkType workType, Role role, RolePerson person) {
		return allWorkDone.stream().filter((workDoneToFilter) -> workDoneToFilter.getWorkType() == workType && 
				workDoneToFilter.getRole() == role && 
				workDoneToFilter.getPerson() == person
		).collect(Collectors.toList());
	}
}
