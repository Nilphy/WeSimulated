package edu.wesimulated.firstapp.simulation;

import java.util.Collection;
import java.util.LinkedList;

import com.wesimulated.simulationmotor.des.BOperation;
import com.wesimulated.simulationmotor.des.OperationBasedExecutor;

import edu.wesimulated.firstapp.simulation.domain.Person;
import edu.wesimulated.firstapp.simulation.domain.Task;

/*
 * 
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
