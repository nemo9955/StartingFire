package com.nemo9955.starting_fire.game;

import com.badlogic.gdx.math.MathUtils;

public enum Hex {
	DIRT("dirt"), GRAVEL("gravel"), GRASS("grass"), WATER("water"), SAND("sand");

	String	name;

	private Hex(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public static Hex getRandomValue() {
		return values()[MathUtils.random(values().length - 1)];
	}

}
