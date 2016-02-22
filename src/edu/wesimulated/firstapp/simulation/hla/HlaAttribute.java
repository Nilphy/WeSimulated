package edu.wesimulated.firstapp.simulation.hla;

public class HlaAttribute {
	private static HlaAttribute hlaWorkDoneAttribute;
	private static HlaAttribute hlaWorkToDoAttribute;
	
	private String name;
	private HlaClass ofClass;
	
	public static HlaAttribute getHlaWorkDoneAttributeInstance() {
		if (hlaWorkDoneAttribute == null) {
			hlaWorkDoneAttribute = new HlaAttribute("WorkDone", HlaClass.getHlaPersonClassInstance());
		}
		return hlaWorkDoneAttribute;
	}

	public static HlaAttribute getHlaWorkToDoInstance() {
		if (hlaWorkToDoAttribute == null) {
			hlaWorkToDoAttribute = new HlaAttribute("WorkToDo", HlaClass.getHlaTaskClassInstance());
		}
		return hlaWorkToDoAttribute;
	}
	
	private HlaAttribute(String name, HlaClass ofClass) {
		this.setName(name);
		this.setOfClass(ofClass);
	}
	
	public String getName() {
		return name;
	}

	private void setName(String name) {
		this.name = name;
	}

	public HlaClass getOfClass() {
		return ofClass;
	}

	private void setOfClass(HlaClass ofClass) {
		this.ofClass = ofClass;
	}
}
