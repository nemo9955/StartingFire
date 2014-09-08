package com.nemo9955.starting_fire.game.tiles;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.nemo9955.starting_fire.storage.SF;

public enum HexBase {

	DIRT("dirt"), GRAVEL("gravel"), GRASS("grass"), WATER("water"), SAND("sand");

	String	name;

	private HexBase(String name) {
		this.name = name;
	}

	public TextureRegion getTex() {
		return SF.atlas.findRegion(name);
	}

	public String getName() {
		return name;
	}

	public static HexBase getRandomValue() {
		return values()[MathUtils.random(values().length - 1)];
	}

}
