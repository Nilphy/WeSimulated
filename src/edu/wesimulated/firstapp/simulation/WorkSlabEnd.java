package edu.wesimulated.firstapp.simulation;

import java.util.Date;

import com.wesimulated.simulationmotor.des.BOperation;

import edu.wesimulated.firstapp.simulation.domain.Task;
import edu.wesimulated.firstapp.simulation.domain.worktype.TypeOfWork;
import edu.wesimulated.firstapp.simulation.stochastic.classifier.TaskClassSelectorFactory;
import edu.wesimulated.firstapp.simulation.stochastic.classifier.TypeOfWorkSelector;

public class WorkSlabEnd implements BOperation {

	private RoleSimulator roleSimulator;
	private Date startOfSlab;
	private Date endOfSlab;

	public WorkSlabEnd(RoleSimulator roleSimulator, Date startOfSlab, Date endOfSlab) {
		this.startOfSlab = startOfSlab;
		this.endOfSlab = endOfSlab;
		this.roleSimulator = roleSimulator;
	}

	@Override
	public void doAction() {
		this.roleSimulator.getCurrentTypeOfWork().applyEffects(startOfSlab, endOfSlab);
		Task taskToWorkIn = this.simulator.getCurrentTask();
		if (this.simulator.getCurrentTask().isCompleted(this.simulator.getRole())) {
			// TODO find another task to work in the project
			taskToWorkIn = this.simulator.getProject().findTaskToWorkOn(endOfSlab, this.simulator.getPerson(), this.simulator.getRole());
		}
		TypeOfWork typeOfWork = this.selectTypeOfWorkForNextOperation();
		// TODO calc min date considering end of laboral day
		Date minDate = null;
		this.roleSimulator.addCOperation(new WorkSlabStart(this.roleSimulator, typeOfWork, nextTask, minDate));
		this.roleSimulator.getPerson().setAvailable(true);
	}

	@Override
	public Date getStartTime() {
		return this.endOfSlab;
	}

	private TypeOfWork selectTypeOfWorkForNextOperation() {
		TypeOfWorkSelector selector = TaskClassSelectorFactory.buildFactory().buildTypeOfWorkSelector();
		selector.consider(this.simulator.getCurrentTask());
		selector.consider(this.simulator.getCurrentTypeOfWork());
		selector.consider(this.simulator.getPerson());
		selector.consider(this.simulator.getRole());
		selector.consider(this.simulator.getProject());
		TypeOfWork typeOfWork = selector.selectTypeOfWorkToContinue();
		return typeOfWork;
	}

}
