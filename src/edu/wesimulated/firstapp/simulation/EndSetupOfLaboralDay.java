package edu.wesimulated.firstapp.simulation;

import java.util.Date;

import com.wesimulated.simulationmotor.des.BOperation;

import edu.wesimulated.firstapp.simulation.domain.TypeOfWork;
import edu.wesimulated.firstapp.simulation.stochastic.classifier.TaskClassSelectorFactory;
import edu.wesimulated.firstapp.simulation.stochastic.classifier.TypeOfWorkSelector;

public class EndSetupOfLaboralDay implements BOperation {

	private Date when;
	private RoleSimulator simulator;
	private Date startTime;

	public EndSetupOfLaboralDay(RoleSimulator roleSimulator, Date startTime, Date endTime) {
		this.when = endTime;
		this.startTime = startTime;
		this.simulator = roleSimulator;
	}

	@Override
	public void doAction() {
		long timeExpended = this.when.getTime() - startTime.getTime();
		this.simulator.getPerson().increaseExperienceWithWorkbenchTools(this.simulator.getCurrentTask(), timeExpended);
		TypeOfWork typeOfWork = selectTypeOfWorkForNextOperation();
		this.simulator.addCOperation(new WorkSlabStart(this.simulator, typeOfWork));
		this.simulator.getPerson().setAvailable(true);
		
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

	@Override
	public Date getStartTime() {
		return this.when;
	}
}
