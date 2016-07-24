package edu.wesimulated.firstapp.simulation;

import com.wesimulated.simulationmotor.des.BOperation;
import com.wesimulated.simulationmotor.des.COperation;
import com.wesimulated.simulationmotor.des.OperationBasedExecutor;
import com.wesimulated.simulationmotor.des.threefaseaproach.ThreePhaseExecutor;

import edu.wesimulated.firstapp.simulation.domain.Person;
import edu.wesimulated.firstapp.simulation.domain.Project;
import edu.wesimulated.firstapp.simulation.domain.Role;
import edu.wesimulated.firstapp.simulation.domain.Task;
import edu.wesimulated.firstapp.simulation.domain.worktype.TypeOfWork;

/*
 * 
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
public class RoleSimulator extends Simulator {

	private Project project;
	private Role role;
	private Person person;
	private Task currentTask;
	private TypeOfWork typeOfWork;

	public RoleSimulator(OperationBasedExecutor executor) {
		this.setExecutor(executor);
	}

	public RoleSimulator(ThreePhaseExecutor executor, Role role, Project project, Person person) {
		this(executor);
		this.role = role;
		this.project = project;
		this.person = person;
	}

	public void addBOperation(BOperation operation) {
		this.getExecutor().addBOperation(operation);
	}

	public void addCOperation(COperation operation) {
		this.getExecutor().addCOperation(operation);
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
		this.typeOfWork = typeOfWork;
	}
	
	public TypeOfWork getCurrentTypeOfWork() {
		return this.typeOfWork;
	}
}
