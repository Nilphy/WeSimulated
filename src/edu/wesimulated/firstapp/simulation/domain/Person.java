package edu.wesimulated.firstapp.simulation.domain;

import com.wesimulated.simulationmotor.des.Resource;

import edu.wesimulated.firstapp.simulation.hla.HlaPerson;

public class Person implements Resource {

	private boolean available;
	private HlaPerson hlaPerson;

	@Override
	public void setAvailable(boolean available) {
		this.available = available;
	}

	@Override
	public boolean isAvailable() {
		return available;
	}

	public HlaPerson getHlaPerson() {
		return hlaPerson;
	}

	public void setHlaPerson(HlaPerson hlaPerson) {
		this.hlaPerson = hlaPerson;
	}
}
