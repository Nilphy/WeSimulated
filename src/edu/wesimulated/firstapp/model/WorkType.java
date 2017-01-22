package edu.wesimulated.firstapp.model;

public enum WorkType {

	Development("DEV"), 
	TechnologyInvestigation("TECH_INV"),
	BussinessInvestigation("BUSS_INV"),
	Desing("DESING"), 
	Rework("REWORK"),
	/**
	 * Pair review
	 */
	Review("REVIEW"),
	/**
	 * Manual tests
	 */
	Qc("QC"), 
	/**
	 * Automatic tests
	 */
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
