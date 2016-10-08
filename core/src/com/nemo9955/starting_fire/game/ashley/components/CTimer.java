package com.nemo9955.starting_fire.game.ashley.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool.Poolable;

public class CTimer implements Poolable, Component {

	public float time;
	public int msgType;

	@Override
	public void reset() {
		time = 0;
		msgType = 0;
	}

}
