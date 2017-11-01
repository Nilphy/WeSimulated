package edu.wesimulated.firstapp.simulation.domain.mywork.role;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Observable;
import java.util.Observer;

import com.wesimulated.simulation.runparameters.TaskCompletedEndCondition;
import com.wesimulated.simulationmotor.DateUtils;
import com.wesimulated.simulationmotor.des.BOperation;
import com.wesimulated.simulationmotor.des.COperation;
import com.wesimulated.simulationmotor.des.OperationBasedSimulator;
import com.wesimulated.simulationmotor.des.threefaseaproach.ThreePhaseExecutor;

import edu.wesimulated.firstapp.simulation.domain.Project;
import edu.wesimulated.firstapp.simulation.domain.Role;
import edu.wesimulated.firstapp.simulation.domain.Task;
import edu.wesimulated.firstapp.simulation.domain.worktype.WorkType;
import edu.wesimulated.firstapp.simulation.stochastic.NumericallyModeledEntity;

/**
 * En el diagrama de estados se ve que una persona durante la ejecuci�n de un proyecto, 
 * pasa de un rol a otro y de un estado a otro const�ntemente.
 * 
 * Una de las complejidades de su simulaci�n est� en que es dif�cil determinar a que estado pasar� luego
 * de salir un estado y cuanto tiempo permanecer� en cada estado.
 * 
 * Los pasajes entre estados se podr�an dar porque:
 * - la persona fue interrumpida
 * - por necesidad
 * - porque hab�a otra tarea calendarizada
 * 
 * El simulador de rol consume al recurso persona la cual trabaja durante su horario laboral ejerciendo
 * distintos roles, lo cual implica que:
 * 
 * - Los simuladores de cada rol se interrumpir�n entre s�
 * - El simulador de un rol estar� pausado mientras otro simulador de rol est� consumiendo a su persona
 * - Cuando una tarea que deb�a ser realizada por un rol empiece a ser realizada por cierta persona espec�fica dificilmente cambie la persona asignada a la misma
 * - Los roles tendr�n cierta prioridad en el uso de una persona
 *  
 *  Las tareas van a requerir a un rol una cantidad de personas para trabajar en ellas
 */
public class RoleSimulator extends OperationBasedSimulator implements Observer {

	private Project project;
	private Role role;
	private RolePerson person;
	private AvatureDeveloperTask currentTask;
	private WorkType currentWorkType;
	private Date currentTaskStart;
	private Date lastDateWorkRegistered;

	public RoleSimulator(Role role, Project project, RolePerson person) {
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
		this.lastDateWorkRegistered = this.getCurrentDate(); // FIXME: the interruption should have advanced the clock
		long durationOfCurrentTask = this.lastDateWorkRegistered.getTime() - this.currentTaskStart.getTime();
		this.currentTask.increaseWorkDone(durationOfCurrentTask, this.currentWorkType.getTaskNeedFulfilled(), lastDateWorkRegistered);
		this.getOperationBasedExecutor().removeEndOfThisTask();
		this.getOperationBasedExecutor().reprogramCurrentCOperation();
		this.getPerson().getHlaPerson().addObserver(this);
	}

	public Date getCurrentDate() {
		return this.getOperationBasedExecutor().getClock().getCurrentDate();
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

	public RolePerson getPerson() {
		return this.person;
	}

	public AvatureDeveloperTask getCurrentTask() {
		return this.currentTask;
	}

	public void setCurrentTask(AvatureDeveloperTask task) {
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

	public int findTimeSinceLastTimeTask() {
		return DateUtils.calculateDifferenceInMinutes(this.lastDateWorkRegistered, getCurrentDate());
	}

	public long findTimeInTask() {
		return this.currentTask.getWorkDone(this.getCurrentWorkType(), this.getRole(), this.getPerson());
	}
}