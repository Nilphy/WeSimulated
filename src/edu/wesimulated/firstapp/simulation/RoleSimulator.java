package edu.wesimulated.firstapp.simulation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import com.wesimulated.simulation.runparameters.TaskCompletedEndCondition;
import com.wesimulated.simulationmotor.des.BOperation;
import com.wesimulated.simulationmotor.des.COperation;
import com.wesimulated.simulationmotor.des.OperationBasedSimulator;
import com.wesimulated.simulationmotor.des.threefaseaproach.ThreePhaseExecutor;

import edu.wesimulated.firstapp.simulation.domain.Person;
import edu.wesimulated.firstapp.simulation.domain.Project;
import edu.wesimulated.firstapp.simulation.domain.Role;
import edu.wesimulated.firstapp.simulation.domain.Task;
import edu.wesimulated.firstapp.simulation.domain.worktype.TypeOfWork;
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
public class RoleSimulator extends OperationBasedSimulator {

	private Project project;
	private Role role;
	private Person person;
	private Task currentTask;
	private TypeOfWork currentTypeOfWork;
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
		Date interruptionDate = this.getOperationBasedExecutor().getClock().getCurrentDate();
		long durationOfCurrentTask = interruptionDate.getTime() - this.currentTaskStart.getTime();
		this.currentTask.increaseWorkDone(durationOfCurrentTask, this.currentTypeOfWork.getTaskNeedFulfilled(), interruptionDate);
		// TODO register work done until the moment
		// TODO remove next BOperation
		/*
		 * TODO add next COperation to continue working when the person be free
		 * again
		 */
	}

	public Collection<NumericallyModeledEntity> getAllNumericallyModeledEntities() {
		Collection<NumericallyModeledEntity> out = new ArrayList<>();
		out.add(this.getRole());
		out.add(this.getProject());
		out.add(this.getPerson());
		out.add(this.getCurrentTask());
		out.add(this.getCurrentTypeOfWork());
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
		this.currentTask = task;
	}

	public void setCurrentTypeOfWork(TypeOfWork typeOfWork) {
		this.currentTypeOfWork = typeOfWork;
	}

	public TypeOfWork getCurrentTypeOfWork() {
		return this.currentTypeOfWork;
	}

	public void setCurrentTaskStart(Date date) {
		this.currentTaskStart = date;
	}
}
