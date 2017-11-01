package edu.wesimulated.firstapp.simulation.domain.worktype;

import java.util.Date;

import com.wesimulated.simulationmotor.des.State;

import edu.wesimulated.firstapp.model.TaskNeed;
import edu.wesimulated.firstapp.simulation.domain.mywork.role.RoleSimulator;
import edu.wesimulated.firstapp.simulation.stochastic.Classification;
import edu.wesimulated.firstapp.simulation.stochastic.NumericallyModeledEntity;

/**
 * Algo curioso sobre el diseño de los simuladores es que hay cosas que van a
 * ser aprendidas por el sistema por los datos ingresados durante el uso pero va
 * a haber otras cosas que van a ser fijadas por mi diseño Lo ideal sería que lo
 * que yo establezca sea lo menor posible para que el simulador pueda adaptarse
 * a cualquier tipo de proyecto Algo que estoy fijando por código es los
 * distintos tipos de trabajo y que consecuencias tienen en las entidades
 * participantes de la simulación Si bien los números exactos son calculados con
 * IA
 * 
 * @author Carolina
 *
 */
public abstract class WorkType implements NumericallyModeledEntity, State, Classification {

	private RoleSimulator roleSimulator;
	private TaskNeed taskNeedFulfilled;

	public WorkType(RoleSimulator roleSimulator) {
		this.setRoleSimulator(roleSimulator);
	}

	public abstract void applyEffects(Date start, Date end);

	public RoleSimulator getRoleSimulator() {
		return roleSimulator;
	}

	public void setRoleSimulator(RoleSimulator roleSimulator) {
		this.roleSimulator = roleSimulator;
	}

	public TaskNeed getTaskNeedFulfilled() {
		return this.taskNeedFulfilled;
	}
}
