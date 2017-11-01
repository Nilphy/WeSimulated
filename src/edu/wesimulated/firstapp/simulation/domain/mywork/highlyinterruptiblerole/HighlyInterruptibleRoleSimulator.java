package edu.wesimulated.firstapp.simulation.domain.mywork.highlyinterruptiblerole;

import java.util.Date;
import java.util.Observable;
import java.util.Observer;

import com.wesimulated.simulation.BaseSimulator;
import com.wesimulated.simulation.runparameters.TaskCompletedEndCondition;
import com.wesimulated.simulationmotor.des.processbased.Entity;
import com.wesimulated.simulationmotor.des.processbased.ProcessBasedExecutor;

import edu.wesimulated.firstapp.simulation.domain.Person;
import edu.wesimulated.firstapp.simulation.domain.Project;

/**
 * Va a haber distintos tipos de roles highlyInterruptible que tendrán distintas
 * actividades con distintas prioridades.
 * 
 * Las tareas que están en la lista de prioridades pueden ser tareas de control,
 * liberar a la persona para que cumpla un rol.
 * 
 * Por ejemplo las tareas de control son: verificar si hay mensajes de
 * mensajería o mails pendientes. Si los hay verificar su prioridad teniendo en
 * cuenta el tiempo que llevan sin contestar.
 * 
 * Pero principalmente hay un listado de actividades priorizadas y dependiendo
 * de la ocurrencia deberá decidir cual de ellas realizar.
 * 
 * Hay como una iteración de checkeos que hay que hacer periódicamente.
 * 
 * Una de las tareas puede ser liberar a una persona para que cierto rol pueda
 * consumirla.
 * 
 * @author Carolina
 *
 */
public class HighlyInterruptibleRoleSimulator extends BaseSimulator implements Observer {
	public Person person;

	public HighlyInterruptibleRoleSimulator(Project project, HighlyInterruptibleRolePerson person) {
		super(new ProcessBasedExecutor(new TaskCompletedEndCondition(project)));
		this.setPerson(person);
	}

	public void registerSimulationEntity(Entity entity) {
		this.getMyExecutor().registerEntity(entity);
	}

	private ProcessBasedExecutor getMyExecutor() {
		return (ProcessBasedExecutor) this.getExecutor();
	}

	public void acceptInterruption() {
		// FIXME: has to determine if will accept the interruption or not (and
		// count interruptions)???
		Date interruptionDate = this.getMyExecutor().getClock().getCurrentDate();
		for (Entity currentEvent : this.getMyExecutor().getCurrentEventsList()) {
			currentEvent.acceptInterruption(interruptionDate);
		}
		this.getPerson().getHlaPerson().addObserver(this);
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Check if the person is free and try to interrupt it
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}
}
