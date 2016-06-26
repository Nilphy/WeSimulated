package edu.wesimulated.firstapp.simulation.hla;

import java.util.ArrayList;
import java.util.Collection;

public class HlaClass {
	private static HlaClass hlaPersonClass;
	private static HlaClass hlaTaskClass;
	private static HlaClass hlaProjectClass;

	private String name;
	private String federateName;
	private Collection<HlaAttribute> attributes;

	public static HlaClass getHlaPersonClassInstance() {
		if (hlaPersonClass == null) {
			Collection<HlaAttribute> attributes = new ArrayList<>();
			attributes.add(HlaAttribute.getHlaWorkDoneAttributeInstance());
			hlaPersonClass = new HlaClass("Person", "PERSON_FEDERATE", attributes);
		}
		return hlaPersonClass;
	}

	public static HlaClass getHlaTaskClassInstance() {
		if (hlaTaskClass == null) {
			Collection<HlaAttribute> attributes = new ArrayList<>();
			attributes.add(HlaAttribute.getHlaWorkToDoInstance());
			hlaTaskClass = new HlaClass("Task", "TASK_FEDERATE", attributes);
		}
		return hlaTaskClass;
	}

	public static HlaClass getHlaProjectClassInstance() {
		if (hlaProjectClass == null) {
			hlaProjectClass = new HlaClass("Project", "PROJECT_FEDERATE", null);
		}
		return hlaProjectClass;
	}

	public static Collection<HlaClass> getAllClasses() {
		Collection<HlaClass> returnValue = new ArrayList<HlaClass>();
		returnValue.add(HlaClass.getHlaPersonClassInstance());
		returnValue.add(HlaClass.getHlaProjectClassInstance());
		returnValue.add(HlaClass.getHlaTaskClassInstance());
		return returnValue;
	}

	private HlaClass(String name, String federateName, Collection<HlaAttribute> attributes) {
		this.setName(name);
		this.setFederateName(federateName);
		this.setAttributes(attributes);
	}

	public String getName() {
		return name;
	}

	private void setName(String name) {
		this.name = name;
	}

	public String getFederateName() {
		return federateName;
	}

	private void setFederateName(String federateName) {
		this.federateName = federateName;
	}

	public Collection<HlaAttribute> getAttributes() {
		return attributes;
	}

	private void setAttributes(Collection<HlaAttribute> attributes) {
		this.attributes = attributes;
	}
}
