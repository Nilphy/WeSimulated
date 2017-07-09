package edu.wesimulated.firstapp.simulation.stochastic;

/**
 * get Value puede construir un Number y devolver los long value, float value...
 * y si es una classification ver de construir la correspondiente classification
 * o devolver el string según corresponda
 */
public class EntryValue {

	public enum Type {
		Long, Float, String
	}

	private Type type;
	private String stringValue;
	private Number numberValue;

	public EntryValue(Type type, String value) {
		this.type = type;
		this.stringValue = value;
	}

	public EntryValue(Type type, Number value) {
		this.type = type;
		this.numberValue = value;
	}

	public Number getNumber() {
		if (this.numberValue != null) {
			return this.numberValue;
		}
		if (this.type == Type.Long) {
			return Long.getLong(this.stringValue);
		}
		if (this.type == Type.Float) {
			return Float.parseFloat(this.stringValue);
		}
		throw new IllegalStateException();
	}
}
