package edu.wesimulated.firstapp.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class LogEvent {
	
	private StringProperty event;

	public LogEvent() {
        this(0);
    }
	
	public LogEvent(double unitsOfWork) {
		this.event = new SimpleStringProperty(String.format("Person has done a total of %f units of work", unitsOfWork));
	}

	@Override
	public String toString() {
		return event.getValue();
	}
}
