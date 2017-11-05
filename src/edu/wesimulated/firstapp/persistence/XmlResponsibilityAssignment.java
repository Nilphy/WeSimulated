package edu.wesimulated.firstapp.persistence;

import edu.wesimulated.firstapp.model.RoleData;

public class XmlResponsibilityAssignment {

	private Integer taskId;
	private RoleData role;
	private Boolean responsible;
	private Boolean accountable;
	private Boolean consulted;
	private Boolean informed;

	public Integer getTaskId() {
		return taskId;
	}

	public void setTaskId(Integer taskId) {
		this.taskId = taskId;
	}

	public RoleData getRole() {
		return role;
	}

	public void setRole(RoleData role) {
		this.role = role;
	}

	public Boolean getResponsible() {
		return responsible;
	}

	public void setResponsible(Boolean responsible) {
		this.responsible = responsible;
	}

	public Boolean getAccountable() {
		return accountable;
	}

	public void setAccountable(Boolean accountable) {
		this.accountable = accountable;
	}

	public Boolean getConsulted() {
		return consulted;
	}

	public void setConsulted(Boolean consulted) {
		this.consulted = consulted;
	}

	public Boolean getInformed() {
		return this.informed;
	}

	public void setInformed(Boolean informed) {
		this.informed = informed;
	}

}
