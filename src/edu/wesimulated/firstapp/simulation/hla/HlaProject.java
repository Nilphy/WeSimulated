package edu.wesimulated.firstapp.simulation.hla;

import hla.rti1516e.ObjectClassHandle;
import hla.rti1516e.ObjectInstanceHandle;
import hla.rti1516e.RTIambassador;

/**
 * The hla project will have, people, roles, contract and so on
 * @author Carolina
 *
 */
public class HlaProject extends HlaObject {

	public HlaProject(RTIambassador rtiAmbassador, ObjectClassHandle classHandle, ObjectInstanceHandle personHandle, String personName) {
		super(rtiAmbassador, classHandle, personHandle, personName);
		// TODO HLA entities
	}

}
