package edu.wesimulated.firstapp.simulation;

import hla.rti1516e.exceptions.CallNotAllowedFromWithinCallback;
import hla.rti1516e.exceptions.FederateNotExecutionMember;
import hla.rti1516e.exceptions.FederateOwnsAttributes;
import hla.rti1516e.exceptions.FederatesCurrentlyJoined;
import hla.rti1516e.exceptions.FederationExecutionDoesNotExist;
import hla.rti1516e.exceptions.InvalidResignAction;
import hla.rti1516e.exceptions.NotConnected;
import hla.rti1516e.exceptions.OwnershipAcquisitionPending;
import hla.rti1516e.exceptions.RTIinternalError;
import edu.wesimulated.firstapp.simulation.exception.SimulationStillRunningException;

public class EndEvent extends SimulationEvent {

	@Override
	public void updateSimulation(PersonSimulator personSimulator, AbstractFederate abstractFederate) {
		if (personSimulator.isRunning()) {
			throw new SimulationStillRunningException();
		}
		abstractFederate.resignFromFederation();
	}
}
