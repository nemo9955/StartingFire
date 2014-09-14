package com.nemo9955.starting_fire.game.ashley.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool.Poolable;

public class CTimer extends Component implements Poolable {

	public float	time;

	@Override
	public void reset() {
		time = 0;
	}

}
