package edu.wesimulated.firstapp.simulation.domain.avature.role;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Observable;
import java.util.Observer;

import com.wesimulated.simulation.runparameters.TaskCompletedEndCondition;
import com.wesimulated.simulationmotor.des.BOperation;
import com.wesimulated.simulationmotor.des.COperation;
import com.wesimulated.simulationmotor.des.OperationBasedSimulator;
import com.wesimulated.simulationmotor.des.threefaseaproach.ThreePhaseExecutor;

import edu.wesimulated.firstapp.simulation.domain.Person;
import edu.wesimulated.firstapp.simulation.domain.Project;
import edu.wesimulated.firstapp.simulation.domain.Role;
import edu.wesimulated.firstapp.simulation.domain.Task;
import edu.wesimulated.firstapp.simulation.domain.worktype.WorkType;
import edu.wesimulated.firstapp.simulation.stochastic.NumericallyModeledEntity;

/**
 * En el diagrama de estados se ve que una persona durante la ejecución de un proyecto, 
 * pasa de un rol a otro y de un estado a otro constántemente.
 * 
 * Una de las complejidades de su simulación está en que es difícil determinar a que estado pasará luego
 * de salir un estado y cuanto tiempo permanecerá en cada estado.
 * 
 * Los pasajes entre estados se podrían dar porque:
 * - la persona fue interrumpida
 * - por necesidad
 * - porque había otra tarea calendarizada
 * 
 * El simulador de rol consume al recurso persona la cual trabaja durante su horario laboral ejerciendo
 * distintos roles, lo cual implica que:
 * 
 * - Los simuladores de cada rol se interrumpirán entre sí
 * - El simulador de un rol estará pausado mientras otro simulador de rol esté consumiendo a su persona
 * - Cuando una tarea que debía ser realizada por un rol empiece a ser realizada por cierta persona específica dificilmente cambie la persona asignada a la misma
 * - Los roles tendrán cierta prioridad en el uso de una persona
 *  
 *  Las tareas van a requerir a un rol una cantidad de personas para trabajar en ellas
 */
public class RoleSimulator extends OperationBasedSimulator implements Observer {

	private Project project;
	private Role role;
	private Person person;
	private Task currentTask;
	private WorkType currentWorkType;
	private Date currentTaskStart;

	public RoleSimulator(Role role, Project project, Person person) {
		super(new ThreePhaseExecutor(new TaskCompletedEndCondition(project)));
		this.role = role;
		this.project = project;
		this.person = person;
	}

	public void addBOperation(BOperation operation) {
		this.getOperationBasedExecutor().addBOperation(operation);
	}

	public void addCOperation(COperation operation) {
		this.getOperationBasedExecutor().addCOperation(operation);
	}

	public void acceptInterruption() {
		// FIXME: has to determine if will accept the interruption or not (and count interruptions)???
		Date interruptionDate = this.getOperationBasedExecutor().getClock().getCurrentDate();
		long durationOfCurrentTask = interruptionDate.getTime() - this.currentTaskStart.getTime();
		this.currentTask.increaseWorkDone(durationOfCurrentTask, this.currentWorkType.getTaskNeedFulfilled(), interruptionDate);
		this.getOperationBasedExecutor().removeEndOfThisTask();
		this.getOperationBasedExecutor().reprogramCurrentCOperation();
		this.getPerson().getHlaPerson().addObserver(this);
	}

	public Collection<NumericallyModeledEntity> getAllNumericallyModeledEntities() {
		Collection<NumericallyModeledEntity> out = new ArrayList<>();
		out.add(this.getRole());
		out.add(this.getProject());
		out.add(this.getPerson());
		out.add(this.getCurrentTask());
		out.add(this.getCurrentWorkType());
		return out;
	}

	public Role getRole() {
		return this.role;
	}

	public Project getProject() {
		return this.project;
	}

	public Person getPerson() {
		return this.person;
	}

	public Task getCurrentTask() {
		return this.currentTask;
	}

	public void setCurrentTask(Task task) {
		this.getPerson().setAvailable(false);
		this.currentTask = task;
		this.getPerson().setCurrentTask(task);
	}

	public void setCurrentWorkType(WorkType workType) {
		this.currentWorkType = workType;
	}

	public WorkType getCurrentWorkType() {
		return this.currentWorkType;
	}

	public void setCurrentTaskStart(Date date) {
		this.currentTaskStart = date;
	}

	@Override
	public void update(Observable o, Object arg) {
		if (this.person.isAvailable()) {
			this.addBOperation(new RecoverFocus(this.person.getDateLastUpdate(), this));
		} else {
			this.acceptInterruption();
		}
	}

	public String findTimeSinceLastTimeTask() {
		// TODO Auto-generated method stub
		return null;
	}

	public String findTimeInThisMonthTask() {
		// TODO Auto-generated method stub
		return null;
	}
}
