package edu.wesimulated.firstapp.simulation.stochastic;

/**
 * get Value puede construir un Number y devolver los long value, float value...
 * y si es una classification ver de construir la correspondiente classification
 * o devolver el string según corresponda
 */
public class EntryValue {

	public enum Type {
		LONG, FLOAT, STRING, BOOL
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
		this.stringValue = value.toString();
	}

	public EntryValue(Boolean value) {
		this.type = Type.BOOL;
		this.stringValue = value.toString();
		this.numberValue = value ? 1 : 0;
	}

	public Number getNumber() {
		if (this.numberValue != null) {
			return this.numberValue;
		}
		if (this.type == Type.LONG) {
			return this.numberValue;
		}
		if (this.type == Type.FLOAT) {
			return this.numberValue;
		}
		if (this.type == Type.BOOL) {
			return this.numberValue;
		}
		throw new IllegalStateException();
	}

	public String getString() {
		if (this.type == Type.STRING) {
			return this.stringValue;
		}
		if (this.type == Type.LONG) {
			return this.stringValue;
		}
		if (this.type == Type.FLOAT) {
			return this.stringValue;
		}
		if (this.type == Type.BOOL) {
			return this.stringValue;
		}
		throw new IllegalStateException();
	}
	
	public Boolean  getBoolean() {
		if (this.type == Type.BOOL) {
			return Boolean.parseBoolean(this.stringValue);
		}
		throw new IllegalStateException();
	}
}
