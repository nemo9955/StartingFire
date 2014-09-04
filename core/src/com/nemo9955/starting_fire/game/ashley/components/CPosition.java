package com.nemo9955.starting_fire.game.ashley.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool.Poolable;

public class CPosition extends Component implements Poolable {

	public float	x, y;

	@Override
	public void reset() {
		x = y = 0;
	}
}
