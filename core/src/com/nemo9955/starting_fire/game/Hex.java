package com.nemo9955.starting_fire.game;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.nemo9955.starting_fire.storage.SF;

public enum Hex {

	DIRT("dirt"), GRAVEL("gravel"), GRASS("grass"), WATER("water"), SAND("sand");

	String	name;

	private Hex(String name) {
		this.name = name;
	}

	public TextureRegion getTex() {
		return SF.atlas.findRegion(name);
	}

	public String getName() {
		return name;
	}

	public static Hex getRandomValue() {
		return values()[MathUtils.random(values().length - 1)];
	}

}
