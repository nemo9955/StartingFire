package com.nemo9955.starting_fire.game.ashley.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.utils.Pool.Poolable;

public class CInputListener implements Component, Poolable {

	public InputProcessor inputs;

	@Override
	public void reset() {
		inputs = null;
	}

}
