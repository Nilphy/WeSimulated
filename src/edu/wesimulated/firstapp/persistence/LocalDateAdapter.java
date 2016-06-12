package edu.wesimulated.firstapp.persistence;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javafx.util.StringConverter;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class LocalDateAdapter extends XmlAdapter<String, LocalDate> {

	private final String pattern = "yyyy-MM-dd";
	StringConverter<LocalDate> converter;
	DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);

	public LocalDate unmarshal(String value) throws Exception {
		return LocalDate.parse(value, dateFormatter);
	}

	public String marshal(LocalDate value) throws Exception {
		return dateFormatter.format(value);
	}
}
