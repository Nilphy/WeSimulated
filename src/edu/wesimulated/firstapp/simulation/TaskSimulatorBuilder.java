package edu.wesimulated.firstapp.simulation;

import com.wesimulated.simulationmotor.systemdynamics.Constant;
import com.wesimulated.simulationmotor.systemdynamics.Flow;
import com.wesimulated.simulationmotor.systemdynamics.SinkFlow;
import com.wesimulated.simulationmotor.systemdynamics.Stock;

import edu.wesimulated.firstapp.model.WorkType;
import edu.wesimulated.firstapp.simulation.domain.Project;
import edu.wesimulated.firstapp.simulation.domain.Task;
import edu.wesimulated.firstapp.simulation.stochastic.ParametricAlgorithm;
import edu.wesimulated.firstapp.simulation.stochastic.StochasticVar;

/**
 * Comportamiento a simular sobre una tarea:
 * 
 * * Irá completando el trabajo requerido para realizarse cuando las personas
 * asignadas realizen trabajo para la tarea. * Irá teniendo bugs * Irá generando
 * necesidades de cambio
 * 
 * @author Carolina
 *
 */
public class TaskSimulatorBuilder {

	private static final String FLOW_REWORK_GENERATION = "FLOW_REWORK_GENERATION";
	private static final String STOCK_ALL_REWORK = "STOCK_ALL_REWORK";
	private static final String STOCK_REWORK_DETECTED = "STOCK_REWORK_DETECTED";
	private static final String FLOW_REWORK_DETECTION = "FLOW_REWORK_DETECTION";
	private static final String CONST_TIME_REVIEW_PER_DETECTION = "CONST_TIME_REVIEW_PER_DETECTION";
	protected static final String CONST_TIME_QC_PER_DETECTION = "CONST_TIME_QC_PER_DETECTION";
	protected static final String CONST_QUALITY_AUTO_QC = "CONST_QUALITY_AUTO_QC";
	private static final String FLOW_REWORK_COMPLETION = "FLOW_REWORK_COMPLETION";
	private static final String STOCK_REWORK_FIXED = "STOCK_REWORK_FIXED";
	private static TaskSimulatorBuilder instance;

	public static TaskSimulatorBuilder getInstance() {
		if (instance == null) {
			instance = new TaskSimulatorBuilder();
		}
		return instance;
	}

	private TaskSimulatorBuilder() {
	}

	public TaskSimulator build(Task task, Project project) {
		ParametricAlgorithm reviewTimeToReworkFactor = ParametricAlgorithm.buildParamethricAlgorithmForVar(StochasticVar.ReviewTimeToReworkFactor);
		reviewTimeToReworkFactor.consider(task);
		reviewTimeToReworkFactor.considerAll(task.getAllRelatedNumericallyModeledEntities());
		ParametricAlgorithm qcTimeToReworkFactor = ParametricAlgorithm.buildParamethricAlgorithmForVar(StochasticVar.QcTimeToReworkFactor);
		qcTimeToReworkFactor.consider(task);
		qcTimeToReworkFactor.considerAll(task.getAllRelatedNumericallyModeledEntities());
		ParametricAlgorithm autoQcToReworkFactor = ParametricAlgorithm.buildParamethricAlgorithmForVar(StochasticVar.AutoQcToReworkFactor);
		autoQcToReworkFactor.consider(task);
		ParametricAlgorithm uowBugsProportion = ParametricAlgorithm.buildParamethricAlgorithmForVar(StochasticVar.UowBugs);
		uowBugsProportion.consider(task);
		uowBugsProportion.considerAll(task.getAllRelatedNumericallyModeledEntities());

		TaskSimulator simulator = new TaskSimulator(task);

		// This flow is to be used of output of all stocks that are consumed
		// entirely on each step
		Flow sinkFlow = new SinkFlow();
		simulator.register(sinkFlow);

		// The output of all this stocks is accumulated (not dropped to another
		// flow being a physical input) because
		// they have is the work done in the task, and is used to know if the
		// task is finished
		WorkModule developmentModule = new WorkModule(WorkType.Development, task);
		simulator.registerPlannedTaskWorkModule(developmentModule);
		WorkModule techInvestigationModule = new WorkModule(WorkType.TechnologyInvestigation, task);
		simulator.registerPlannedTaskWorkModule(techInvestigationModule);
		WorkModule bussinessInvestigationModule = new WorkModule(WorkType.BussinessInvestigation, task);
		simulator.registerPlannedTaskWorkModule(bussinessInvestigationModule);
		WorkModule designModule = new WorkModule(WorkType.Desing, task);
		simulator.registerPlannedTaskWorkModule(designModule);

		// The output of this stocks is consumed completely on each time step,
		// so its dropped to a sink flow
		WorkModule reworkModule = new WorkModule(WorkType.Rework, task);
		simulator.register(reworkModule);
		sinkFlow.connectInput(reworkModule.getOutputStock());
		WorkModule reviewModule = new WorkModule(WorkType.Review, task);
		simulator.register(reviewModule);
		sinkFlow.connectInput(reviewModule.getOutputStock());
		WorkModule qcModule = new WorkModule(WorkType.Qc, task);
		simulator.register(qcModule);
		sinkFlow.connectInput(qcModule.getOutputStock());
		WorkModule automatedQcModule = new WorkModule(WorkType.AutoQc, task);
		simulator.register(automatedQcModule);
		sinkFlow.connectInput(automatedQcModule.getOutputStock());
		Flow reworkGeneration = new Flow(FLOW_REWORK_GENERATION) {

			@Override
			public Double calculateNext(Double previousValue) {
				double uowBugsProportionSample = uowBugsProportion.findSample().getPrediction().getValue().doubleValue();
				return uowBugsProportionSample * (v(WorkType.Rework.c(WorkModule.STOCK_INTEGRATED_WORK)) + v(WorkType.Development.c(WorkModule.STOCK_INTEGRATED_WORK)));
			}
		};
		simulator.register(reworkGeneration);
		reworkGeneration.connectInput(reworkModule.getOutputStock());
		reworkGeneration.connectInput(developmentModule.getOutputStock());

		Stock allRework = new Stock(STOCK_ALL_REWORK);
		simulator.register(allRework);
		allRework.connectInputFlow(reworkGeneration);

		autoQcToReworkFactor.considerAll(task.getAllRelatedNumericallyModeledEntities());
		Constant timeReviewPerDetection = new Constant(CONST_TIME_REVIEW_PER_DETECTION, reviewTimeToReworkFactor.findSample().getPrediction().getValue().doubleValue());
		Constant timeQCPerDetection = new Constant(CONST_TIME_QC_PER_DETECTION, qcTimeToReworkFactor.findSample().getPrediction().getValue().doubleValue());
		Constant qualityAutoQc = new Constant(CONST_QUALITY_AUTO_QC, autoQcToReworkFactor.findSample().getPrediction().getValue().doubleValue());
		Flow reworkDetection = new Flow(FLOW_REWORK_DETECTION) {

			@Override
			public Double calculateNext(Double previousValue) {
				Double totalReworkDetectionWork = 
						v(CONST_TIME_REVIEW_PER_DETECTION) * v(WorkType.Review.c(WorkModule.STOCK_INTEGRATED_WORK)) 
						+ v(CONST_TIME_QC_PER_DETECTION) * v(WorkType.Qc.c(WorkModule.STOCK_INTEGRATED_WORK)) 
						+ v(CONST_QUALITY_AUTO_QC) * v(WorkType.AutoQc.c(WorkModule.STOCK_INTEGRATED_WORK));
				if (v(STOCK_ALL_REWORK) > totalReworkDetectionWork) {
					return totalReworkDetectionWork;
				} else {
					return v(STOCK_ALL_REWORK);
				}
			}
		};
		simulator.register(reworkDetection);
		reworkDetection.connectInput(timeReviewPerDetection);
		reworkDetection.connectInput(timeQCPerDetection);
		reworkDetection.connectInput(qualityAutoQc);
		reworkDetection.connectInput(reviewModule.getOutputStock());
		reworkDetection.connectInput(qcModule.getOutputStock());
		reworkDetection.connectInput(automatedQcModule.getOutputStock());
		reworkDetection.connectInput(allRework);

		Stock reworkDetected = new Stock(STOCK_REWORK_DETECTED);
		simulator.register(reworkDetected);
		reworkDetected.connectInputFlow(reworkDetection);

		Flow reworkCompletion = new Flow(FLOW_REWORK_COMPLETION) {

			@Override
			public Double calculateNext(Double previousValue) {
				if (v(STOCK_REWORK_DETECTED) > v(WorkType.Rework.c(WorkModule.STOCK_INTEGRATED_WORK))) {
					return v(STOCK_REWORK_DETECTED);
				} else {
					return v(WorkType.Rework.c(WorkModule.STOCK_INTEGRATED_WORK));
				}
			}
		};
		simulator.register(reworkCompletion);
		reworkCompletion.connectInput(reworkModule.getOutputStock());
		reworkCompletion.connectInput(reworkDetected);

		Stock reworkFixed = new Stock(STOCK_REWORK_FIXED);
		simulator.register(reworkFixed);
		reworkFixed.connectInputFlow(reworkCompletion);

		return simulator;
	}
}
