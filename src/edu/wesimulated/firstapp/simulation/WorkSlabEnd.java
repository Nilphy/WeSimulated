package edu.wesimulated.firstapp.simulation;

import java.util.Date;

import com.wesimulated.simulationmotor.des.BOperation;

import edu.wesimulated.firstapp.simulation.domain.Task;
import edu.wesimulated.firstapp.simulation.domain.worktype.TypeOfWork;
import edu.wesimulated.firstapp.simulation.stochastic.classifier.TaskClassSelectorFactory;
import edu.wesimulated.firstapp.simulation.stochastic.classifier.TypeOfWorkSelector;

public class WorkSlabEnd implements BOperation {

	private RoleSimulator simulator;
	private Date startOfSlab;
	private Date endOfSlab;

	public WorkSlabEnd(RoleSimulator roleSimulator, Date startOfSlab, Date endOfSlab) {
		this.startOfSlab = startOfSlab;
		this.endOfSlab = endOfSlab;
		this.simulator = roleSimulator;
	}

	@Override
	public void doAction() {
		// TODO Auto-generated method stub
		this.simulator.getCurrentTypeOfWork().applyEffects(startOfSlab, endOfSlab);
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
		return this.endOfSlab;
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
