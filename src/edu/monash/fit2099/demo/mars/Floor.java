package edu.monash.fit2099.demo.mars;

import edu.monash.fit2099.engine.Ground;


public class Floor extends Ground {

	public Floor() {
		super('.');
	}

	@Override
	public void experienceRain(double rainfall) {

	}

	@Override
	public boolean isSquishable() {
		return false;
	}
}
