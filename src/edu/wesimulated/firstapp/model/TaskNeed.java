package edu.wesimulated.firstapp.model;
/**
 * Each task requires some things to be completed os this class 
 * @author Carolina
 *
 */
public enum TaskNeed {

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

	TaskNeed(String initial) {
		this.initial = initial;
	}

	public String toString() {
		return initial;
	}

	public String c(String constant) {
		return constant + this.toString();
	}
}
