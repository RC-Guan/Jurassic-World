package edu.monash.fit2099.demo.conwayslife;

import edu.monash.fit2099.engine.Ground;

public class Floor extends Ground {

	public Floor() {
		super('.');
		addCapability(Status.DEAD);
	}

	@Override
	public void experienceRain(double rainfall) {

	}

	@Override
	public boolean isSquishable() {
		return false;
	}
}
