package edu.wesimulated.firstapp.simulation;

import java.util.Date;

import com.wesimulated.simulationmotor.des.BOperation;

import edu.wesimulated.firstapp.simulation.domain.Task;
import edu.wesimulated.firstapp.simulation.domain.worktype.TypeOfWork;
import edu.wesimulated.firstapp.simulation.stochastic.classifier.TaskClassSelectorFactory;
import edu.wesimulated.firstapp.simulation.stochastic.classifier.TypeOfWorkSelector;

public class WorkSlabEnd implements BOperation {

	private RoleSimulator simulator;
	private Date when;
	private long duration;

	public WorkSlabEnd(Date endOfSlab, long duration) {
		this.when = endOfSlab;
		this.duration = duration;
	}

	@Override
	public void doAction() {
		// TODO Auto-generated method stub
		this.simulator.getPerson().increaseExperience();
		this.simulator.getCurrentTask().increaseWorkDone(this.duration, this.simulator.getRole(), this.simulator.getExecutor().getClock().getCurrentDate());
		if (this.simulator.getCurrentTask().isCompleted(this.simulator.getRole())) {
			Task taskFromNextSlab = null; // TODO find another task to work in
											// the project
			this.simulator.setCurrentTask(taskFromNextSlab);
		}
		TypeOfWork typeOfWork = this.selectTypeOfWorkForNextOperation();
		this.simulator.addCOperation(new WorkSlabStart(this.simulator, typeOfWork));
		this.simulator.getPerson().setAvailable(true);
	}

	@Override
	public Date getStartTime() {
		return this.when;
	}

	private TypeOfWork selectTypeOfWorkForNextOperation() {
		TypeOfWorkSelector selector = TaskClassSelectorFactory.buildFactory();
		selector.consider(this.simulator.getCurrentTask());
		selector.consider(this.simulator.getPerson());
		selector.consider(this.simulator.getRole());
		selector.consider(this.simulator.getProject());
		TypeOfWork typeOfWork = selector.selectTypeOfWorkToContinue();
		return typeOfWork;
	}

}
