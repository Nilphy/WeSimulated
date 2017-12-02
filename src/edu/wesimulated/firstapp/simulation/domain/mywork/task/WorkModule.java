package edu.wesimulated.firstapp.simulation.domain.mywork.task;

import java.util.ArrayList;
import java.util.Collection;

import com.wesimulated.simulationmotor.systemdynamics.Constant;
import com.wesimulated.simulationmotor.systemdynamics.Flow;
import com.wesimulated.simulationmotor.systemdynamics.Module;
import com.wesimulated.simulationmotor.systemdynamics.Source;
import com.wesimulated.simulationmotor.systemdynamics.Stock;

import edu.wesimulated.firstapp.model.TaskNeedType;
import edu.wesimulated.firstapp.simulation.domain.Task;
import edu.wesimulated.firstapp.simulation.stochastic.ParametricAlgorithm;
import edu.wesimulated.firstapp.simulation.stochastic.StochasticVar;

public class WorkModule implements Module {
	public static final String SOURCE_BRUTE_WORK = "SOURCE_BRUTE_WORK";
	public static final String FLOW_POLISHED_WORK = "FLOW_POLISHED_WORK";
	public static final String CONST_EFICIENCY = "CONST_EFICIENCY";
	public static final String STOCK_INTEGRATED_WORK = "STOCK_INTEGRATED_WORK";

	private Task task;
	private TaskNeedType taskNeed;
	private Stock outputStock;
	private Collection<Stock> stocks;
	private Collection<Flow> flows;

	public WorkModule(TaskNeedType taskNeed, Task task) {
		this.task = task;
		this.taskNeed = taskNeed;
		this.stocks = new ArrayList<Stock>();
		this.flows = new ArrayList<>();
		this.buildWorkTypeModule();
	}

	private void buildWorkTypeModule() {
		Source bruteWork = new Source(getTaskNeed().c(SOURCE_BRUTE_WORK));
		ParametricAlgorithm timeWorkedEffectiveUowFactor = ParametricAlgorithm.buildParametricAlgorithmForVar(StochasticVar.EFFECTIVE_UOW_X_TIME_WORKED);
		timeWorkedEffectiveUowFactor.consider(task);
		Constant efficiency = new Constant(getTaskNeed().c(CONST_EFICIENCY), timeWorkedEffectiveUowFactor);

		Flow polishedWork = new Flow(getTaskNeed().c(FLOW_POLISHED_WORK)) {

			@Override
			public Double calculateNext(Double previousValue) {
				return v(getTaskNeed().c(SOURCE_BRUTE_WORK)) * v(getTaskNeed().c(CONST_EFICIENCY));
			}
		};
		this.addFlow(polishedWork);
		polishedWork.connectInput(bruteWork);
		polishedWork.connectInput(efficiency);

		Stock integratedWork = new Stock(getTaskNeed().c(STOCK_INTEGRATED_WORK));
		this.addStock(integratedWork);
		integratedWork.connectInputFlow(polishedWork);

		this.outputStock = integratedWork;
	}

	public TaskNeedType getTaskNeed() {
		return this.taskNeed;
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
