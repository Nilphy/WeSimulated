package edu.wesimulated.firstapp.model;

public enum WorkType {

	Development("DEV"), 
	TechnologyInvestigation("TECH_INV"),
	BussinessInvestigation("BUSS_INV"),
	Desing("DESING"), 
	Rework("REWORK"), 
	Review("REVIEW"), 
	Qc("QC"), 
	AutoQc("AUTO_QC");

	private String initial;

	WorkType(String initial) {
		this.initial = initial;
	}

	public String toString() {
		return initial;
	}

	public String c(String constant) {
		return constant + this.toString();
	}
}
