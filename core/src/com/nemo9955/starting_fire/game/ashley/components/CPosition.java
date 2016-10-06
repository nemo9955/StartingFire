package com.nemo9955.starting_fire.game.ashley.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool.Poolable;

public class CPosition extends Vector2 implements Poolable,Component {

//	public float	x, y;

	/**
	 * 
	 */
	private static final long serialVersionUID = -3698301205308208738L;

	@Override
	public void reset() {
		x = y = 0;
	}
}
