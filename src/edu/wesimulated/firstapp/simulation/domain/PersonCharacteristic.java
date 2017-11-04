package edu.wesimulated.firstapp.simulation.domain;

public enum PersonCharacteristic implements Characteristic {
	EFFICIENCY,
	EFFECTIVENESS,
	EXPERIENCE,
	EXPERIENCE_DEVELOP_SOFTWARE,
	EXPERIENCE_WITH_WORKBENCH_TOOLS,
	MAX_PRIORITY_EMAIL,
	MAX_PRIORITY_FACE_TO_FACE_QUESTION,
	MAX_PRIORITY_SQUEALER,
	MAX_PRIORITY_IM,
	IS_AVAILABLE,
	AMOUNT_OF_TEAMS,
	// SENDER CHARACTERISTICS
	SENDER_EFFICIENCY,
	SENDER_EFFECTIVENESS,
	SENDER_MAX_PRIORITY_EMAIL,
	SENDER_MAX_PRIORITY_FACE_TO_FACE_QUESTION,
	SENDER_MAX_PRIORITY_SQUEALER,
	SENDER_MAX_PRIORITY_IM,
	SENDER_IS_AVAILABLE,
	SENDER_AMOUNT_OF_TEAMS;
	
	public static PersonCharacteristic valueOfOrNull(String characteristicName) {
		try {
			return PersonCharacteristic.valueOf(characteristicName);
		} catch (IllegalArgumentException e) {
			// nothing to see here...
		}
		return null;
	}
}
