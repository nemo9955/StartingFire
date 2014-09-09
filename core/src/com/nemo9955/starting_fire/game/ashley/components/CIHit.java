package com.nemo9955.starting_fire.game.ashley.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.utils.Pool.Poolable;

public class CIHit extends Component implements Poolable {

	public IHitable	interact;

	@Override
	public void reset() {
		interact = null;
	}

	public static interface IHitable {

		public void hit( Entity ent );

	}

}
