package com.nemo9955.starting_fire.game.tiles;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.nemo9955.starting_fire.storage.SF;

public enum HexBase {

	Dirt, Gravel, Grass, Water, Sand;

	@Override
	public String toString() {
		return this.name().toLowerCase().replace('_', ' ');
	}

	public TextureRegion getTex() {
		return SF.atlas.findRegion(toString());
	}

	public static HexBase getRandomValue() {
		return values()[MathUtils.random(values().length - 1)];
	}

}
