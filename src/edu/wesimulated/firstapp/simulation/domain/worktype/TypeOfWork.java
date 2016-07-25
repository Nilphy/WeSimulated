package edu.wesimulated.firstapp.simulation.domain.worktype;

import java.util.Date;

import edu.wesimulated.firstapp.simulation.RoleSimulator;
import edu.wesimulated.firstapp.simulation.stochastic.NumericallyModeledEntity;

/**
 * Algo curioso sobre el dise�o de los simuladores es que hay cosas que van a ser aprendidas por el sistema
 * por los datos ingresados durante el uso
 * pero va a haber otras cosas que van a ser fijadas por mi dise�o
 * Lo ideal ser�a que lo que yo establezca sea lo menor posible para que el simulador pueda adaptarse 
 * a cualquier tipo de proyecto
 * Algo que estoy fijando por c�digo es los distintos tipos de trabajo
 * y que consecuencias tienen en las entidades participantes de la simulaci�n
 * Si bien los n�meros exactos son calculados con IA
 */
public abstract class TypeOfWork implements NumericallyModeledEntity {

	private RoleSimulator roleSimulator;

	public TypeOfWork(RoleSimulator roleSimulator) {
		this.setRoleSimulator(roleSimulator);
	}

	public abstract void applyEffects(Date start, Date end);

	public RoleSimulator getRoleSimulator() {
		return roleSimulator;
	}

	public void setRoleSimulator(RoleSimulator roleSimulator) {
		this.roleSimulator = roleSimulator;
	}
}