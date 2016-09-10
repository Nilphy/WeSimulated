package edu.wesimulated.firstapp.simulation;

import java.util.ArrayList;
import java.util.Collection;

import com.wesimulated.simulationmotor.systemdynamics.Constant;
import com.wesimulated.simulationmotor.systemdynamics.Flow;
import com.wesimulated.simulationmotor.systemdynamics.Module;
import com.wesimulated.simulationmotor.systemdynamics.Source;
import com.wesimulated.simulationmotor.systemdynamics.Stock;

import edu.wesimulated.firstapp.model.WorkType;
import edu.wesimulated.firstapp.simulation.domain.Task;

class WorkModule implements Module {
	public static final String SOURCE_BRUTE_WORK = "SOURCE_BRUTE_WORK";
	public static final String FLOW_POLISHED_WORK = "FLOW_POLISHED_WORK";
	public static final String CONST_EFICIENCY = "CONST_EFICIENCY";
	public static final String STOCK_INTEGRATED_WORK = "STOCK_INTEGRATED_WORK";

	private Task task;
	private WorkType workType;
	private Stock outputStock;
	private Collection<Stock> stocks;
	private Collection<Flow> flows;

	WorkModule(WorkType workType, Task task) {
		this.task = task;
		this.workType = workType;
		this.stocks = new ArrayList<Stock>();
		this.flows = new ArrayList<>();
		this.buildWorkTypeModule();
	}

	private void buildWorkTypeModule() {
		Source bruteWork = new Source(getWorkType().c(SOURCE_BRUTE_WORK));
		Constant efficiency = new Constant(getWorkType().c(CONST_EFICIENCY), task.getResponsiblePerson().getEfficiency());

		Flow polishedWork = new Flow(getWorkType().c(FLOW_POLISHED_WORK)) {

			@Override
			public Double calculateNext(Double previousValue) {
				return v(getWorkType().c(SOURCE_BRUTE_WORK)) * v(getWorkType().c(CONST_EFICIENCY));
			}
		};
		this.addFlow(polishedWork);
		polishedWork.connectInput(bruteWork);
		polishedWork.connectInput(efficiency);

		Stock integratedWork = new Stock(getWorkType().c(STOCK_INTEGRATED_WORK));
		this.addStock(integratedWork);
		integratedWork.connectInputFlow(polishedWork);

		this.outputStock = integratedWork;
	}

	public WorkType getWorkType() {
		return this.workType;
	}

	public Stock getOutputStock() {
		return this.outputStock;
	}

	@Override
	public Collection<Flow> getFlows() {
		return this.flows;
	}

	public void addFlow(Flow flow) {
		this.flows.add(flow);
	}

	@Override
	public Collection<Stock> getStocks() {
		return stocks;
	}

	public void addStock(Stock stock) {
		this.stocks.add(stock);
	}
}
