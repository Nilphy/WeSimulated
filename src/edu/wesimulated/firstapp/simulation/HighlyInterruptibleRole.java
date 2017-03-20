package edu.wesimulated.firstapp.simulation;
import java.util.Collection;
import java.util.Date;
import java.util.TreeMap;

import com.wesimulated.simulationmotor.des.Prioritized;
import com.wesimulated.simulationmotor.des.processbased.Entity;

import edu.wesimulated.firstapp.simulation.domain.ImMessage;

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
public class HighlyInterruptibleRole extends Entity {

	private TreeMap<Prioritized, HighlyInterruptibleRoleActivity> activityFlow;

	@Override
	protected Date doProcess() {
		return this.findActivityToWorkOn().process(this);
	}

	private HighlyInterruptibleRoleActivity findActivityToWorkOn() {
		return activityFlow.firstEntry().getValue();
	}

	public Collection<ImMessage> readAndCategorizeUnreadIM(Collection<ImMessage> unreadMessages) {
		// TODO Auto-generated method stub
		return null;
	}

	public Collection<ImMessage> resolvePendingImMessages(Collection<ImMessage> pendingImMessages) {
		// TODO Auto-generated method stub
		return null;
	}
}
