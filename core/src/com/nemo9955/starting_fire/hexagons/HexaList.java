package com.nemo9955.starting_fire.hexagons;

public enum HexaList {

	dirt("dirt"), gravel("gravel"), gras("grass"), water("water"), sand("sand");

	String	name;

	@Override
	public String toString() {
		return name;
	}

	HexaList(String name) {
		this.name = name;
	}

}
