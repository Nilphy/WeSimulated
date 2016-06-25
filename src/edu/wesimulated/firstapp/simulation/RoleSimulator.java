package edu.wesimulated.firstapp.simulation;

import java.util.Collection;
import java.util.LinkedList;

import com.wesimulated.simulationmotor.des.BOperation;
import com.wesimulated.simulationmotor.des.OperationBasedExecutor;

import edu.wesimulated.firstapp.simulation.domain.Person;
import edu.wesimulated.firstapp.simulation.domain.Task;

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
 *  
 */
public class RoleSimulator extends Simulator {

	private Collection<Task> tasks;
	private Person person;

	public RoleSimulator(OperationBasedExecutor executor, Person person) {
		this.setExecutor(executor);
		this.person = person;
	}

	public void assignTask(Task task) {
		if (this.tasks == null) {
			this.tasks = new LinkedList<>();
		}
		this.tasks.add(task);
	}

	public void addBOperation(BOperation operation) {
		this.getExecutor().addBOperation(operation);
	}
}
