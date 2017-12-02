package edu.wesimulated.firstapp.simulation.domain.mywork.role;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javafx.util.Pair;
import edu.wesimulated.firstapp.model.TaskNeedType;
import edu.wesimulated.firstapp.simulation.domain.Role;
import edu.wesimulated.firstapp.simulation.domain.TaskCharacteristic;
import edu.wesimulated.firstapp.simulation.domain.worktype.WorkType;
import edu.wesimulated.firstapp.simulation.stochastic.EntryValue;
import edu.wesimulated.firstapp.simulation.stochastic.EntryValue.Type;
import edu.wesimulated.firstapp.simulation.stochastic.ParametricAlgorithm;
import edu.wesimulated.firstapp.simulation.stochastic.StochasticVar;

public class Task extends edu.wesimulated.firstapp.simulation.domain.Task {

	public List<WorkDone> allWorkDone;

	public long findDurationOfRecoverFocus(RoleSimulator roleSimulator) {
		ParametricAlgorithm durationOfRecoverFocus = ParametricAlgorithm.buildParametricAlgorithmForVar(StochasticVar.TIME_TO_FOCUS);
		durationOfRecoverFocus.considerSingleValue(new Pair<>(TaskCharacteristic.TIME_SINCE_LAST_TIME, new EntryValue(Type.LONG, roleSimulator.findTimeSinceLastTimeTask())));
		durationOfRecoverFocus.considerSingleValue(new Pair<>(TaskCharacteristic.TIME_IN_TASK, new EntryValue(Type.LONG, roleSimulator.findTimeInTask())));
		durationOfRecoverFocus.consider(roleSimulator.getCurrentTask());
		durationOfRecoverFocus.consider(roleSimulator.getCurrentWorkType());
		return durationOfRecoverFocus.findSample().getPrediction().getValue().longValue();
	}

	public long findDurationOfWorkSlab(RoleSimulator roleSimulator) {
		ParametricAlgorithm timeOfWorkSlab = ParametricAlgorithm.buildParametricAlgorithmForVar(StochasticVar.DURATION_WORK_SLAB);
		// TODO The person will have a list of all interruptions it has had
		// given the priority of this task and the ones that has interrupted it
		// The duration of this task could be calculated
		return timeOfWorkSlab.findSample().getPrediction().getValue().longValue();
	}
	
	public void increaseWorkDone(long duration, TaskNeedType taskNeed, Date when) {
		Number currentWorkDone = this.getWorkDoneInHoursPerTaskNeed().get(taskNeed);
		this.getWorkDoneInHoursPerTaskNeed().put(taskNeed, new Long(duration + currentWorkDone.longValue()));
		if (when == null) {
			// this.hlaTask.registerWorkToDo(new Work(duration), new
			// DateLogicalTime(when));
			// FIXME register pending changes to send to hla
		}
	}

	public long getWorkDone(WorkType currentWorkType, Role role, Person person) {
		Collection<WorkDone> workDone = this.findWorkDoneBy(currentWorkType, role, person);
		return workDone.stream().mapToLong((workDoneOfStream) -> {
			return workDoneOfStream.getAmount();
		}).sum();
	}

	public void registerWorkDone(long l, WorkType workType, Role role, Person person, Date date) {
		this.allWorkDone.add(new WorkDone(l, role, person, workType));
	}

	private Collection<WorkDone> findWorkDoneBy(WorkType workType, Role role, Person person) {
		return allWorkDone.stream().filter((workDoneToFilter) -> workDoneToFilter.getWorkType() == workType && 
				workDoneToFilter.getRole() == role && 
				workDoneToFilter.getPerson() == person
		).collect(Collectors.toList());
	}
}
