package edu.wesimulated.firstapp.simulation.stochastic;

public class EntryValue {

	public enum EntryValueType {
		Long,
		Int,
		Float,
		String
	}
	
	private EntryValueType type;
	private String value;

	/**
	 * get Value puede construir un Number y devolver los long value, float value...
	 * y si es una classification ver de construir la correspondiente classification o devolver el string según corresponda
	 */
}
