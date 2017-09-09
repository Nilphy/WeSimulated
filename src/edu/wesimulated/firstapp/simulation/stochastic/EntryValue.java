package edu.wesimulated.firstapp.simulation.stochastic;

/**
 * get Value puede construir un Number y devolver los long value, float value...
 * y si es una classification ver de construir la correspondiente classification
 * o devolver el string según corresponda
 */
public class EntryValue {

	public enum Type {
		Long, Float, String, Boolean
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

	public EntryValue(Boolean value) {
		this.type = Type.Boolean;
		this.stringValue = value.toString();
		this.numberValue = value ? 1 : 0;
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
		if (this.type == type.Boolean) {
			return this.numberValue;
		}
		throw new IllegalStateException();
	}

	public String getString() {
		if (this.type == Type.String) {
			return this.stringValue;
		}
		if (this.type == Type.Boolean) {
			return this.stringValue;
		}
		throw new IllegalStateException();
	}
	public Boolean  getBoolean() {
		if (this.type == Type.Boolean) {
			return Boolean.parseBoolean(this.stringValue);
		}
		throw new IllegalStateException();
	}
}
